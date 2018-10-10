package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Logger;
import java.util.Set;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Address;
import seedu.address.model.event.Attendance;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.ui.BrowserPanel;

/**
 * Formatter for Event Page HTML file
 */
public class EventPageFormatter {

    private static String SEARCH_PAGE_PATH;
    private static String SEARCH_PAGE_STRING;

    private final Logger logger = LogsCenter.getLogger(getClass());

    public EventPageFormatter() throws IOException {
        this.SEARCH_PAGE_PATH = BrowserPanel.getSearchPageUrlWithoutName().toString();
        this.SEARCH_PAGE_STRING = readFile(this.SEARCH_PAGE_PATH, StandardCharsets.UTF_8);
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void formatEvent(Event event) {

    }

    private void formatName() {

    }

    private void formatPhone() {

    }

    private void formatEmail() {

    }

    private void formatAddress() {

    }

    private void formatTags() {

    }
}
