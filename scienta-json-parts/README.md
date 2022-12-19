# JSON-parts

Denne oppgaven er en variant av [scienta-json-parts-carte-blanche](../scienta-json-parts-carte-blanche/README.md) 
med ferdig oppsett for en maven-basert løsning i moderne Java (JDK 17).  


Vi har et Java-grensesnitt [JsonMatcher](src/main/java/no/scienta/jsonparts/JsonMatcher.java).
Implementasjoner skal holde på et hoved-JSON-dokument, og deretter kunne avgjøre om andre
dokumenter er "delmengder" av dette.

Vi bruker Jackson som JSON-bibliotek, og jobber på Jacksons rekursive `JsonNode`-type, som modellerer et
AST for JSON.

## Problembeskrivelse

Grensesnittet ser ut som under:

```java
@FunctionalInterface
public interface JsonMatcher {

    boolean contains(JsonNode part);
}
```
Vi har [en påbegynt implementasjon](src/main/java/no/scienta/jsonparts/DefaultJsonMatcher.java) som kan plukkes opp, og 
[en hjelpeklasse for å grave i JSON-noder](src/main/java/no/scienta/jsonparts/JsonUtils.java).

Med begrepet "delmengde" mener vi at:

* Alle løvnoder i en delmengde-JSON må også finnes i hoved-dokumentet, på samme sted
  og med samme verdi
* Alle lister behandles som mengder (eng. "sets"), dvs. at rekkefølgen er uten
  betydning, og antallet forekomster av en verdi er uten betydning.  Med andre ord:
  En liste X er delmengde av liste Y hvis alle elementer i X også kan finnes i Y.

Reglene for liste-sammenligninger har mye med bruksområdet å gjøre, så det er ikke noe problem om
løsningen definerer en annen logikk for dette.

Her følger noen eksempler med definisjonen som gitt over. Om vi har dette hoved-dokumentet:
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

(Om løsningen definerer regelen for liste-sammenligning annerledes må noen av testene
justeres.)

## Utvidelser

Mulige utvidelser av dette kan være:

### Hva feilet? 

I stedet for å returnere en enkel sannhetsverdi, kan vi se for oss at vi
heller returnerer informasjon om hva forskjellen på dokumentene er. Hvordan
kan en slik returverdi se ut?

### Kan vi parametrisere liste-oppførselen?

Hvordan kan vi implementere forskjellige tolkninger av hvordan to lister
skal sammenlignes?  Kan vi f.eks. lage en variant som sjekker rekkefølge,
antall forekomster etc.?

