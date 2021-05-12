# A build framework inicializálása

Az egyik technológiai fókuszú feladatnak a build keretrendszer beüzemelését választottunk. Bár a projekt már rendelkezett egy [Ant](https://ant.apache.org/) frameworkkel, korábbi tapasztalatainkhoz közelebb állt a [Maven](https://maven.apache.org/), így végül arra esett a választásunk.

## Részfeladatok

A feladat megvalósítása során az alábbi részfeladatok megoldására volt szükség:

### Project konvertálása

Első lépésként a meglévő Java projektet importáltuk az Eclipse IDE-be, majd a beépített opciókat felhasználva átalakítottuk Maven projektté.

1. Projekt nevén jobb klikk
2. Configure
3. Convert to Maven project

Ennek hatására megváltozott a projekt felépítése, és létre lett hozva a `pom.xml`.

### `Pom` fájl kiegészítése függőségekkel

Az így létrejött `pom.xml` fájlt ezután ki kellett egészíteni, hogy tartalmazza az összes, projekt által használt könyvtárat. Míg korábban ezek a `Build Path` részét képezték, és a repositoryban is benne voltak, a Mavennek köszönhetően ezeket mind a két helyről el lehetett távolítani, és egy helyen, ebben a `pom.xml` fájlban, `<dependencies>` címkén belül definiálni.

Két függőség kivételével valamennyit elsőre megtalált ezek alapján a létrehozott projekt, viszont a `sesame-onejar` és az `airline` függőségek esetében hosszabb kutakodásra volt szükség.

#### `sesame-onejar`

A függőségnél a problémát az okozta, hogy egyszerűen nem volt megtalálható alapból a maven repositoryban (se a [sonatype](https://search.maven.org/), se a [mvn repository](https://mvnrepository.com/)ban) a `jar` fájl.

A problémát végül úgy sikerült feloldalni, hogy a megtalált bejegyzésnél a `Used By` sorban megjelenített repositorykat elkezdtük megnézni, és találtunk bennük `pom.xml` fájlokat is, és ahol ugyanaz a függőség volt definiálva, ami nekünk is kellett, ott még előtte egy `<repositories>` nevű címke is [bevezetésre került](https://repo1.maven.org/maven2/it/uniroma2/art/owlart/owlart-sesame2impl/1.5/owlart-sesame2impl-1.5.pom).

Ezt átvettük, és így már a mi projektünkben is megfeleően be lett olvasva a függőség.

#### `airline`

A CLI kialakításáért felelős, annotációkat használó függőség behúzása jelentette a legnagyobb problémát. Hiába választottunk ki különféle verziókat (az eredeti GitHub repositoryban `0.7.3` verzió szerepel, ami nem létezik egyik maven repositoryban sem), mindig az alábbi hibá egyikét dobta:

- `io.airlift.command.*` nem található
- `title` paraméter értékének `String`-nek kéne lennie, de `String[]`-et kapott

Mind a két problémának a gyökerében az állt, hogy az eredeti GitHub repositoryban található `.jar` fájl egy máshol nem létező változatát tartalmazza ennek a függőségnek – mind a két problémára a megoldást a behúzott `.jar` fájlok tanulmányozása során találtuk.

Régebbi (4, 5) verziókban még szerepelt az `io.airlift.command` package, későbbiekben ez azonban le lett cserélve `io.airlift.airline` package-re, így az importálás nem működött megfelelően.

Az importokat átírva azonban a másik probléma ütötte fel a fejét: a `title` paraméter.
A GitHub repositoryban tárolt könyvtár így tartalmazza az osztályt:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Arguments {
    String[] title() default { "" };

    String description() default "";

    String usage() default "";

    boolean required() default false;
}
```

És bármely másik, maven repositorykból behúzott változatban már így szerepel:

```java
@Retention(RUNTIME)
@Target(FIELD)
public @interface Arguments
{
    /**
     * Name of the arguments.
     */
    String title() default "";

    /**
     * A description of the arguments.
     */
    String description() default "";

    /**
     * Argument usage for help.
     */
    String usage() default "";

    /**
     * Whether this arguments are required.
     */
    boolean required() default false;
}
```

Vagyis nem `String[]` típusú adatot vár, hanem egyszerűen csak egy `String`-et – az alkalmazásban pedig 3 argumentumra van szükségünk.

Ezután kiterjesztettük a keresést, és nem csak az `io.airlift` groupban kerestünk, és meg is találtunk egy olyan változatot, ami bár kicsit eltér a GitHubon tárolttól, mégis behúzható és működik:

```xml
<dependency>
  <groupId>com.github.rvesse</groupId>
  <artifactId>airline</artifactId>
  <version>2.8.1</version>
</dependency>
```

Így a fő java fájlban az importokat át kellett írni, de `mvn clean` és `mvn install` után a generált `.jar` fájl működése megegyezik az eredetivel.

### `Pom` fájl kiegészítése pluginokkal

Ahhoz, hogy az előző feladatban említett `.jar` fájlt generálni tudjuk, szükségünk volt néhány pluginra.

1. `maven-jar-plugin`
   Ennek segítségével hozható létre maga a `.jar` fájl, és az `<archive>` megadásával a belé generált `MANIFEST.MD`.

2. `maven-dependency-plugin`
   Ezen plugin segítségével pedig a definiált függőségek kerülnek bele a futtatható fájlba, nem kell kézzel megadni mindet.
