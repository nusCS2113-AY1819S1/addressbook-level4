package seedu.address.logic.comments;

/**
 *  Replies to a comment on a given line with a comment
 */
public class ReplyComment extends Comments {

    /**
     *  Replies with the comment to event Comment section of index and line
     */
    public String replyComment(String comment, int line) {
        try {
            v.add(line, "REPLY--->" + comment);
        } catch (Exception e) {
            System.out.println("Line error");
        }
        return rewrite(v,input);
    }

}
