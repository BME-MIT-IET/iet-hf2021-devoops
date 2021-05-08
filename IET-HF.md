# DevOops

## Feladat leírása

A kiválasztott repository CSV fájlból csinál RDF adatbázist (TTL formátumban, egy alap TTL template alapján).

A megadott példakódot második megbeszélésünk során ki is próbáltuk, és így láttuk mi is, hogyan működik (először telepíteni kellett a build toolt).

### Példakód

A repository gyökérkönyvtárában található README.md megad egy példakódot, amit saját gépen is lefuttathatunk, az [ant](http://ant.apache.org/bindownload.cgi?Preferred=https%3A%2F%2Fdownloads.apache.org%2F) Java build-tool segítségével.

A projekt tartalmaz egy `examples` mappát is, amiben egy minta CSV és TTL fájl is található.
Így a gyökérkönytvárban egy parancssor megnyitása után kiadható az alábbi parancs:

```java
java -jar dist/lib/csv2rdf.jar examples/cars/template.ttl examples/cars/cars.csv cars.ttl
```

Ennek hatására létrejön egy `cars.ttl`.
