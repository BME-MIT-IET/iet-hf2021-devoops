# BDD tesztek készítése

## Bevezetés

A BDD tesztek megvalósításához a [Cucumber](https://cucumber.io/) szoftvert használtuk. Első lépések közé tartozott a pom.xml kiegészítése a cucumber-java8, cucumber-java és cucumber-junit függőségekkel. Ezt követően részletesen tanulmányoztuk a hivatalos dokumentációt. A manuális teszteseteket alapul véve 6 darab Scenario-t határoztunk meg melyek a következők:

### 1 Program sikeres lefutásának ellenőrzése
```gherkin  
Scenario: Check if program runs
Given I have a default csv
When I start converting
Then I receive no errors
```
Ebben a tesztesetben ellenőriztük, hogy a megfelelő paramétereket megadva lefut-e a program. 

### 2 Generate TTL
```gherkin  
Scenario: Generate TTL
Given I have a default csv
When I start converting
Then I get converted file
```
Ebben a tesztesetben ellenőriztük, hogy amegfelelő paramétereket megadva  sikeresen létrejön-e az output file. 

### 3 Not enough arguments
```gherkin  
Scenario: Not enough arguments
Given I have a default csv
When I give 2 arguments
Then I do not get new file
```
Ebben a tesztesetben szándékosan kevesebb argumentumot adtunk meg, mint amennyire a programnak szüksége van, így a konvertálás sem ment végbe.
Az elvárt módon Exceptiont is kaptunk.

### 4 Too many arguments
```gherkin  
Scenario: Too many arguments
Given I have a default csv
When I give 4 arguments
Then I do not get new file
```
Ebben a tesztesetben szándékosan több argumentumot adtunk meg, mint amennyire a programnak szüksége van, így a konvertálás sem ment végbe.
Az elvárt módon Exceptiont is kaptunk.


### 5 Empty input file
```gherkin  
Scenario: Empty input file
Given I have an empty csv
When I start converting
Then I do not get new file
```
Ebben a tesztesetben input fileként egy üres csv-t adtunk meg, ennek következtében a konvertálás nem ment végbe. Exceptiont is kaptunk a következő üzenettel: Input file is empty!


### 6 Incorrect csv structure
```gherkin  
Scenario: Incorrect csv structure
Given I have an incorrect csv
When I start converting
Then I get an empty file
```
Ebben a tesztesetben szándékosan egy hiányos csv filet adtunk meg (oszlopok hiányoztak). Ennek következtében a konvertálás nem tudott végbemenni, de egy üres output file generálódott.

## Tanulságok
1. Step-ek újrahasznosítása: több hasonló Scenario is volt, melyekhez a Step-ek újból felhasználhatóak voltak, ezekhez azonban megfelelő paraméterezésre volt szükség. Ennek érdekében létrehoztunk egy `pathToCSV` stringet, melyben az aktuális elérési útvonat tároltuk, illetve egy `conversionArguments` tömböt, amiben a futtatáshoz szükséges argumentumokat állítottuk össze.

2. Újrafuttatható tesztesetek biztosítása: a konverziók során számos file létrejött, melyeket idempotens tesztesetek érdekében törölnünk kellett. Ennek manuális elvégzése helyett, felhasználtuk az `@After` annotációt, így minden Scenario után le tudott futni egy "törlő" művelet.

3. Garbage Collector bevezetése: az üres file létrehozásánál a fő program nem szabadította fel az erőforrásokat, emiatt nem lehetett a tesztek lefuttatása után kitörölni. Ennek kiküszöbölésére az `@After` annotációban meghívásra került a GC minden Scenario után.
