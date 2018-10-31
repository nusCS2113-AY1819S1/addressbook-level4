package seedu.address.ui;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.OpenStockListVersionEvent;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Loads the specified .xml file as a .html page.
     */
    private void loadFileAsPage(String fileName) {
        File jarPath = new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String filePath = jarPath.getParent();
        String pathXsl = getClass().getResource("/docs/template.xsl").toString();
        try {
            transformXml((filePath + "\\versions\\" + fileName), pathXsl);
        } catch (Exception e) {
            //handle exception
        }
    }


    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleOpenStockListVersionEvent (OpenStockListVersionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFileAsPage(event.fileName);
    }

    /**
     * Transforms the given .xml file with the given .xsl template and outputs onto the browser
     * @param pathXml
     * @param pathXsl
     * @throws Exception
     */
    private void transformXml (String pathXml, String pathXsl) throws Exception {
        Source source = new StreamSource(pathXml);
        Source xsl = new StreamSource(pathXsl);
        final StreamResult output = new StreamResult(new StringWriter());
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xsl);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                "4");
        transformer.transform(source, output);
        browser.getEngine().loadContent(output.getWriter().toString());
    }
}
