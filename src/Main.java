import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        CommandHandler handler = new CommandHandler(new UrlShortener());
        handler.run(new BufferedReader(new InputStreamReader(System.in)), System.out);
    }
}
