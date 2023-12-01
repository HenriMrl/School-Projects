# BooksAndWords

Books and words on kurssin harjoitustyön aihe. Joinakin vuosina pakollinen, joinakin valinnainen, joten tarkista tilanne kun osallistut kurssille, kyseisenä vuonna.

## Lyhyt esittely

Sovellus:

1. Lukee tekstitiedostoa (Unicode, UTF-8 encoded) joka sisältää ison määrän tekstiä, kuten esimerkiksi kirja. Kutsumme tätä ohjeissa sanalla "kirja" ja koodissa sanalla "book".
2. Lukee toisen pienemmän tekstitiedoston (Unicode, UTF-8 encoded) joka sisältää sanoja joita *ei huomioida*. Tätä kutsutaan ignore-tiedostoksi ja ignore-sanoiksi.
3. Lukiessaan kirjatiedostoa ja sieltä sanoja, sovellus katsoo onko sana ignoorattava, ja jos ei ole, laskee kaikkien tällaisten kirjan uniikkien sanojen esiintymismäärän kirjatiedostossa.
4. Tulostaa laskevassa järjestyksessä 100 yleisintä sanaa jotka esiintyivät kirjassa, mukaanlukien esiintymismäärän.

Sovelluksen täytyy toimia oikeellisesti ja olla aikatehokas. Suurimmalla testiaineistolla, `Bulk.txt`, suoritusajan pitää olla korkeintaan muutamia sekunteja (olettaen keskimääräisen tehokkaan tietokoneen).

**TÄRKEÄ HUOMIO** Toteuttaessasi tätä projektia, **et saa** käyttää mitään Javan tietosäiliöluokkia tai algoritmeja. Et siis voi käyttää mitään luokkia jotka toteuttavat suoraan tai epäsuorasti Javan `Collection` tai `Map` rajapinnan. Mitään valmiita algoritmeja Javan kirjastoista, esimerkiksi luokissa `Arrays` tai `Collections` **ei saa käyttää**.

Sen sijaan **sinun tulee käyttää** niitä soveltuvia tietorakenteita ja algoritmeja jotka olet itse toteuttanut tämän kurssin harjoituksissa. Esimerkiksi harjoituksessa `67-phonebook` toteutetut tietorakenteet ovat tässä erittäin käyttökelpoisia, samoin algoritmit luokassa `Algorithms` jota olet täydentänyt kurssin harjoituksissa. Tässä ei kannata alkaa tekemään näitä jo tehtyjä asioita uudestaan ja erikseen vain tähän sovellukseen, vaan uudelleenkäytät aikaisemmin toteuttamiasi geneerisiä algoritmeja ja tietorakenteita.

Valmiina annettu koodi sisältää JavaDoc -muodossa olevaa dokumentaatiota kommenteissa. Jos haluat generoida tästä HTML dokumentaation, voit tehdä sen komentorivillä projektin juurihakemistossa komennolla `mvn javadoc:javadoc`. Tämän jälkeen dokumentaatio löytyy tiedostosta `target/site/apidocs/index.html`.

Alla oleva UML:n luokkamalli kuvaa sovelluksen rakenteen.

![UML class diagram](classes.png)

## Yksityiskohtaiset vaatimukset

Kun käsittelet tiedostoja ja raportoit tuloksia, huomaa seuraavat rajoitteet ja vaatimukset:

