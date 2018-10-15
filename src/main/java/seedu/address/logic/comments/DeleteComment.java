package seedu.address.logic.comments;

/**
 *  Deletes a comment given the line of it
 */
public class DeleteComment extends Comments {

    /**
     *  Admin only: Can delete comment given event Comment Section indexx and Line
     */
    public String deleteComment(int line) {
        try {
            v.remove(line-1);
        } catch(Exception e) {
            System.out.println("Line error");
        }
        return rewrite(v,input);
    }
}
