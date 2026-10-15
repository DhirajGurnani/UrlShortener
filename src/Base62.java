/** Encodes non-negative numbers in base 62 using the alphabet 0-9, a-z, A-Z. */
public final class Base62 {

    static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();

    private Base62() {
    }

    public static String encode(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Cannot encode a negative number: " + value);
        }
        if (value == 0) {
            return "0";
        }
        StringBuilder code = new StringBuilder();
        while (value > 0) {
            code.append(ALPHABET.charAt((int) (value % BASE)));
            value /= BASE;
        }
        // Digits come out least significant first.
        return code.reverse().toString();
    }
}
