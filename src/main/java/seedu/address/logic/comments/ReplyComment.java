package seedu.address.logic.comments;

import java.util.Vector;

/**
 *  Replies to a comment on a given line with a comment
 */
public class ReplyComment extends Comments {

    /**
     *  Replies with the comment to event Comment section of index and line
     */
    public String replyComment(String comment, int line) {
        Vector comments = new Vector();
        try {
            comments = getComments();
            comments.add(line, "REPLY--->" + comment);
        } catch (Exception e) {
            System.out.println("Line error");
        }
        return rewrite(comments, getInput());
    }

}
