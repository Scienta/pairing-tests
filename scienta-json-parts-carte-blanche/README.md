# JSON-parts

I denne oppgaven skal vi jobbe med JSON. Du kan bruke det språket og det JSON-biblioteket du liker best.

Hvis du vil komme fort i gang, finnes det [en variant av denne oppgaven med](../scienta-json-parts/README.md) 
et ferdig kjørende oppsett med tester, som bruker Maven og JDK 17.

## Oppgaven

Oppgaven er å lage en nyttig biblioteksfunksjon som kan avgjøre om et del-dokument er en "delmengde" av et
større, "hoved"-dokument.

Med begrepet "delmengde" mener vi i utgangspunktet at:

* Alle løvnoder i en delmengde-JSON må også finnes i hoved-dokumentet, på samme sted
  og med samme verdi
* Alle lister behandles som mengder (eng. "sets"), dvs. at rekkefølgen er uten
  betydning, og antallet forekomster av en verdi er uten betydning.  Med andre ord:
  En liste X er delmengde av liste Y hvis alle elementer i X også kan finnes i Y.

Reglene for liste-sammenligninger er ofte drevet av hvilke bruksområder vi har, så det er ikke noe problem om
løsningen definerer en annen logikk for dette.

## Eksampler

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

