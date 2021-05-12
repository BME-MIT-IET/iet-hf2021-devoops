# GitHub Actions inicializálása
GitHub Actions futtatásához a [/.github/workflows/](/.github/workflows/) mappában létrehoztuk a `maven.yaml` fájlt. A cél az volt, hogy minden mainre irányuló pull request és push hatására lefusson a build.
Itt azonban problémába ütköztünk. 
A `art.uniroma2.it.org.openrdf.sesame` repositoryt nem a https://mvnrepository.com/ -ról húztuk be, hanem a clojars.org-ról , a Maven pedig blokkolja a külső HTTP kéréseket.
Emiatt ún. Mirrort kellett használnunk. Ennek beállítása a [local-settings.xml](/.mvn/local-settings.xml) és [maven.config](/.mvn/maven.config) fájlokban található.

Lényegi része:
```xml
<mirrors>
    <mirror>
        <id>release-http-unblocker</id>
        <mirrorOf>central</mirrorOf>
        <name></name>
        <url>http://my-url/libs-release</url>
        <blocked>false</blocked>
    </mirror>
</mirrors>
```

Ezután már megfelelően működött a GitHub Actions, láthattuk, hogy a build sikeresen lefutott.
![image](https://user-images.githubusercontent.com/34817244/118017733-3ead3f00-b357-11eb-91ea-d710b74f1165.png)
