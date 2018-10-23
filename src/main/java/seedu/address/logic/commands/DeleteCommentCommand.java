package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comments.DeleteComment;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Delete a comment in the comment section of the event given the line
 */
public class DeleteCommentCommand extends Command {

    public static final String COMMAND_WORD = "deleteComment";

    public static final String MESSAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LINE + "LINE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE + "91 ";

    public static final String MESSAGE_DELETE_COMMENT = "Comment deleted for Event %1$s at Line %2$s";
    public static final String MESSAGE_LINE_INVALID = "Line is invalid, try again";

    private final Index index;
    private final EditCommand.EditEventDescriptor editCommentDescriptor;
    private int line = 0;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public DeleteCommentCommand(Index index, int line) {
        requireNonNull(index);
        requireNonNull(line);

        this.index = index;
        this.line = line;
        this.editCommentDescriptor = new EditCommand.EditEventDescriptor();
    }

    public int getLine() {
        return this.line;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> filteredEventList = model.getFilteredEventList();


        if (index.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        Event eventToEdit = filteredEventList.get(index.getZeroBased());
        Event editedEvent = EditCommand.createEditedEvent(eventToEdit, editCommentDescriptor);

        model.updateEvent(eventToEdit, editedEvent);
        model.commitEventManager();

        String test =
                "<span>Comment Section</span>\n"
                        + "<ol>\n"
                        + "</ol>";
        DeleteComment comments = new DeleteComment(test);
        test = comments.deleteComment(getLine());
        File savingFile = new File("C:/Users/Gerald/Desktop/test/1.html");
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(savingFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.write(test.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_DELETE_COMMENT, index.getOneBased(), getLine()));
    }

}
