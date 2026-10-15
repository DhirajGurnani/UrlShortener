import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlStoreTest {

    private final UrlStore store = new UrlStore();

    @Test
    void returnsStoredUrl() {
        store.put("1", "https://example.com");

        assertEquals(Optional.of("https://example.com"), store.get("1"));
        assertEquals(1, store.size());
    }

    @Test
    void unknownCodeIsEmpty() {
        assertEquals(Optional.empty(), store.get("nope"));
    }

    @Test
    void codesAreCaseSensitive() {
        store.put("a", "https://lower.com");
        store.put("A", "https://upper.com");

        assertEquals(Optional.of("https://lower.com"), store.get("a"));
        assertEquals(Optional.of("https://upper.com"), store.get("A"));
    }

    @Test
    void rejectsReusedCodeAndKeepsOriginalMapping() {
        store.put("1", "https://first.com");

        assertThrows(IllegalStateException.class, () -> store.put("1", "https://second.com"));
        assertEquals(Optional.of("https://first.com"), store.get("1"));
        assertEquals(1, store.size());
    }
}