1. Kirjatiedostot ovat vain tekstiä sisältäviä tiedostoa UTF-8 enkoodattuna.
1. Ignore -sanoja sisältävä tiedosto on vain tekstiä ja myös UTF-8 enkoodattu.
1. Ignore -tiedostossa ignoorattavat sanat ovat toisistaan pilkuilla eroteltuina, eikä välilyöntejä ole.
1. Kun sovellus laskee sanoja, koko kirjatiedosto on käsiteltävä ensimmäisestä viimeiseen merkkiin.
1. Kaikki ignoorattavat sanat ignore -tiedostosta on otettava huomioon.
1. Merkkitietoa lukiessa tiedostoista, on tekstiä luettava eksplisiittisesti Unicode UTF-8 enkoodattuna, ASCII, Latin-1 tai mikään muukaan käyttöjärjestelmäkohtainen oletus ei käy.
1. Kun tulkitaan milloin *kirjan* sana vaihtuu tai katkeaa, tämän määrittelyssä on käytettävä Javan `Character.isLetter(int)` metodia. Eli kun tulee vastaan merkki joka ei tämän metodin paluuarvon mukaan ole "letter", silloin edelliset luetut merkit (jos niitä on), muodostavat sanan.
1. Sanat, joiden pituus on yksi merkki, ignoorataan aina. (*sen jälkeen* kun sana on yllämainitulla tavalla luettu kirjatiedostosta).
1. Sanat on normalisoitava pieniksi kirjaimiksi ennen käsittelyä. Näin sana "Hello" ja "hello" katsotaan samaksi sanaksi.
1. Sovelluksen toiminnallisuus on toteutettava luotava luokka joka toteuttaa annetun `Book` -rajapinnan. 
1. `Book` rajapintaa ei saa millään tavalla muuttaa, siihen mitään lisätä tai siitä mitään poistaa.
1. Kun uniikit sanat esiintymismäärineen on löydetty, sovelluksen täytyy tulostaa top100 lista jossa *laskevassa järjestyksessä* esiintymismäärän mukaan tulostetaan sanat ja niiden esiintymismäärä.
1. Jos kirjassa ei ollut sataa (100) sanaa, tulostetun listan on oltava tietysti lyhyempi ja sisältää vain löydetyt uniikit sanat.
1. Lisäksi sovelluksen on tulostettava alempana tässä dokumentissa kuvatut laskurit.
1. `Book.java` sisältää metodeja jotka on merkitty "For testing and evaluation". Näiden pitää palauttaa valideja arvoja. Näitä käytetään automaattisissa testeissä varmistamaan että toteutus löytää oikeita sanoja oikeita määriä kirjoista. Jos nämä eroavat liikaa testien odottamista sanoista tai määristä, testi ilmoittaa toteutuksen epäonnistuneen (testi epäonnistuu).
1. Missään `Book` rajapinnan toteuttavan luokan metodeissa ei saa olla mitään interaktiota käyttäjän kanssa. Käyttäjältä ei saa näiden metodien toteutuksessa kysyä mitään inputtia. Tämä näkyisi testeissä siten että aikamittaukset osoittavat koodin olevan erittäin hidasta. Näin ollen testit kertovat sovelluksen olevan liian hidas ja se hylätään aikatehokkuudeltaan huonona toteutuksena.

## Book -rajapinnan toteutus
 
Toteuta `Book` -rajapinta siten että se täyttää vaatimukset ne rajoitteet huomioiden mitä kurssin töille on annettu. Huomaa että teet uuden .java -tiedoston jossa toteutat kyseisen Book -rajapinnan. Älä siis koodaa tätä projektissa valmiina annettuihin .java -tiedostoihin.

Kun toteutus on testattavissa, toteuta valmiina annettuun metodiin `BookFactory.createBook()` koodi joka luo instanssin omasta Book -toteutuksestasi ja palauttaa sen kutsujalle.
 
**Lue tarkkaan** Book -rajapinnan metodien kuvaukset lähdekoodissa olevista kommenteista, ja noudata myös näitä ohjeita toteutuksessasi.

Book -rajapinnan metodeja kutsutaan aina seuraavassa järjestyksessä:

1. `setSource(String, String)`, jossa annetaan parametreina analysoitavan kirjatiedoston nimi hakemistopolkuineen. Toteutuksesi ei saa millään tavalla muuttaa tätä annettua tiedoston nimeä tai hakemistopolkua vaan käyttää sitä sellaisenaan.
1. `countUniqueWords()`, tämä käynnistää varsinaisen prosessoinnin; ignore -tiedosto avataan ja luetaan ignore-sanat muistiin; kirjatiedosto avataan ja aletaan käsittelemään sieltä poimittuja sanoja, kuten on kuvattu yllä vaatimuksissa.
1. `report()`, jossa tulostetaan top-100 lista, lajiteltuna sanojen esiintymismäärän mukaan laskevaan järjestykseen, plus muu raportoitavat tieto (katso alumpaa ja alhaalta mitä tämä tarkoittaa).
1. `close()`, jossa vapautetaan kaikki sovelluksen varaamat resurssit (muisti, avoinna olevat tiedostot jos niitä on, jne.) ennenkuin sovellus myöhemmin lopetetaan.
 
 Esimerkki:

 ```Java
  Book theBook = BookFactory.createBook();
  theBook.setSource(bookFile, wordsToIgnoreFile);
  theBook.countUniqueWords();  
  theBook.report();
  theBook.close();
```

