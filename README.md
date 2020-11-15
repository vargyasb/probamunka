
# GB Solutions probafeladat

# A feladat:

A cél egy több forrásból adatokat gyűjtő álláskereső API létrehozása.
A feladatot Java 11 nyelven, Spring framework segítségével valósítsd meg. Adatok tárolására
bármilyen SQL vagy NoSQL adatbázist használhatsz.

## Követelmények:

1. Az alkalmazás biztosítson lehetőséget kliensalkalmazások regisztrációjára (POST /clients). A
kliens átadja a nevét (validáció: max 100 karakter), e-mail címét (validáció: érvényes email
cím formátum, bármilyen regexp használatával, valamint egyediség ellenőrzése). A
responseban a szerver egy api kulcsot ad vissza UUID formátumban.

2. Az alkalmazás biztosítson lehetőséget állások létrehozására (POST /positions). A kliens
átadja az állás megnevezését(validáció: max 50 karakter), a munkavégzés földrajzi
helyét(validáció: max 50 karakter). A szerver első lépésben ellenőrzi az api kulcs
érvényességét. Nem érvényes api kulcs esetén hibaüzenettel tér vissza. A szerver mentse el
az állást, majd térjen vissza egy URL-lel a responseban, hogy milyen oldalon érhető el a
pozició.

3. Az alkalmazás biztosítson lehetőséget állások keresésére (GET /positions). A kliens
átadja a keresett keywordöt (pl.: "finance", validáció: max 50 karakter) valamint a
lokációt (pl.: "london", validáció: max 50 karakter). A szerver első lépésben ellenőrzi az
api kulcs érvényességét. Nem érvényes api kulcs esetén hibaüzenettel tér vissza.
Érvényes api kulcs esetén az átadott adatokkal bekérdez a következő helyekre:
○ GitHub Job API (https://jobs.github.com/api)
○ Adatbázisban tárolt állások

4. A fenti adatokat össze kell fésülni és a kliens alkalmazásnak visszaadni a következő
adatokat:
○ Job title - az állás megnevezése
○ Location - a munkavégzés földrajzi helye
○ URL - a hírdetéshez tartozó URL

A feladat megoldása közben ha bármilyen problémába ütközöl az API használata során
(nem elérhető, bizonytalanul elérhető), akkor bármilyen más, ingyenes API-t használhatsz a
megoldáshoz (Itt találsz egy listát az elérhető API-król:
https://github.com/toddmotto/public-apis#jobs).

A megoldás során próbáld generikusan kialakítani a rendszert, hogy igény esetén könnyen
tovább bővíthető legyen plusz adatforrások bevonásával.

A szerver validációs hibák esetén egységes hibatípussal térjen vissza, részletezve, hogy
milyen mezőkkel milyen validációs hiba történt.

A megoldást kérlek, töltsd fel GitHub vagy GitLab-ra és commitold lépésről-lépésre, hogy
lássam, milyen fázisokban haladtál. Készíts hozzá egy rövid leírást is, ami leírja, hogyan
indítható el az alkalmazás és a hozzá tartozó tesztek.

A feladat kb 4 órát vesz igénybe, amennyiben nem sikerül időn belül megoldani a feladatot,
akkor térj ki arra, hogy melyik részek nincsenek készen és mennyi idő lenne implementálni a
többi részt is.

A fejlesztés után sorolj fel egy legalább 5 elemű listát arról, hogy milyen további lépéseket
tennél ezzel a projekttel kapcsolatban, hogy teljes mértékben production ready alkalmazás
legyen és az üzemeltetés is elfogadja tőlünk ezt az alkalmazást.

## A megoldásról:

*H2 inmemory adatbázist használtam, hogy nálatok is könnyen futtatható legyen az alkalmazás.
Körülbelül 12 órát foglalkoztam a projekttel, sajnos a tesztekkel nem lettem kész és a 3. feladatban
lévő api kulcs validációt sem tudtam implementálni valamit a generikussá tétellel sem tudtam foglalkozni. Ezzel kapcsolatban az a kérdésem (amennyiben még aktuális),
hogy milyen formában kellett volna validálni a kulcsot? Bárki lekérdezhet vagy csak regisztrált kliensek kérdezhetnek az api-tól?
A https://jobs.github.com/positions?json végpontba én regisztráció nélkül is be tudtam kérdezni, itt lehetséges, hogy nálam van egy kis fogalomzavar.*

POSTMAN-ben teszteltem fejlesztés közben az alábbi módon:

**1** - POST az alábbi címre az alábbi adatokkal:  
 http://localhost:8080/clients
 
    {
       "email" : "test@gmail.com",
       "name" : "testUser"
    }
 
 **Válasz UUID:**  
  723ada24-c3c0-4207-8eac-33c4a51a4ed1
 
 **2** - POST az alábbi címre az alábbi adatokkal:  
  http://localhost:8080/positions
  
     {
        "clientID" : "AZ ELŐZŐ POST UTÁN VISSZAKAPOTT UUID",
        "title" : "Developer",
        "location" : "London"
     }
  
  Válasz az alábbi formátumban, ahol a path utáni rész a Position ID-ja:  
   http://localhost:8080/positions/2d90f6a6-7a48-4039-8d16-6b09b1110796

**3** - GET a következő címre: http://localhost:8080/positions  
  Válasz az alábbi formátumban:
  
    {
       "title": "Developer",
       "location": "London",
       "clientID": "723ada24-c3c0-4207-8eac-33c4a51a4ed1",
       "url": "http://localhost:8080/positions/2d90f6a6-7a48-4039-8d16-6b09b1110796"
    },
    {
       "title": "Full Stack Developer (Mid-Level or Senior) ",
       "location": "North America",
       "clientID": null,
       "url": "https://jobs.github.com/positions/fc91fd74-104d-4d9b-ba0b-b8a31d6ee298"
    }
    
**4** - GET a következő címre: http://localhost:8080/positions/2d90f6a6-7a48-4039-8d16-6b09b1110796  
 Válasz az alábbi formátumban:  
    
    {
      "title": "Developer",
      "location": "London",
      "clientID": "723ada24-c3c0-4207-8eac-33c4a51a4ed1",
      "url": "http://localhost:8080/positions/2d90f6a6-7a48-4039-8d16-6b09b1110796"
    }

**5** - GET a következő címre: http://localhost:8080/positions/?title=developer&location=newyork  
 Válasz az alábbi formátumban:  
     
     {
           "title": "Full Stack Engineer",
           "location": "New York City",
           "url": "https://jobs.github.com/positions/4926de97-addf-4f0e-afde-44b83dad5d73"
     }
   
   
   
