import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/** Step 1 only: both operations are stubs. Real tests come with steps 2 and 3. */
class UrlShortenerTest {

    private final UrlShortener shortener = new UrlShortener();

    @Test
    void shortenIsNotImplementedYet() {
        assertThrows(UnsupportedOperationException.class, () -> shortener.shorten("https://example.com"));
    }

    @Test
    void resolveIsNotImplementedYet() {
        assertThrows(UnsupportedOperationException.class, () -> shortener.resolve("abc"));
    }
}
