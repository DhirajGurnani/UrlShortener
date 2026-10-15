import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Assigns each URL the next counter value, base62 encoded, as its short code.
 * Mappings live in memory only and are lost when the app exits.
 */
public class UrlShortener {

    private final Map<String, String> urlsByCode = new HashMap<>();
    private long nextId = 1;

    /** Returns a new short code for the given URL. */
    public String shorten(String longUrl) {
        // The counter only moves forward, so each code is new and can never overwrite a mapping.
        String code = Base62.encode(nextId++);
        urlsByCode.put(code, longUrl);
        return code;
    }

    /** Returns the original URL for the given short code, or empty if the code is unknown. */
    public Optional<String> resolve(String shortCode) {
        return Optional.ofNullable(urlsByCode.get(shortCode));
    }
}
