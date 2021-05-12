# Manuális kódátvizsgálás
## A CSV2RDF osztály
A kód elején levő CSV2RDF osztály implementálja a Runnable interfészt, ezért felülírja a run() függvényt. A programnak megadott opciók ennek az osztálynak a tagváltozói (no-header, separator, quote, escape), illetve a bemeneti argumentumok (template, input és output fájl) is itt kapnak értéket.
### A run függvény
Először ellenőrzi a bemeneti paraméterek helyességét, beolvassa a bementi fájlokat és létrehozza vagy felülírja a kimeneti fájlt. Egy CSVReader segítségével beolvassa az input fájlt, és a template alapján elkészíti a kimeneti RDF-et. Ez a függvény hívódik a main-ből, ez a program magja.

## A Template osztály
## A StatementGenerator osztály
Ez az osztály építi fel az RDF állítást. Három tagváltozója a subject, predicate és az object.
### A generate függvény
RDF állítás generálásáért felel a paraméterül kapott adatokból a konstruktorban beállított értékek alapján.
## A ValueProvider osztály
Absztrakt ősosztály. A provideValue absztrakt függvényét valósítják meg a leszármazottak.
### A RowValueProvider osztály
### A RowNumberProvider osztály
### A UUIDProvider osztály

## ValueGenerator interfész
### ConstantValueGenerator
### BNodeGenerator
### TemplateValueGenerator
#### TemplateURIGenerator
#### TemplateLiteralGenerator