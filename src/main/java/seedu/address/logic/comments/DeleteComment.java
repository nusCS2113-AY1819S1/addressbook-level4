package seedu.address.logic.comments;

import java.util.Vector;

/**
 *  Deletes a comment given the line of it
 */
public class DeleteComment extends Comments {

    /**
     *  Admin only: Can delete comment given event Comment Section indexx and Line
     */
    public String deleteComment(int line) {
        Vector comments = new Vector();
        try {
            comments = getComments();
            comments.remove(line - 1);
        } catch (Exception e) {
            System.out.println("Line error");
        }
        return rewrite(comments, getInput());
    }
}
