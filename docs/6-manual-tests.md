# Manuális tesztek

## Separator option
A csv szeparátorát ";"-vel teszteltük az alapértelmezett "," helyett. Ennek a fájljai az [alábbi mappában](../examples/separator) található. A program az elvárásoknak megfelelően működött. Létrehozta a [.ttl fájlt](../examples/separator/separator.ttl) a [.csv fájlból](../examples/separator/separator.csv).

## Escape option
A csv escape karakterét a default "\\" helyett "%"-kal teszteltük. A kapcsolódó fájlok az [alábbi mappában](../examples/escape) találhatók. A program helyesen lefutott. Létrejött a [.ttl fájl](../examples/escape/escape.ttl) a [.csv fájlból](../examples/escape/escape.csv).

## Quote option
A csv quote karakterét a kezdeti " helyett "+"-szal helyettesítettük. A kapcsolódó fájlok a [mappában](../examples/quote) találhatók. A program helyesen lefutott. Létrejött a [.ttl fájl](../examples/quote/quote.ttl) a [.csv fájlból](../examples/quote/quote.csv). Próbáltuk a "^" jellel is, de ezt a karaktert nem bírta feldolgozni, IllegalArgumentException keletkezett.
![](../examples/quote/exception.png)
Az egymásba ágyazott "-ek más karakterrel való helyettesítése sem működött.

## No-header option
A no-header option kipróbálása során számos problémába ütköztünk. Az általunk kiadott parancs a leírásnak megfelelő volt, mégis minden esetben Exception-t kaptunk. A csv fájlt is többféleképpen módosítottuk (üres fejléc sor, kihagyott fejléc, csak vesszőkből álló sor), illetve a parancs kiadását is variáltuk, a [README.md](../README.md)-ben írt help parancs segítségével is értelmeztük.
A kód debugolása közben arra juttottunk, hogy a hiba abban a kódrészben keletkezik, ahol még nem ellenőrzi, hogy van-e header, illetve a --no-header option be van-e állítva.
![](../examples/noheader/exception.png)

## Hibás adat tesztelése