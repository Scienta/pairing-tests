# JSON-parts

Denne oppgaven bruker moderne Java.

Vi har et Java-grensesnitt [JsonMatcher](src/main/java/no/scienta/jsonparts/JsonMatcher.java).
Implementasjoner skal holde på et hoved-JSON-dokument, og deretter kunne avgjøre om andre
dokumenter er "delmengder" av dette.

Vi bruker Jackson som JSON-bibliotek, og jobber på Jacksons rekursive `JsonNode`-type, som modellerer et 
AST for JSON. 

## Problembeskrivelse

Grensesnittet ser ut som under.  Det er et `@FunctionalInterface`, så det er bare `match`-methoden 
trenger en implementasjon, men vi har også muligheten til å implementere `contains`:

```java
@FunctionalInterface
public interface JsonMatcher {

    @FunctionalInterface
    interface StructuralMatch {

        default boolean matches() {
            return unmatched().isEmpty();
        }

        Map<List<String>, JsonNode> unmatched();
    }

    default boolean contains(JsonNode part) {
        return match(part).matches();
    }

    StructuralMatch match(JsonNode part);
}
```
Her er en kompilerbar, men feil implementasjon av `JsonMatcher`:
```java
public record DefaultJsonMatcher(JsonNode node) implements JsonMatcher {

    @Override
    public StructuralMatch match(JsonNode part) {
        return Collections::emptyMap;
    }
}
```
Denne returnerer alltid en `StructuralMatch` uten noen avvik, altså vil den svare at
alle dokumenterer er delmengder.

Med begrepet "delmengde" mener vi at: 

* Alle løvnoder i en delmengde-JSON må også finnes i hoved-dokumentet, på samme sted 
  og med samme verdi
* Alle lister behandles som mengder (eng. "sets"), dvs. at rekkefølgen er uten 
  betydning, og antallet forekomster av en verdi er uten betydning.  Med andre ord: 
  En liste X er delmengde av liste Y hvis alle elementer i X også kan finnes i Y.

Om vi har dette hoved-dokumentet:
```json
{
  "foo": "bar", 
  "zot": {
    "answer": 42
  },
  "zip": [ 1, 6, 7 ]
}
```
er dette en delmengde:
```json
{
  "zot": {
    "answer": 42
  }
}
```
og dette: (`foo` matcher, både 6 og 1 finnes i hoved-dokumentets liste)
```json
{
  "foo": "bar", 
  "zip": [ 6, 1, 1, 1, 1 ]
}
```
men ikke dette: (Hoved-dokumentet har ikke 8 i lista)
```json
{
  "zip": [ 6, 7, 8 ]
}
```
eller dette: (Dette er noe helt annet!)
```json
{
  "somethingElse": "stuff"
}
```
eller dette: (Samme løvnode, feil sted)
```json
{
  "answer": 42
}
```
eller dette: (Samme løvnode, riktig sted, feil verdi)
```json
{
  "zot": {
    "answer": 54
  }
}
```
Vi har testen [JsonMatcherTest](src/test/java/no/scienta/jsonparts/JsonMatcherTest.java)
som viser flere eksempler.  De fleste av disse feiler, de som passerer gjør det av feil
grunn. 

## Løsningsstrategier

### Enkel start

I første runde kan vi implementere `contains`-metoden separat. Den trenger bare svare 
`true`/`false`. Dette vil fikse de fleste av testene.

### Full løsning

En full løsning implementerer `match`-metoden som returnerer en `Map` med
alle "veiene" gjennom del-dokumentet som _ikke_ kunne finnes i hoved-dokumentet.
En "vei" angis som en liste med strenger, f.eks. er `["zot", "answer"]` en vei
gjennom hoved-dokumentet over.

`contains`-metoden kan da skrives som sjekk av dette `Map`et.  Om det er
tomt, har vi en match.
