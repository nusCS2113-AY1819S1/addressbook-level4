package seedu.address.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import seedu.address.model.event.Event;

/**
 * Formatter for Event Page HTML file
 */
public class EventPageFormatter {

    private static URI SearchPagePath;
    private static String SearchPageString;
    private static final DateFormat pageDateFormat = new SimpleDateFormat("EEEEE dd-MMMMM-yyyy 'at' HH:mm a");
    public EventPageFormatter() {

    }

    /**
     * Reads Event Search Page file from provided path
     */
    private static String readFile(URI path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    /**
     * Overwrites Event Search Page file
     */
    private static void writeFile(URI path) throws IOException {
        File f = new File(SearchPagePath);
        FileWriter fWriter = new FileWriter(f, false);
        fWriter.write(SearchPageString);
        fWriter.close();
    }

    /**
     * Formats Event Search Page with data from event
     */
    public static void formatEvent(Event event) throws IOException, URISyntaxException {
        SearchPagePath = BrowserPanel.getSearchPageUrlWithoutName().toURI();
        SearchPageString = readFile(SearchPagePath);
        SearchPageString = SearchPageString.replace(SearchPageString,
                        event.getName().toString() + "<br/>"
                                + " Venue: " + event.getAddress().toString() + "<br/>"
                                + " Time: " + pageDateFormat.format(event.getDateTime().dateTime) + "<br/>"
                                + " Phone: " + event.getPhone().toString()
                                + " Email: " + event.getEmail().toString() + "<br/>");
        writeFile(SearchPagePath);
    }
}
