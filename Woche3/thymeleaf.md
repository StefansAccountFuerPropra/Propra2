# Thymeleaf

## Überblick

Syntax für die Platzhalter:`th:attribut="{variablenname}"`

|thymeleaf variable| Funktions|
|-|-|
|`th:pareameter="${variablenname}"`|Fast jeder Parameter eines HTML-Tags kann mit thymeleaf ersetzt werden.|
|`th:text="${variablenname}`|Kann Text innerhalb eines Tags ersetzen.|
|`th:classappend="${markierung}"`|Kann enem Tag eine andere Klasse geben.|


## Kopie der Vorlesung

Folgendes ist direkt aus der Vorlesung geklaut, da es thymeleaf sehr gut erklärt.

### Parameter ersetzen

Die offensichtlichsten Varianten der Thymeleaf-Attribute sind die Attribute, die einem regulären HTML-Attribut entsprechen. Zu praktisch jedem regulären HTML-Attribut existiert eine Thymeleaf-Variante, die während des Renderings das HTML-Attribut ersetzt.

Beispielsweise gibt es als Pendant des value-Attributes in einem input Tag das `th:value` Attribut. Wenn die Templateverarbeitung stattfindet, wird das value Attribut durch den berechneten Wert des th:value Attributs ersetzt. Stellen wir uns vor, dass wir ein Model haben, in dem unter dem Schlüssel usermail der Wert propra@cs.hhu.de gespeichert ist. Dann würde folgendes Rendering resultieren:


```html
<!-- Template -->
<form>
 <input type="text" name="mail" value="test@example.com" th:value="${usermail}">
 <button type="submit">Ändern</button>
</form>

<!-- wird zu -->
<form>
 <input type="text" name="mail" value="propra@cs.hhu.de">
 <button type="submit">Ändern</button>
</form>
```

### Text ersetzen

Neben den normalen HTML-Attributen gibt es in Thymeleaf zusätzlich das spezielle th:text Attribut, das nicht ein HTML-Attribut, sondern den vollständigen Inhalt eines Tags ersetzt. Mit unserem Beispielmodel würde also folgendes Rendering resultieren:

```html
<!-- Template -->
<p class="alert alert-danger" th:text="${usermail}">Stellen wir uns vor, wie haben hier jede Menge <strong>Text<strong> stehen</p>

<!-- wird zu -->
<p class="alert alert-danger">propra@cs.hhu.de</p>
```

Es ist zu beachten, dass der komplette Inhalt, der innerhalb des Tags im Template steht durch den Wert ersetzt wird. Bei einzelnen Stellen in einem Fließtext bietet es sich daher an, etwas wie <span> als Ziel für eine Änderung zu verwenden.

```html
<!-- Template -->
<p class="alert alert-danger">Nachrichten des Systems werden automatisch an <span th:text="${usermail}">test@example.com</span> verschickt.</p>

<!-- wird zu -->
<p class="alert alert-danger">Nachrichten des Systems werden automatisch an <span>propra@cs.hhu.de</span> verschickt.</p>
```

### Klasse ersetzen

Ein weiteres Thymeleaf-Attribut, zu dem es keine HTML-Entsprechung gibt, ist th:classappend, das wir verwenden können, wenn wir zu bestehenden CSS-Klassen weitere Klassen hinzufügen wollen. Stellen wir uns vor, im Model würde unter dem Schlüssel markierung der Wert warnung stehen. Dann würde folgendes Rendering durchgeführt.

```html
<!-- Template -->
<p class="box" th:classappend="${markierung}">Das hier ist wichtig!</p>

<!-- wird zu -->
<p class="box warnung">Das hier ist wichtig!</p>
```

Das th:classappend Attribut ist nützlich, wenn wir bestimmte optische Hervorhebungen über den Java-Code steuern wollen.



## Objekte übergeben

Man kann in thymeleaf Objekte übergeben und auf Variablen und Funktionen dieser zugreifen.

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
public String get_index(Model model, Vector v) {
    model.addAttribute("vector", v);
    return "forward:/index";
}
```

```HTML
<p th:text="${vector.getLength()}"> 0 </p>
<p th:text="${vector.x}">> x </p>
```
Man beachte das x eigentlich private ist aber thymeleaf trotzdem den getter erkennt.
man kann soger `vector.lenght` anstatt `vector.getLength()` schreiben obwohl die variable nicht existiert.
Dazu muss aber der getter den richtigen Namen haben.


## thymeleaf scope

Man kann mit `th:object` einen scope erzeugen. Folgendes ist äquivalent zu oben.

```HTML
<div th:object="${vector}">
    <p th:text="*{getLength()}"> 0 </p>
    <p th:text="*{x}"> x </p>
</div>
```

Mit `th:field="*{x}">> x` können wir dann schönere formulare schreiben. Folgende input felde sind äquivalent:

```HTML
<form th:object="${user}" method="post">
    <input type="text" th:field="*{username}" />
    <input type="text" id="username" name="username" value="(aktueller Wert aus user.username)">
</form>
```

## Arithmetik

Man mkann in thymeleaf auch einwenig rechnen.

`<span th:text="${a} + ${b}">0</span>`

Mit dem Elvis-Operator ? kann man default Werte bei null setzen. 

`th:text="${name} ?: 'Unbekannter Nr. 1'"`


## href

-Wir können einen Query-Parameter in runden Klammern anhängen. Wenn wir im Template das Attribut `th:href="@{/order(id=${orderId})}"` schrieben, dann bekommen wir im resultierenden HTML-Dokument `href="/order?id=32"`.

-Ein Pfadparameter wird analog zur Pfadparametersyntax im Controller geschrieben. Wir würden zum Beispiel mit `th:href="@{/customers/{nr}(nr=${customerId})}` als Resultat `href="/customers/18"` erhalten.

-Wir können auch beide Parameterarten miteinander kombinieren und `th:href="@{/customers/{nr}/orders/(nr=${customer},id=${orderId})}"` würde als Ergebnis `href="/customers/18/orders?id=32"` produzieren.


## Kontrollsrtuckturen

### if/unless

Die th:if und th:unless Attribute können wir verwenden, um Tags beim Rendering bedingt zu inkludieren.

```HTML
<div th:if="${einblenden}">
 <p>Dieser Text ist nicht zu sehen, wenn einblenden <em>false<em> oder <em>null<em> ist.
</div>

<div th:unless="${einblenden}">
 <p>Dieser Text ist nur dann zu sehen, wenn einblenden <em>false<em> oder <em>null<em> ist.
</div>
```

### each

In folgendem java und html abschnitt nutzen wir das each atribut um eine liste auszu geben.

```java
public record TodoItem(boolean done, String text) {}

List<TodoItem> items = List.of(
    new TodoItem(true,  "Einkaufen"),
    new TodoItem(false, "Lernen")
    );

model.addAttribute("todos", items);
```

```HTML
<ul th:remove="all-but-first">
    <li th:each="item : ${todos}"
        th:classappend="${item.done} ? 'done': ''"
        th:text="${item.text}">
        Item 1
    </li>
    <li class="done">Item 2</li>
    <li class="done">Item 3</li>
    <li>Item 4</li>
    <li>Item 5</li>
</ul>
```
Folgender code wird erzeugt.

```HTML
<ul>
    <li class="list-group-item done">Einführungsvideo Thymeleaf aufnehmen</li>
    <li class="list-group-item">th:each erklären</li>
</ul>
```