package unrefactored.ui;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

public class URIBuilder {
    private StringBuilder filePath;
    private StringBuilder queries;

    public URIBuilder() {
        filePath = new StringBuilder();
        queries = new StringBuilder();
    }

    public void addPath(String folder) {
        filePath.append("/");
        filePath.append(folder);
    }

    public void addPath(URL folder) {
        filePath.append(folder);
    }

    public void addQuery(String parameter, String value) throws UnsupportedEncodingException {
        if (queries.toString().length() > 0) {
            queries.append("&");
        }
        queries.append(parameter);
        queries.append("=");
        queries.append(URLEncoder.encode(value, "UTF-8"));
    }

    public String getURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(null, null, filePath.toString(), queries.toString(), null);
        return uri.toString();
    }
}
