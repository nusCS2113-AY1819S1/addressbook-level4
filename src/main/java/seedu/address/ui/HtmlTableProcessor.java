package seedu.address.ui;

import java.util.ArrayList;

/**
 * This class handles utility methods for the rendering of Strings to HTML tables.
 */
public class HtmlTableProcessor {

    /**
     * This method renders the table titles component .
     */
    public static String renderTableStart(ArrayList<String> tableHeaders) {

        StringBuilder sb = new StringBuilder();

        sb.append(getTableHeadStart());
        for (String s: tableHeaders) {
            sb.append(getTableItem(s));
        }
        sb.append(getTableHeadEnd());
        return sb.toString();

    }

    /**
     * This method returns HTML resembling a single item in a HTML table.
     */
    public static String renderTableItem(ArrayList<String> tableItem) {

        StringBuilder sb = new StringBuilder();

        sb.append(getTableItemStart());
        for (String s: tableItem) {
            sb.append(getTableItem(s));
        }
        sb.append(getTableItemEnd());

        return sb.toString();

    }

    public static String getTableHeadStart() {
        return "<table class=\"table table-hover\">\n"
                + "  <thead>\n"
                + "    <tr>\n";
    }

    public static String getTableHeadItem(String item) {
        return "<th scope=\"col\">" + item + "</th>\n";
    }

    public static String getH3Representation(String text) {
        return "<h3>" + text + "</h3>\n";
    }

    public static String getTableHeadEnd() {
        return "</tr>"
                + "  </thead>"
                + "  <tbody>\n";
    }

    public static String getTableItemStart() {
        return "<tr class=\"table-active\">\n";
    }

    public static String getTableItem(String item) {
        return "<td>" + item + "</td>\n";
    }

    public static String getTableItemEnd() {
        return "</tr>\n";
    }

    public static String getTableEnd() {
        return "\"  </tbody>\\n\" +\n"
                + "\"</table> \"";
    }
}
