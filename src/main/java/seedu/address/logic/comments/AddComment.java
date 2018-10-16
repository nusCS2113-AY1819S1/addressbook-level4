package seedu.address.logic.comments;

import java.util.Vector;

/**
 *  Adds Comment to the end of the comments section
 */
public class AddComment extends Comments {

    /**
     *  Appends comment to the end of the current vector and stores
     */
    public String addComment(String comment) {
        Vector comments = new Vector();
        comments = getComments();
        comments.add(comment);
        return rewrite(comments, getInput());
    }

}
