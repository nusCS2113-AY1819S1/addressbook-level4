package seedu.address.ui;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * To build a uri path for html files specifically, in HelpWindow.html
 */
public class UriBuilder {
    private StringBuilder filePath;
    private StringBuilder queries;

    public UriBuilder() {
        filePath = new StringBuilder();
        queries = new StringBuilder();
    }
    /**
     * Adding @param folder to the path of url
     */
    public void addPath(String folder) {
        filePath.append("/");
        filePath.append(folder);
    }

    /**
     * Adding @param folder to the path of url
     */
    public void addPath(URL folder) {
        filePath.append(folder);
    }

    /**
     * Adds a html query
     * @param parameter
     * @param value
     * @throws UnsupportedEncodingException
     */
    public void addQuery(String parameter, String value) throws UnsupportedEncodingException {
        if (queries.toString().length() > 0) {
            queries.append("&");
        }
        queries.append(parameter);
        queries.append("=");
        queries.append(URLEncoder.encode(value, "UTF-8"));
    }

    public String getUrl() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(null, null, filePath.toString(), queries.toString(), null);
        return uri.toString();
    }
}
