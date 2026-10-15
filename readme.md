# UrlShortener

A local URL shortener that runs on a single machine.

## Implementation

Step 1 Implement a CLI to take command and based on the call a mehtod. Commands shorten longUrl, resolve shorten url as mentioned below
- `shorten(longUrl)` returns a short code
- `resolve(shortCode)` returns the original URL

Step 2 have a hashmap to store short url to long url ocnversation

Step 3 implement a counter and then base62 encode the that counter and use that counter to create short url.
Mappings are saved to disk so they survive a restart.

## Execution

- Go over the steps one by one do not implement all.
- Ask questions, then implement code
- Everytime you write code write uni test along with it
- Commit the code after you added anythign