import java.util.Optional;

/** Assigns each URL the next counter value, base62 encoded, as its short code. */
public class UrlShortener {

    private final UrlStore store;
    private long nextId = 1;

    public UrlShortener() {
        this(new UrlStore());
    }

    UrlShortener(UrlStore store) {
        this.store = store;
    }

    /** Returns a new short code for the given URL. */
    public String shorten(String longUrl) {
        String code = Base62.encode(nextId);
        store.put(code, longUrl);
        nextId++;
        return code;
    }

    /** Returns the original URL for the given short code, or empty if the code is unknown. */
    public Optional<String> resolve(String shortCode) {
        return store.get(shortCode);
    }
}
