package seedu.address.ui;

import java.util.AbstractMap;
import java.util.List;

/**
 * Contains utility methods to generate HTML strings for general display.
 */
public class HtmlProcessor {

    /**
     * Constructs a detailed view of any single entity in a horizontal form layout.
     * The rendering leverages on the table utility methods exposed in {@code HtmlTableProcessor}.
     */
    public static String constructDetailedView(List<AbstractMap.SimpleEntry<String, String>> details) {
        StringBuilder sb = new StringBuilder();

        sb.append(HtmlTableProcessor.getBanner("Module View"));
        sb.append(HtmlTableProcessor.getTableWithoutBorderStart());
        sb.append(HtmlTableProcessor.getTableBodyStart());

        for (AbstractMap.SimpleEntry<String, String> kvp : details) {
            sb.append(HtmlTableProcessor.getTableRowStart());
            sb.append(HtmlTableProcessor.getTableItem(kvp.getKey()));
            sb.append(HtmlTableProcessor.getTableItem(kvp.getValue()));
            sb.append(HtmlTableProcessor.getTableRowEnd());
        }

        sb.append(HtmlTableProcessor.getTableBodyEnd());
        sb.append(HtmlTableProcessor.getTableWithoutBorderEnd());

        return sb.toString();
    }

    public static String getOrderedListStart() {
        return "<ol class=\"pl-4\">";
    }

    public static String getListItem(String item) {
        return "<li>" + item + "</li>";
    }

    public static String getOrderedListEnd() {
        return "</ol>";
    }
}
