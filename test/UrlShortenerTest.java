import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UrlShortenerTest {

    private final UrlShortener shortener = new UrlShortener();

    @Test
    void firstCodeIsOne() {
        assertEquals("1", shortener.shorten("https://example.com"));
    }

    @Test
    void codesFollowTheCounterInBase62() {
        for (int i = 1; i <= 61; i++) {
            shortener.shorten("https://example.com/" + i);
        }

        assertEquals("10", shortener.shorten("https://example.com/62"));
    }

    @Test
    void resolveReturnsTheShortenedUrl() {
        String code = shortener.shorten("https://example.com/page?q=1");

        assertEquals(Optional.of("https://example.com/page?q=1"), shortener.resolve(code));
    }

    @Test
    void everyCodeResolvesToItsOwnUrl() {
        String first = shortener.shorten("https://a.com");
        String second = shortener.shorten("https://b.com");

        assertEquals(Optional.of("https://a.com"), shortener.resolve(first));
        assertEquals(Optional.of("https://b.com"), shortener.resolve(second));
    }

    @Test
    void codesAreCaseSensitive() {
        for (int i = 1; i <= 10; i++) {
            shortener.shorten("https://example.com/" + i);
        }

        assertEquals(Optional.of("https://example.com/10"), shortener.resolve("a"));
        assertEquals(Optional.empty(), shortener.resolve("A"));
    }

    @Test
    void unknownCodeResolvesToEmpty() {
        assertEquals(Optional.empty(), shortener.resolve("1"));
    }

    // Current behaviour until step 4 adds "same URL returns the existing code".
    @Test
    void sameUrlTwiceCurrentlyGetsTwoCodes() {
        assertNotEquals(shortener.shorten("https://a.com"), shortener.shorten("https://a.com"));
    }
}
