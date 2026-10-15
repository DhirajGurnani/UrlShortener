import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Base62Test {

    @Test
    void encodesZero() {
        assertEquals("0", Base62.encode(0));
    }

    @Test
    void encodesSingleDigitBoundaries() {
        assertEquals("1", Base62.encode(1));
        assertEquals("9", Base62.encode(9));
        assertEquals("a", Base62.encode(10));
        assertEquals("z", Base62.encode(35));
        assertEquals("A", Base62.encode(36));
        assertEquals("Z", Base62.encode(61));
    }

    @Test
    void rollsOverToMoreDigitsAtPowersOf62() {
        assertEquals("10", Base62.encode(62));
        assertEquals("ZZ", Base62.encode(62 * 62 - 1));
        assertEquals("100", Base62.encode(62 * 62));
    }

    @Test
    void encodesLargestLong() {
        assertEquals("aZl8N0y58M7", Base62.encode(Long.MAX_VALUE));
    }

    @Test
    void rejectsNegativeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> Base62.encode(-1));
    }

    @Test
    void alphabetHas62DistinctCharacters() {
        assertEquals(62, Base62.ALPHABET.chars().distinct().count());
    }
}
