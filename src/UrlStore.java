import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory mapping from short code to long URL. Data is lost when the app exits. */
public class UrlStore {

    private final Map<String, String> urlsByCode = new HashMap<>();

    /** Stores a new mapping. Throws if the code is already taken, so no mapping is ever overwritten. */
    public void put(String code, String longUrl) {
        if (urlsByCode.containsKey(code)) {
            throw new IllegalStateException("Code already in use: " + code);
        }
        urlsByCode.put(code, longUrl);
    }

    public Optional<String> get(String code) {
        return Optional.ofNullable(urlsByCode.get(code));
    }

    public int size() {
        return urlsByCode.size();
    }
}
