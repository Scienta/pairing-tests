# JSON-parts

Denne oppgaven bruker moderne Java.

Vi har et Java-grensesnitt [JsonMatcher](src/main/java/no/scienta/jsonparts/JsonMatcher.java).
Implementasjoner skal holde på et hoved-JSON-dokument, og deretter kunne avgjøre om andre
dokumenter er "delmengder" av dette.  Vi bruker Jackson som JSON-bibliotek.

## Problembeskrivelse

Grensesnittet ser ut som under.  Det er et `@FunctionalInterface`, så det er bare `match`-methoden 
trenger en implementasjon:

```java
public interface JsonMatcher {

    @FunctionalInterface
    interface StructuralMatch {

        default boolean matches() {
            return unmatched().isEmpty();
        }

        Map<List<String>, JsonNode> unmatched();
    }

    default boolean contains(JsonNode part) {
        return match(part).unmatched().isEmpty();
    }

    StructuralMatch match(JsonNode part);
}
```

Begrepet "delmengde" defineres som sådan: 

* Alle løvnoder i delmengden må også finnes i hoved-dokumentet, med samme verdi
* Alle lister behandles som mengder ("sets"), dvs. at rekkefølge ikke er 
  av betydning. Med andre ord: En liste X er delmengde av liste Y hvis alle
  elementer i X også kan finnes i Y.

Om vi har dette hoved-dokumentet:
```json
{
  "foo": "bar", 
  "zot": {
    "answer": 42
  },
  "zip": [ 1, 2, 3, 4, 5, 6, 7 ]
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
og dette:
```json
{
  "foo": "bar", 
  "zip": [ 6, 1 ]
}
```
men ikke dette:
```json
{
  "zip": [ 6, 7, 8 ]
}
```
eller dette:
```json
{
  "somethingElse": "stuff"
}
```
eller dette:
```json
{
  "zot": {
    "answer": 54
  }
}
```
Vi har testen [JsonMatcherTest](src/test/java/no/scienta/jsonparts/JsonMatcherTest.java)
som viser flere eksempler. 

## Løsningsstrategier

### Enkel start

I første runde kan vi implementere `contains`-metoden som bare svarer 
`true`/`false`.

Avhengig av hvordan løsningen er strukturert, kan det være naturlig å gå
videre til neste steg: 

### Full løsning

En full løsning implementerer `match`-metoden som returnerer en `Map` med
alle "veiene" gjennom del-dokumentet som _ikke_ kunne finnes i hoved-dokumentet.
En "vei" angis som en liste med strenger, f.eks. er `["zot", "answer"]` en vei
gjennom hoved-dokumentet over.

`contains`-metoden blir da redusert til en sjekk av dette `Map`et.  Om det er
tomt, har vi en match.

