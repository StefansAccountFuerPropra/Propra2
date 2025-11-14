# Parameter in Spring

## Syntax

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
Die oben definierten handler werden nur aufgerufen wenn der Parameter `name` vorhanden ist.
Dieser kann allerdings `null` sein.
Mit `@RequestParam(defaultValue = irgendwas )` können wir dem Parameter einen Wert geben, falls `null` übergeben wird.

## Parameter als andere Typen

Wenn Wir einen Parameter als einen Anderen Typ interpretieren wollen. Können wir einfach auf den Typ casten. 
Dabei muss z.B. `Double` anstatt `double` genutzt werden, da primitive Datentypen kein `null` halten können.

Möchte man keinen default wert, sollte man einen null-check machen.

Sagen wir erhalten von der Website 2 Zahlen x und y.
```java
@GetMapping()
@ResponseBody
public String get_index(Double x, Double y) {
    if(x == null || y == null){"forward:/index";}
    Double z = Math.sqrt(x*x + y*y);
    return x.toString();
}
```

## viele Parameter

Es kann sich lohnen eine Klasse zu erstellen, welche die Parameter als Felder hat. Spring kann diese automatisch füllen.
Dabei müssen die Felder genau so heißen wie die gewünschten Parameter

```java
public record Vector(Double x, Double y){
    public Boolean notNull(){
        return (x != null && != null);
    }
    public Double getLength(){
        return Math.sqrt(x*x + y*y);
    }
}

@GetMapping()
@ResponseBody
public String getIndex(Vector v) {
    if(v.notNull()){
        return v.getLength().toString();
    }
    return "forward:/index";
}
```


## Pfad als Parameter

Auch Teile des Pfades können wir als Parameter übergeben. Diese müssen wir mit `@PathVariable` annotieren und hier können wir das nicht weglassen.

```java
@GetMapping("/{organization}/{repository}")
ResponseBody
public String[] repo(   @PathVariable("organization") String organization,
                        @PathVariable("repository")   String repository) {
  return new String[]{organization, repository};
}
```

## veitere Parameter

https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/arguments.html