package seedu.address.ui;

/**
 * This class handles utility methods for the rendering of Strings to HTML cards.
 */
public class HtmlCardProcessor {

    public static String getCardStart() {
        return "<div class=\"card\">";
    }

    public static String getCardStartV2() {
        return "<div class=\"card text-white bg-primary mb-3\">";
    }

    /**
     * This method renders an h3 header to the card.
     */
    public static String renderCardHeader(String headingType, String header) {
        StringBuilder sb = new StringBuilder();

        sb.append("  <" + headingType + " class=\"card-header\">");
        sb.append(header);
        sb.append("</" + headingType + ">\n");

        return sb.toString();
    }

    public static String renderCardTitle(String title) {
        StringBuilder sb = new StringBuilder();

        sb.append("    <h4 class=\"card-title\">");
        sb.append(title);
        sb.append("</h4>");

        return sb.toString();
    }

    public static String renderCardSubtitle(String subtitle) {
        StringBuilder sb = new StringBuilder();

        sb.append("    <h6 class=\"card-subtitle mb-2 text-muted\">");
        sb.append(subtitle);
        sb.append("</h6>");

        return sb.toString();
    }

    public static String getCardBodyStart() {
        return "<div class=\"card-body\">\n";
    }

    public static String getDivEndTag() {
        return "</div>\n";
    }

    public static String renderCardText(String text) {
        StringBuilder sb = new StringBuilder();

        sb.append("    <p class=\"card-text\">");
        sb.append(text);
        sb.append("</p>\n");

        return sb.toString();
    }

    /**
     * This method renders a footer to the card.
     */
    public static String renderCardFooter(String footer) {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"card-footer text-muted\">\n");
        sb.append("  " + footer + "\n");
        sb.append(getDivEndTag());

        return sb.toString();
    }
}
