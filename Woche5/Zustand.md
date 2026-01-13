# Zustand

Problem: HTTP ist zustandslos.

Folgende Lösungen gibt es:

## HTTP + HTML + Thymeleaf

1. In Form_1 setzen wir eine Variable x.

2. In einem Controller_1 zu dem form_1 schreiben wir die Variable x mit thymeleaf zurück in ein hidden input.

3. In Form_2 haben wir nun x als input.

Vorteil: Kein Zustand auf dem Server.

Nachteil: man muss in jedem schritt die variable zurück schreiben. das kann für längere zeit unpracktischsein


## Cookies

### Cookie anlegen

```java
@PostMapping()
public String name(String name, HttpServletResponse resp){
    resp.addCookie(new Cookie("nameCookie", name));
    return "html";
}
```

### Cookie nutzen
Spring kann dir den Cookie direkt in den Parameter injecten:

```java
@GetMapping("/welcome")
public String welcome(@CookieValue(name = "nameCookie", defaultValue = "Gast") String name) {
    System.out.println("Cookie-Wert: " + name);
    return "welcome";
}
```

- Wenn der Cookie existiert → name enthält den Wert
- Wenn nicht → "Gast"

Alternativ mit `req.getCookies();` aus `HttpServletRequest`.

### Cookie age

#### 0: cookie löschen
```java
Cookie c = new Cookie("nameCookie", null);
c.setMaxAge(0); // löscht sofort
resp.addCookie(c);
```

#### default: Sessioncookie

Das default age eines cookies ist die Session, also wenn der **Browser** geschlossen wird.

#### x: x sekunden
`c.setMaxAge(x)` setzet die cookie laufzeit auf x sekunden.

### Bewertung :(

Cookies sind eher schlecht, da der client Cookies bearbeiten oder garnicht erst schicken kann.


## Sessions

Membervariablen eines Controllers sind einzigartig., das  heißt alle Sessions greifen auf die gleiche Variable zu.

**Es ist keine Lösung Zustand in** **MemberVariablen zu speichern**

### SessionScope

Annotiert man eine ControllerClass mit `@SessionScope` wird für jede Session dieser Controller erzeugt.

Nachteile: 

- Ressourzen aufwendig
- alle injecierten Beans müssen auch mit `@SessionScope` annotiert werden

### SessionAttributes

Annotieren wir einen Controller mit `@SessionAttributes({"lal"})` so ist lal ein Session attribut. 

Haben wir ein ModelAttribut lal wird dies zusätzlich in der Session gespeichert und als "backup" genutzt fals lal nicht übergeben wird. Wird lal übergeben wird auch das sessionatribut lal überschrieben.

### SessionAttribute

Man kann auch direkt parameter mit `@SessionAttribute("lal")` annotieren.

### HttpSession

`HttpSession`-Elemente können als parameter übergeben werden und damit über Controllerhinaus Session Variablen angelegt werden.

```java
@GetMapping("/test")
public String test(HttpSession session) {
    session.setAttribute("foo", "bar");
    return "view";
}
```