## Raportointi ja sanojen laskurit

Sovelluksen täytyy tulostaa tietoja siitä mitä kirjatiedostosta löyty, yllä kuvatulla tavalla. Tämä tulostus tapahtuu metodin `Book.report()` toteutuksessa.

Tässä on tulostettava seuraavaa:

1. Tulostetaan top 100 lista sanoista, lajiteltuna laskevaan järjestykseen sanojen esiintymismäärän perusteella (yleisin sana siis ensin).
2. Jos kirjassa oli vähemmän kuin 100 sanaa, tulostetaan vain ne sanat.
3. Tulostetaan top 100 listan *jälkeen*:
   * kirjan sanojen kokonaismäärä.
   * uniikkien sanojen määrä kirjassa.
   * ignore -sanojen lukumäärä.
   * kuinka monta sanaa kirjasta ignoorattiin kaiken kaikkiaan (mukaan lukien yhden merkin kirjaimet).

Lisäksi tässä on tulostettava tietoa joka kertoo tietorakenteesi tehokkuudesta ja toiminnallisuudesta. Jos esimerkiksi käytit toteutuksessa hajautustaulua, tulosta näkyviin kunka monta törmäystä tapahtui kun taulukkoon lisättiin sanaa hajautusfunktion avulla. Jos taas käytit toteutuksessa binääristä hakupuuta, tulosta puun korkeus, eli montako askelta on puun juuresta syvimmällä olevaan lehteen.

Nämä tulostukset auttavat siinä, että voit arvioida esimerkiksi hajautusavaimen tehokkuutta -- jos törmäyksiä tulee paljon suhteessa tiedon määrään, hajautusfunktiossa on korjattavaa, tai hajautustaulu on liian pieni tiedon määrään nähden. Voit sitten säätää ja parantaa toteutustasi ottaen huomioon tämän informaation. Tämä myös nopeuttaa sovelluksen toimintaa; mitä vähemmän aikaa käytetään törmäysten käsittelyyn, sitä nopeampi sovellus on. Mitä nopeampi sovellus on, sitä todennäköisemmin saat tästä paremmat pisteet.

## Vihjeitä

Jos toteutus kaatuu omituisesti isoilla tietomäärillä, ja käytät jotain rekursiivista algoritmia (esimerkiksi quicksort), lue ohje [WHAT-STACKOVERFLOW.md](WHAT-STACKOVERFLOW.md). 

**Tutki** kurssin materiaaleja nopeista tietorakenteista isoille tietomäärille (esim. hajautustaulut, binääriset hakupuut). Nämä ovat kaksi ilmeisintä ratkaisua tähän ongelmaan. Muitakin ratkaisuja toki voi käyttää, kunhan ne ovat oikeellisia ja erityisesti aikatehokkaita, ja testit menevät läpi.

## Testaus

Testaa toteutustasi kuten tähänkin mennessä kurssin harjoituksissa.

HUOM: Testit käyttävät kirjatiedostoja hakemistosta `src/test/resources/`. Sieltä löytyy tiedosto `testfiles.zip`. **Pura** tämä tiedosto ennen testien ajamista tähän samaiseen hakemistoon. Muuten testit eivät löydä tiedostoja. Yksinkertaisinta on tehdä tämä komentorivillä, jos `unzip` (tai vastaava) työkalu on käytettävissä:

```console
unzip testfiles.zip
```
Windows -käyttäjät voivat purkaa tiedoston sisällön myös Tiedostonhallinta -sovelluksella, mikä sen nimi nyt onkaan missäkin Windowsin versiossa (resurssienhallinta?).

