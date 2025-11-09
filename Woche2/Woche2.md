# Woche 2

## Idee

Wir haben eine Website mit url **website.de** und wollen auf bestimmten Unterseiten, z.B. mit url **website.de/unterseite**, bestimmte Anfragen wie **GET**, **PUT** oder **POST** bearbeiten.

Dazu müssen wir bestimmten Klassen oder Methoden bestimmte Aufgaben zuordnen.

## Spring Boot

### Ressources

Alle ressourcen wie HTML-, CSS-, javascript-Dateien oder Bilder kommen entweder in den static ordner oder in den template ordner. Dateien im template ordner können mit thymeleaf variablen enthalten, die in java gesetzt werden können.

### Main

Die Mainclass ist mit `@SpringBootApplication` annotiert und führt den SpringApplicationrunner aus. Diese starten einen Server und reagiert auf anfragen der Website.

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```

### Controller

Klassen die mit `@Controller` annotiert sind können Methoden enthalten welche auf spezielle Anfragen der Website reagieren.

### RequestMapping

Methoden eines Controllers können mit `@RequestMapping` annotiert sein.
Diese Handler-Methoden (oder einfach handler) sind zuständig dafür auf bestimmte anfragen von bestimmten Unterseiten zu antworten RequestMapping hat 2 parameter.

- path: gibt an für welche Unterseite diese diese Methode "zuständig ist".
- method: gibt an auf welche Methode (GET etc.) die Handler-Methode antwortet
- params: checkt welche Parameter wie gesetzt sind und fürt den handler nur unter diesen Bedingungen aus.

Beispiel für params (in Kapitel "Params" genauer):

`@RequestMapping(params = {"a=1", "b", "c!= 3", "!d"})`

Folgendes Beispiel antwortet mit einer HTML-Datei aus dem templates Ordner, wenn die Unterseite website.de/unterseite ein GET request schickt.
Dies passiert beim Laden der Seite, also wird die HTML-Datei direkt angezeigt.

```java
@Controller
public class ControllerClass {

    @RequestMapping(path = "/unterseite", method = RequestMethode.Get)
    public String get_index() {
        return "index.html";
    }
}
```

Wir können auch `@RequestMapping` an die Klasse schreiben, wenn wir eine Klasse für eine bestimmte Unterseite nutzen wollen. 

Die Klasse aus folgendem Beispiel ist zuständig für die Unterseite website.de/unterseite.

|handler		|path|method|
|-				|-|-|
|get_index		|/unterseite|GET|
|defaultHandler	|/unterseite|alle außer GET|
|defaultHandlerDetails|/unterseite/details| alle |

```java
@Controller
@RequestMapping(path = "/unterseite")
public class ControllerClass {

    @RequestMapping(method = RequestMethode.Get)
    public String get_index() {
        return "index.html";
    }

	@RequestMapping()
    public String defaultHandler() {
        return "default.html";
    }

	@RequestMapping(path = "/details")
    public String defaultHandlerDetails() {
        return "default.html";
    }
}
```

### precise requestMapping

Man kann auch in einem `@RequestMapping` mehrere Methoden angeben. Das sollte man allerdings vermeiden. Am besten nimmt am direkt pricise mappings wie `@GetMapping()` anstatt `@RequestMapping(methode = "RequestMethode.Get")`

### ResponseBody

Man kann auch HTML-code als Antwort zurückgeben, der direkt in den `<body>` einer HTML gepackt wird. Dazu annotiert man den handler mit `@ResponseBody` 

```java
@GetMapping()
public String get_index() {
    return "<h1> Hallo </h1>";
}
```
### RestController

Klassen, die mit `@RestController` annotiert sind, verhalten sich wie normale Controller, allerdings verhalten sich alle Handler-Methoden so, als wären der Rückgabetyp mit `@ResponseBody` annotiert.

### Parameter

#### Routing mit params

Oben haben wir bereits gesehen wei Parameter fürs Routing verwendet werden können. Zur Erinnerung das Beispiel:

`@RequestMapping(params = {"a=1", "b", "c!= 3", "!d"})`

|Kürzel |Bedeutung|
|-|-|
|a=1    | a muss gleich 1 sein|
|b      | b muss gesetzt sein|
|c!=3   | darf nicht 3 sein|
|d      | d darf nicht gesetzt sein|

Diese Bedingungen werden verundet und der handler wird nur ausgeführt wenn alle wahr sind.

#### Params im handler nutzen

Haben wir beispielsweise eine **GET**-request mit dem Parameter `name=Stefan` können wir diesen in einem handler nutzen, indem wir diesem einen String parameter übergeben und diesen mit `@RequestParam("name")` annotieren.

Wenn dieser String genau so heißt wie der Parameter, kann man `@RequestParam("name")` weglassen.

```java
@GetMapping()
ResponseBody
public String get_index(@RequestParam("name") String myName) {
    return myName;
}

@GetMapping()
ResponseBody
public String get_index(String name) {
    return name;
}
```
#### Pfad als Parameter

Auch Teile des Pfades können wir als Parameter übergeben. Diese müssen wir mit `@PathVariable` annotieren und hier können wir das nicht weglassen.

```java
@GetMapping("/{organization}/{repository}")
ResponseBody
public String[] repo(   @PathVariable("organization") String organization,
                        @PathVariable("repository")   String repository) {
  return new String[]{organization, repository};
}
```

### Return types

|Annotation|Return type|Was macht das|
|-|-|-|
|keine          |String                 |Gibt ein HTML-template aus templates zurück|
|`@ResponseBody`|String                 |Gibt ein text zurüch der in eine HTML eingebettet wird|
|keine          |ResponseEntity<String> |wie mit `@ResponseBody`, aber man kann einen Fehlercode hinzufügen|
|`@ResponseStatus`|String               |wie das default, aber man kann einen Fehlercode hinzufügen|
|keine          |RedirectView           |- weiterleiting auf eine andere seite <br>- man kann auch einfach in einer default methode `"redirect:/andere_seite"` zurückgeben <br>- gibt man `"forward:/andere_seite"` zurück passiert die umleitung java intern|
|keine          |ModelAndView           | Man kann eine eigene Rückgabe bauen|

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ModelAndView.html

