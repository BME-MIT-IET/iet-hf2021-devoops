# Manuális kódátvizsgálás
## A CSV2RDF osztály
A kód elején levő CSV2RDF osztály implementálja a Runnable interfészt, ezért felülírja a run() függvényt. A programnak megadott opciók ennek az osztálynak a tagváltozói (no-header, separator, quote, escape), illetve a bemeneti argumentumok (template, input és output fájl) is itt kapnak értéket. Ez az osztály tartalmazza az összes alábbi osztályt.
### A run függvény
Először ellenőrzi a bemeneti paraméterek helyességét, beolvassa a bementi fájlokat és létrehozza vagy felülírja a kimeneti fájlt. Egy CSVReader segítségével beolvassa az input fájlt, és a template alapján elkészíti a kimeneti RDF-et a Template osztály használatával. Ez a függvény hívódik a main-ből, ez a program magja.

## A Template osztály
### insertPlaceholders függvény
A template fájlban megkeresi egy regex segítségével a placeholdereket, ahova majd az rdf állítás egyes részeit.
### parseTemplate
Ez a függvény hívódik a konstruktorban. Egy RDF handler segédosztályt hoz létre, amiben az rdf adatbázishoz szükséges namespace-eket is kezeli, valamint összeállítja az rdf állításokat a value- és a statementgenerator segítségével. Ezenkívül a lentebb definiált osztályok segítségével feldolgozza a template-et.
### valueProviderFor függvény
A template fájl alapján eldönti, hogy milyen valueProvider-re lesz szükség. Ezt hívja meg az insertPlaceholders.
## A StatementGenerator osztály
Ez az osztály építi fel az RDF állítást. Három tagváltozója a subject, predicate és az object.
### A generate függvény
RDF állítás generálásáért felel a paraméterül kapott adatokból a konstruktorban beállított értékek alapján.
## A ValueProvider osztály
Absztrakt ősosztály. A provideValue absztrakt függvényét valósítják meg a leszármazottak.
### A RowValueProvider osztály
A ValueProvider leszármazottja. Override-olt függvényében kiolvassa a csv fájlból a sor- és oszlopindex alapján a megfelelő értéket.
### A RowNumberProvider osztály
A ValueProvider leszármazottja. Override-olt függvénye visszaadja a kapott sor indexét.
### A UUIDProvider osztály
A ValueProvider leszármazottja. UUID-t generál az egyes sorokhoz.
## ValueGenerator interfész
Egy generate függvényt deklarál.
### ConstantValueGenerator
A ValueGenerator interfészt implementálja. A konstruktorban kapott értéket adja vissza a generate függvény.
### BNodeGenerator
A ValueGenerator interfészt implementálja. A generate függvény egy RDF csomópontot ad vissza.
### TemplateValueGenerator
Absztrakt osztály, ami a ValueGenerator-t implementálja. Az applyTemplate függvény a template-ben lévő placeholdereket cseréli ki a ValueProviderek által szolgáltatott értékekre.
#### TemplateURIGenerator
Az előbbi absztrakt osztályból származik le. Az interfész generate függvényét override-olja az ős applyTemplate függvényének segítségével.
#### TemplateLiteralGenerator
A TemplateURIGenerator-hoz hasonló, annyiban tér el, hogy a literálisokat hozza létre az URI helyett.