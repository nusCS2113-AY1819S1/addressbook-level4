package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;

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
import seedu.address.logic.comments.AddComment;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds a comment to the end comment section of the event
 */
public class AddCommentCommand extends Command {
    public static final String COMMAND_WORD = "addComment";

    public static final String MESSAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COMMENT + "johndoe@example.com is here";

    public static final String MESSAGE_ADD_COMMENT = "Comment [%1$s] replied for Event %2$s";

    private final Index index;
    private final EditCommand.EditPersonDescriptor editCommentDescriptor;
    private String comment = null;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editPersonDescriptor details to edit the event with
     */
    public AddCommentCommand(Index index, String comment) {
        requireNonNull(index);
        requireNonNull(comment);

        this.index = index;
        this.comment = comment;
        this.editCommentDescriptor = new EditCommand.EditPersonDescriptor();
    }

    public String getComment() {
        return this.comment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        String test =
                "<span>Comment Section</span>\n"
                        + "<ol>\n"
                        + "<li>hello</li>\n"
                        + "<li>My name is Gerald</li>\n"
                        + "<li>What is your name?</li>\n"
                        + "</ol>";

        AddComment comments = new AddComment(test);
        test = comments.addComment(getComment());

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

        requireNonNull(model);
        List<Event> filteredEventList = model.getFilteredEventList();


        if (index.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        Event eventToEdit = filteredEventList.get(index.getZeroBased());
        Event editedEvent = EditCommand.createEditedPerson(eventToEdit, editCommentDescriptor);

        model.updateEvent(eventToEdit, editedEvent);
        model.commitEventManager();

        return new CommandResult(String.format(MESSAGE_ADD_COMMENT, getComment(), index.getOneBased()));
    }

}
