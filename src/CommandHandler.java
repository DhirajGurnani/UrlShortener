import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Parses CLI input and dispatches to {@link UrlShortener}.
 * Commands are case-sensitive: shorten &lt;url&gt;, resolve &lt;code&gt;, exit.
 * Takes its input and output as parameters so it can be tested without System.in/System.out.
 */
public class CommandHandler {

    static final String HELP = "Commands: shorten <url>, resolve <code>, exit";

    private final UrlShortener shortener;

    public CommandHandler(UrlShortener shortener) {
        this.shortener = shortener;
    }

    /** Reads commands one line at a time until "exit" or end of input. */
    public void run(BufferedReader in, PrintStream out) throws IOException {
        out.println(HELP);
        while (true) {
            out.print("> ");
            String line = in.readLine();
            if (line == null || line.trim().equals("exit")) {
                return;
            }
            String response = handle(line);
            if (!response.isEmpty()) {
                out.println(response);
            }
        }
    }

    /** Maps one input line to the text to print. Blank input gives an empty response. */
    String handle(String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        // URLs and codes never contain whitespace, so anything after the first argument is an error.
        String[] parts = trimmed.split("\\s+");
        String command = parts[0];
        switch (command) {
            case "shorten":
                if (parts.length != 2) {
                    return "Usage: shorten <url>";
                }
                return shortener.shorten(parts[1]);
            case "resolve":
                if (parts.length != 2) {
                    return "Usage: resolve <code>";
                }
                return shortener.resolve(parts[1])
                        .orElse("Error: unknown code '" + parts[1] + "'");
            case "exit":
                return "Usage: exit";
            default:
                return "Error: unknown command '" + command + "'. " + HELP;
        }
    }
}
