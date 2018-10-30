package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Deletes a person from a group identified using their displayed index's
 * on the GroupListPanel & GroupPersonListPanel from the address book.
 */
public class DeleteGroupPersonCommand extends Command {

    public static final String COMMAND_WORD = "deletegroupstudent";
    public static final String COMMAND_WORD_2 = "dgs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a student from the group identified by the index numbers used in the "
            + "displayed group panel and students in group panel.\n"
            + "Parameters: " + PREFIX_GROUP_INDEX +  "GROUP_INDEX " + PREFIX_PERSON_INDEX + "PERSON_INDEX\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GROUP_INDEX +  "1 " + PREFIX_PERSON_INDEX + "1";

    public static final String MESSAGE_DELETE_GROUP_PERSON_SUCCESS = "Person index deleted from group at index [%1$s] : [%2$s]";

    private final Index groupTargetIndex;
    private final Index personTargetIndex;


    /**
     * Creates an DeleteGroupPersonCommand to
     * remove a person from a specified group
     */
    public DeleteGroupPersonCommand(Index groupTargetIndex, Index personTargetIndex) {
        requireAllNonNull(groupTargetIndex, personTargetIndex);
        this.groupTargetIndex = groupTargetIndex;
        this.personTargetIndex = personTargetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Group> lastShownList = model.getFilteredGroupList();

        if (groupTargetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group group = lastShownList.get(groupTargetIndex.getZeroBased());

        Set<Person> personSet = group.getPersons();
        List<Person> personList = new ArrayList<>(personSet);

        if (personTargetIndex.getZeroBased() >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = personList.get(personTargetIndex.getZeroBased());

        model.deleteGroupPerson(group, personToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_PERSON_SUCCESS,
                groupTargetIndex.getOneBased(), personTargetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupPersonCommand // instanceof handles nulls
                && groupTargetIndex.equals(((DeleteGroupPersonCommand) other).groupTargetIndex)
                && personTargetIndex.equals(((DeleteGroupPersonCommand) other).personTargetIndex)); // state check
    }

}
