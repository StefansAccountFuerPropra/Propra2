# Woche 3

## param convertierung

Wir kriegen Parameter einer Request immer als Text und müssen den convertieren.
Das kann man einfach wie folgt machen.

```java
@GetMapping()

```

## thymeleaf

Syntax für die Platzhalter:`th:attribut="{variablenname}"`

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

Ein weiteres Thymeleaf-Attribut, zu dem es keine HTML-Entsprechung gibt, ist th:classappend, das wir verwenden können, wenn wir zu bestehenden CSS-Klassen weitere Klassen hinzufügen wollen. Stellen wir uns vor, im Model würde unter dem Schlüssel markierung der Wert warnung stehen. Dann würde folgendes Rendering durchgeführt.

```html
<!-- Template -->
<p class="box" th:classappend="${markierung}">Das hier ist wichtig!</p>

<!-- wird zu -->
<p class="box warnung">Das hier ist wichtig!</p>
```

Das th:classappend Attribut ist nützlich, wenn wir bestimmte optische Hervorhebungen über den Java-Code steuern wollen.
