# Docker und Datenbanken

## Docker

### Beispiel docker-compose.yaml

```yaml
services:
  kundendb:
    image: postgres:15-alpine
    ports:
      - "5433:5432"
    volumes:
      - ./data/kunden:/var/lib/postgresql/data
    environment:
      - "POSTGRES_DB=kunden"
      - "POSTGRES_USER=exampleuser"
      - "POSTGRES_PASSWORD=iamgroot"
```

| | |
|-|-|
|postgres:15-alpine| docker image|
|||
|./data/kunden:/var/lib/postgresql/data| speichere die Daten aus dem containder die in /var/lib/postgresql/data liegen würden in  ./data/kunden |
|POSTGRES_DB=kunden| erstellt die DB `kunden` |

### commands

| | |
|-|-|
|`docker compose up -d`| Startet ein docker container mit der docker-compose.yaml im hintergrund. |
|`docker ps -a`| Zeigt alle Container an.|
|`docker exec -it <Name des containers> /bin/sh`| Verbindung zum Container |
|`docker compose down`| Beendet den docker compose conteiner aus der docker-compose.yaml.|


## relationale Datenbanken

### Relationen

In einer Datenbank werden **Entitäten** gespeichert und die können **Beziehungen** zueinander haben. die se Beziehungen haben eine der folgenden Formen

#### [1:1]

Für jede Firma gibt es einen Chef und für jeden Chef eine Firma.

Wir speichern beides in einer Tabelle.

[1:0] und [0:1] sind hier auch erlaubt und können mit null angegeben werden.

#### [1:n]

Jede Firma hat n Mitarbeiter. 

Wir speichern beides einzeln aber in Mitarbeter haben wir eine Spalte Firma.

"Im n part haben wir einen Verweis auf den 1 part"

[1:0] kann wieder mit null angegeben werden.
[n:0] ist auch erlaubt und ist dann der fall wenn es kein mit arbeiter mit dieser firma gibt.

#### [m:n]

Ein Projekt hat n Mitarbeiter und ein Mitarbeiter arbeitet an m projekten.

Wir speichern beides einzeln und haben eine zusätzliche tabelle mit den jeweiligen ids und speichern dort alle relationen.

### Sprachen

Es gibt in Datenbanken verschiedene Sprachen 

|Sprache|unterteilung|Erklärung|
|-|-|-|
|AnfragenSprache|/|Quasi die Getter.|
|ManipulationsSprache|Data Definition Language (DDL)|Ändert die Art wie die Daten gespeichert werden.|
|ManipulationsSprache|Data Control Language (DCL)|Zugriffs berechtigungen vergeben.|
|ManipulationsSprache|Data Manipulation Language (DML)|Zum speichern und Löschen.|

### Schlüssel

Eine Teilmenge S von Attributen einer Relation heißt Schlüssel, wenn sie

- die Datensätze eindeutig identifiziert, d. h. es gibt keine zwei Datensätze mit derselben Kombination von Werten in S,

- für alle Einträge definiert ist, d. h. jeder Datensatz hat Werte für die Attribute in S,

- minimal ist, d. h. keine echte Teilmenge von S kann die Datensätze eindeutig identifizieren.


|||
|-|-|
|künstliche Schlüssel|Einfach ne fortlaufende zahl bzw UUID|
|natürliche Schlüssel|bereits vorhandene Attribute.|


## Datenbanken in Java

