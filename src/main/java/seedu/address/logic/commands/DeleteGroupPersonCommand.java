package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Deletes a person from a group via indexes.
 */
public class DeleteGroupPersonCommand extends Command {

    public static final String COMMAND_WORD = "deletegroupstudent";
    public static final String COMMAND_WORD_2 = "dgs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a student from the group identified by the index numbers used in the "
            + "displayed group panel and students in group panel.\n"
            + "Parameters: " + PREFIX_GROUP_INDEX + "GROUP_INDEX " + PREFIX_PERSON_INDEX + "PERSON_INDEX\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_GROUP_INDEX + "1 " + PREFIX_PERSON_INDEX + "1";

    public static final String MESSAGE_DELETE_GROUP_PERSON_SUCCESS =
            "Person index deleted from group at index [%1$s] : [%2$s]";
    public static final String LOG_COMMIT = "Version Committed";
    public static final String LOG_COMMAND_SUCCESS = "Person has been deleted from group";
    public static final String LOG_INVALID_PERSON_INDEX = "Invalid person index detected";
    public static final String LOG_INVALID_GROUP_INDEX = "Invalid group index detected";

    private static final Logger logger = LogsCenter.getLogger(DeleteGroupPersonCommand.class);

    private final Index groupTargetIndex;
    private final Index personTargetIndex;

    /**
     * Receives indexes for deleting.
     *
     * @param groupTargetIndex Group index to delete person from.
     * @param personTargetIndex Person index to delete.
     */
    public DeleteGroupPersonCommand(Index groupTargetIndex, Index personTargetIndex) {
        requireAllNonNull(groupTargetIndex, personTargetIndex);
        this.groupTargetIndex = groupTargetIndex;
        this.personTargetIndex = personTargetIndex;
    }

    /**
     * Deletes a person from a group.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return Successful command result.
     * @throws CommandException If index is invalid.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Group> lastShownList = model.getFilteredGroupList();

        if (groupTargetIndex.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.WARNING, LOG_INVALID_GROUP_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group group = lastShownList.get(groupTargetIndex.getZeroBased());

        Set<Person> personSet = group.getPersons();
        List<Person> personList = new ArrayList<>(personSet);

        if (personTargetIndex.getZeroBased() >= personList.size()) {
            logger.log(Level.WARNING, LOG_INVALID_PERSON_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = personList.get(personTargetIndex.getZeroBased());

        model.deleteGroupPerson(group, personToDelete);
        logger.log(Level.INFO, LOG_COMMAND_SUCCESS);
        model.commitAddressBook();
        logger.log(Level.INFO, LOG_COMMIT);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_PERSON_SUCCESS,
                groupTargetIndex.getOneBased(), personTargetIndex.getOneBased()));
    }

    /**
     * Returns true if the objects are the same.
     *
     * @param other Object to compare with.
     * @return Result of comparison.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupPersonCommand // instanceof handles nulls
                && groupTargetIndex.equals(((DeleteGroupPersonCommand) other).groupTargetIndex)
                && personTargetIndex.equals(((DeleteGroupPersonCommand) other).personTargetIndex)); // state check
    }

}
