package seedu.address.logic.comments;

import java.util.Vector;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;




/**
 * Adds comments section.
 */
public abstract class Comments {

    private static String input;
    private static Vector comments;

    /**
     *  Constructor to make sure that used Vector and path is initialised
     */
    public Comments (String input) {
        this.input = input;
        this.comments = this.parseCommentSection(input);
    }

    public Vector getComments() {
        return comments;
    }

    public String getInput() {
        return input;
    }

    /**
     *  Runs a pre-processing to ensure that strings can be stored as a vector
     */
    public Vector parseCommentSection(String input) {
        Vector comments = new Vector();
        Document htmlfile = null;
        htmlfile = Jsoup.parse(input);
        Element element = htmlfile.select("ol").first();
        Elements divChildren = element.children();

        Elements detachedDivChildren = new Elements();
        for (Element elem : divChildren) {
            Element detachedChild = new Element(Tag.valueOf(elem.tagName()),
                    elem.baseUri(), elem.attributes().clone());
            detachedDivChildren.add(detachedChild);
        }
        for (Element elem : divChildren) {
            comments.add(elem.ownText());
        }
        return comments;
    }

    /**
     *  Rewrites String to after a change has happened
     */
    public static String rewrite(Vector commentsVector) {
        String comments = "<span>Comment Section</span>\n<ol>";
        for (int i = 0; i < commentsVector.size(); i++) {
            if (commentsVector.get(i).toString().length() == 0) {
                continue;
            }
            comments += "\n" + "<li>" + commentsVector.get(i) + "</li>" + " <br>";
            System.out.println(comments);
        }
        comments += "\n</ol>";
        return comments;
    }

}
