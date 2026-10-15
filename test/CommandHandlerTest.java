import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandHandlerTest {

    /** Records the argument it was called with and returns a fixed value. */
    private static class FakeShortener extends UrlShortener {
        String shortenedWith;
        String resolvedWith;

        @Override
        public String shorten(String longUrl) {
            shortenedWith = longUrl;
            return "abc";
        }

        @Override
        public String resolve(String shortCode) {
            resolvedWith = shortCode;
            return "https://example.com";
        }
    }

    private final FakeShortener fake = new FakeShortener();
    private final CommandHandler handler = new CommandHandler(fake);

    @Test
    void shortenPassesUrlToShortener() {
        assertEquals("abc", handler.handle("shorten https://example.com/page"));
        assertEquals("https://example.com/page", fake.shortenedWith);
    }

    @Test
    void resolvePassesCodeToShortener() {
        assertEquals("https://example.com", handler.handle("resolve abc"));
        assertEquals("abc", fake.resolvedWith);
    }

    @Test
    void ignoresSurroundingAndRepeatedWhitespace() {
        assertEquals("abc", handler.handle("   shorten    https://example.com  "));
        assertEquals("https://example.com", fake.shortenedWith);
    }

    @Test
    void blankLineGivesEmptyResponse() {
        assertEquals("", handler.handle(""));
        assertEquals("", handler.handle("   \t "));
    }

    @Test
    void commandsAreCaseSensitive() {
        assertEquals("Error: unknown command 'SHORTEN'. " + CommandHandler.HELP,
                handler.handle("SHORTEN https://example.com"));
        assertNull(fake.shortenedWith);
    }

    @Test
    void unknownCommandGivesError() {
        assertEquals("Error: unknown command 'delete'. " + CommandHandler.HELP, handler.handle("delete abc"));
    }

    @Test
    void shortenWithoutArgumentShowsUsage() {
        assertEquals("Usage: shorten <url>", handler.handle("shorten"));
        assertNull(fake.shortenedWith);
    }

    @Test
    void shortenWithExtraArgumentsShowsUsage() {
        assertEquals("Usage: shorten <url>", handler.handle("shorten https://a.com https://b.com"));
        assertNull(fake.shortenedWith);
    }

    @Test
    void resolveWithoutArgumentShowsUsage() {
        assertEquals("Usage: resolve <code>", handler.handle("resolve"));
        assertNull(fake.resolvedWith);
    }

    @Test
    void resolveWithExtraArgumentsShowsUsage() {
        assertEquals("Usage: resolve <code>", handler.handle("resolve abc def"));
        assertNull(fake.resolvedWith);
    }

    @Test
    void exitWithArgumentsShowsUsage() {
        assertEquals("Usage: exit", handler.handle("exit now"));
    }

    @Test
    void unimplementedOperationIsReportedNotThrown() {
        CommandHandler real = new CommandHandler(new UrlShortener());
        assertEquals("Error: shorten is not implemented yet", real.handle("shorten https://example.com"));
        assertEquals("Error: resolve is not implemented yet", real.handle("resolve abc"));
    }

    @Test
    void runProcessesLinesUntilExit() throws IOException {
        String output = run("shorten https://example.com\n\nresolve abc\nexit\nshorten https://ignored.com\n");

        assertEquals(CommandHandler.HELP + "\n> abc\n> > https://example.com\n> ", output);
        assertEquals("https://example.com", fake.shortenedWith);
    }

    @Test
    void runAcceptsExitWithSurroundingWhitespace() throws IOException {
        String output = run("  exit  \nshorten https://ignored.com\n");

        assertTrue(output.endsWith("> "));
        assertNull(fake.shortenedWith);
    }

    @Test
    void runStopsAtEndOfInputWithoutExit() throws IOException {
        String output = run("resolve abc\n");

        assertEquals(CommandHandler.HELP + "\n> https://example.com\n> ", output);
    }

    private String run(String input) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        handler.run(new BufferedReader(new StringReader(input)), out);
        return bytes.toString(StandardCharsets.UTF_8).replace(System.lineSeparator(), "\n");
    }
}
