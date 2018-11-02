package seedu.address.logic.comments;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 *  A facade to access comment related classes, AddComment, DeleteComment and ReplyComment classes
 */
public class CommentFacade {

    private AddComment add;
    private DeleteComment delete;
    private ReplyComment reply;

    /**
    *  Constructor to intialise all the comment commands
    */
    public CommentFacade() {
        add = new AddComment();
        delete = new DeleteComment();
        reply = new ReplyComment();
    }

    /**
    *  Add comment function
    */
    public String addComment(String input, String comment, String username) {
        add.initComments(input);
        return add.addComment(comment, username);
    }

    /**
    *  Delete comment function
    */
    public String deleteComment(String input, int line) throws CommandException {
        delete.initComments(input);
        return delete.deleteComment(line);
    }

    /**
    *  Reply comment function
    */
    public String replyComment(String input, String comment, int line, String username) throws CommandException {
        reply.initComments(input);
        return reply.replyComment(comment, line, username);
    }
}
