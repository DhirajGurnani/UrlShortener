# UrlShortener

A local URL shortener that runs on a single machine.

## Operations

- `shorten(longUrl)` returns a short code
- `resolve(shortCode)` returns the original URL

Mappings are saved to disk so they survive a restart.

## Run

Plain Java project on JDK 21, set up for IntelliJ IDEA (no build tool).

In IntelliJ: open this folder and run `Main`.

From a terminal:

```
javac -d out src/*.java
java -cp out Main
```
