# Woche1

https://propra.d.stups.hhu.de/ws25/570c6179c21a010/index.html

## HTML struktur vorlage

```HTML
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title></title>
    </head>

    <body>
    
    </body>

</html>
```

## HTML verben

HTML Verben bzw HTLM Methoden sind eine Information darüber um was für eine Art von request HTML an den Server (oder an ein anderes Ziel) schickt. 

|Verb|Funktion|Bemerkung|
|-|-|-|
|GET|Läd eine Resource| GET ist save, also darf keine Seiteneffecte haben. Das heißt, dass GET weder die Resource noch irgendetwas anderes ändern darf. <br> 
||| Get darf keine sensiblen Daten holen, da diese in der URL sichtbar sind.|
|||Wird ausgelöst durch: <br> - Eingabe einer URL <br>- Tags im HTML (img, link, …) <br>- Klick auf einen Link <br>- Abschicken eines Formulars|
|PUT|Erzeugt eine Resource|PUT ist auch idempotent.|
|||wird ausgelöst durch javascript|
|DELETE| Löscht eine Resource | DELETE ist idempotent, dass heist es ist egal wieoft man DELETE hintereinander mit den gleichen Parametern aufruft. Es passiert immer das gleiche.|
|||Problem: man muss die URL der neuen Resource wissen. Diese Entscheidung wollen wir allerdings der Datenbank überlassen.|
|||wird ausgelöst durch javascript|
|POST|sendet Informationen an eine Resource| POST kann fast alles.|
|||POST ist weder idempotent noch save|
|||wird ausgelöst durch abschicken eines Fromulars|

Beispiele für wie die verben aufgerufen werden.

|Element|Verben|Bemerkung|
|-|-|-|
|`<a>`|GET| `<a>` fordert die Resource unter `href` an.|
|`<form>`|GET, POST (und per JavaScript PUT/DELETE)||
|`<button>`|abhängig vom Formular|Buttons innerhalb eines `<form>` lösen standardmäßig den Request aus, der im Formular definiert ist (GET oder POST).|

## Formulare

### Input

Formulare haben mehrere Eingabefelder `<input>` und schicken die eingegebenden Daten (oft an einen Server) weiter. 
Es gibt verschiedene Arten von Userinput. Diese werden mit dem `type`- Attribut angegeben. Auf folgender Seite gibt es eine Übersicht:

https://www.w3schools.com/html/html_form_input_types.asp

### Attribute

Außerdem kann jedes Eingabefeld weitere Attribute haben.
Einige wie `type` ode `id` kann und sollte man be allen Attributen nutzen und andere sind spezifisch, wie z.B. `min` oder `max` bei number.
W3school hat auch dazu zwei Übersichten:

https://www.w3schools.com/html/html_form_attributes.asp
https://www.w3schools.com/html/html_form_attributes_form.asp

### Lables

Das `<label>` Element kann (und sollte (ich würde fast sagen muss)) genutzt werden um einem `<input>` Element ein sichtbares Label zu geben. Das wird gemacht in dem man dem `<input>` Element das Attribut `id` und dem `<label>` Element das Attribut `for` mit dem selben Wert gibt.

### Beispiel

```HTML
<form>
    <label for="IDname"> Name </label>
    <input type="text" id="IDname" name="name">

    <label for="IDalter"> Alter </label>
    <input 
        type="number" 
        id="IDalter" 
        name="alter" 
        min="0" 
        max="100"
    >

    <input type="submit" value="Abschicken">
</form>
```

Bemerkung: anscheinend braucht man bei `submit` inputs kein Label, da das `value` Attribut bereits angezeigt wird und auch von screenreadern gut erkannt wird.(quelle chatGPT)

## Accessibility

1. Die **richtigen tags** zu wählen ist für Assistenzsysteme sehr wichtig. Z.B.
    - `<addresse>` für Adressen und nicht `<p>`
    - nicht `h3` für `h2`nur weil sie kleiner ist
2. **Tastaturbedienung** ermöglichen
3. **Farbwahrnehmung und Kontraste** beachten
    - Informationen nicht nur farblich übermitteln
    - guten Kontrast wählen (nicht zu stark und nicht zu schwach)
    - Alternative Farbschemata 
4. **Skalierung** der Seite ermöglichen
5. **Responsiveness** beschreibt die Anpassung auf verschiedene Bildschirme größen (vor allem Smartphones). Das sollte immer gut klappen.
6. JEDES Bild braucht ein **`alt`-Attribut** (kan bei rein optischen bildern auch leer sein).
7. **Leichte Sprache**
8. Formularfelder brauchen immer ein **Label**


## Bootstrap

TODO