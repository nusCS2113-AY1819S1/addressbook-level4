package seedu.address.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import seedu.address.model.event.Address;
import seedu.address.model.event.Attendance;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.tag.Tag;

/**
 * Formatter for Event Page HTML file
 */
public class EventPageFormatter {

    private static URI SEARCH_PAGE_PATH;
    private static String SEARCH_PAGE_STRING;

    public EventPageFormatter() {

    }

    private static String readFile(URI path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private static void writeFile(URI path) throws IOException {
        File f = new File(SEARCH_PAGE_PATH);
        FileWriter fWriter = new FileWriter(f, false);
        fWriter.write(SEARCH_PAGE_STRING);
        fWriter.close();
    }

    public static void formatEvent(Event event) throws IOException, URISyntaxException {
        SEARCH_PAGE_PATH = BrowserPanel.getSearchPageUrlWithoutName().toURI();
        SEARCH_PAGE_STRING = readFile(SEARCH_PAGE_PATH);
        //formatName(event.getName());
        writeFile(SEARCH_PAGE_PATH);
    }

    private static void formatName(Name name) {
        SEARCH_PAGE_STRING =
                SEARCH_PAGE_STRING.replaceAll("(<!-- Name -->)[^]*(<!-- /Name -->)", name.toString());
    }

    private static void formatPhone(Phone phone) {

    }

    private static void formatEmail(Email email) {

    }

    private static void formatAddress(Address address) {

    }

    private static void formatAttendance(Attendance attendance) {

    }

    private static void formatTags(Set<Tag> tags) {

    }
}
