package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Person;
import seedu.address.security.UserStub;

/**
 * Adds a user from the frined list to the others list
 */
public class UnfriendCommand extends Command {
    public static final String COMMAND_WORD = "unfriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the person with the index to from the friends list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_FRIEND_SUCCESS = "Person removed from the friend list!";

    private final Index targetIndex;

    public UnfriendCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> otherList = model.getOtherList(UserStub.getUser());

        if (targetIndex.getZeroBased() >= otherList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = otherList.get(targetIndex.getZeroBased());
        Person editedPerson = personToEdit;
        Person editedUser = UserStub.getUser();
        editedPerson.getFriends().remove(new Friend(UserStub.getUser().getName()));
        editedUser.getFriends().remove(new Friend(personToEdit.getName()));

        model.updatePerson(personToEdit, editedPerson);
        model.updatePerson(UserStub.getUser(), editedUser);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_REMOVE_FRIEND_SUCCESS);
    }
}
