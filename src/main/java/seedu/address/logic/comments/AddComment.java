package seedu.address.logic.comments;

/**
 *  Adds Comment to the end of the comments section
 */
public class AddComment extends Comments {

    /**
     *  Appends comment to the end of the current vector and stores
     */
    public String addComment(String comment) {
        v.add(comment);
        return rewrite(v,input);
    }

}