Testit `BasicTests` -testitiedostossa käyttävät hakemistossa `src/test/resources/` olevia kirja- ja ignore-tiedostoja. Näiden tiedostojen sanamäärät ja sanat on laskettu etukäteen, ja testit vertailevatkin oman toteutuksesi löytämiä sanoja/sanamääriä näihin tunnettuihin sanoihin/sanamääriin ja sijainteihin top100 -listalla. Jos testien mielestä koodisi antamat tulokset eroavat liikaa, testi ilmoittaa että se on epäonnistunut. Mieti missä vika ja korjaa se.

`PerformanceTests` -testit suorittavat toteutuksesi testejä kaikkien kirjatiedostojen kanssa ja mittaavat suoritusaikaa kirjan koon suhteen. Nämä mitatut suoritusajat muine tietoineen tallennetaan tiedostoon `compare.csv`. Tässä tiedostossa löytyy pilkuilla eroteltuina erilaisia mittaustietoja. Kaikki taulukkolaskentasovellukset osaavat avata tai tuoda tällaisen tiedoston jonka jälkeen voit taulukkolaskinsovelluksessa (Excel, Numbers, Sheet, jne.) analysoida tuloksia kuten teit `02-mode` -harjoituksessa.

Kun suoritat näitä suorituskykytestejä, tee se mieluummin komentoriviltä. Näin minkään IDEn (VS Code, Eclipse) puuhastelut siinä samalla eivät hidasta testiajoja. Muutenkin huolehdi siitä ettei taustalla pyöri mitään prosessoria tai levyä paljon käyttäviä ohjelmia -- ne kaikki vievät suoritusaikaa testeiltä.

Analysoi taulukkolaskinsovelluksella sitä, miten aineiston koon kasvaessa suoritusaika kasvaa. Onko kasvu lineaarista, logaritmista, linearitmista, neliöllistä, kuutiollista, exponentiaalista vai peräti faktoriaalista? Analysoi myös käyttämiäsi algoritmeja ja niiden aikakompleksisuutta ja vertaa analyysisi tuloksia taulukkolaskinohjelman piirtämiin graafeihin. Mitä ne kertovat toisistaan?

## Toimitus

Ennenkuin palautat tämänkin suorituksen, tee raportti markdown -muodossa tiedostoon `REPORT.md` samaan hakemistoon missä tämäkin tiedosto on. 

Tässä raportissa, käsittele seuraavia asioita:

1. **Toteutusvaihtoehdot mitkä valitsit tähän ratkaisuun**; mitä tietorakenteita ja/tai algoritmeja käytit. Miten pidät tallessa sanoja ja niiden esiintymismääriä? Minkälainen on käyttämäsi hajautusfunktio? Miten saat varsinaisesta tietorakenteesta jossa uniikit sanat laskureineen, lajitellun top100 -taulukon?
2. Mitä voit sanoa toteutuksesi **oikeellisuudesta**? Toimiiko se, jäikö jotain bugeja joita et saanut ratkottua?
3. Mitä voit sanoa toteutuksen **aikakompleksisuudesta**? Erittele eri ratkaisujen aikakompleksisuutta ja mikä on kokonaisuuden aikakompleksisuus? Esimerkiksi mikä on yhden kirjan sanan käsittelyn aikakompleksisuus ja mikä on käyttämäsi lajittelualgoritmin aikakompleksisuus?
4. Mikä oli mielestäsi **kaikista vaikein** asia ymmärtää ja/tai toteuttaa tässä työssä? Miksi?
5. Mitä opit tätä tehdessäsi, ja ylipäätään koko kurssilla? Mitä olisit vielä halunnut oppia lisää?=

Tallenna myös kuva (screenshot) taulukkolaskinohjelman graafista jossa näkyy suoritusajan suhde datan määrään.

Muista **lisätä nämä tiedostot myös versionhallintaan**: `compare.csv`, `REPORT.md` ja screenshot -kuvatiedosto. 

Tarkista että kaikki on versionhallinnassa, myös etärepositoryssä!
