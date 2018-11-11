//@@author rajdeepsh

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Adds persons to a group via indexes.
 */
public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addgroup";
    public static final String COMMAND_WORD_2 = "ag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds persons to a group specified. "
            + "Parameters: "
            + PREFIX_GROUP_INDEX + "GROUP_INDEX "
            + PREFIX_PERSON_INDEX + "PERSON_INDEX...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP_INDEX + "1 "
            + PREFIX_PERSON_INDEX + "2";

    public static final String MESSAGE_SUCCESS = "Person index(s) added to group at index %1$s";
    public static final String MESSAGE_DUPLICATE_PERSONS = "Person(s) already exist in group";
    public static final String LOG_DUPLICATE_PERSONS = "Person(s) already detected in group";
    public static final String LOG_COMMIT = "Version Committed";
    public static final String LOG_COMMAND_SUCCESS = "Person(s) have been added to group";
    public static final String LOG_INVALID_PERSON_INDEX = "Invalid person index detected";
    public static final String LOG_INVALID_GROUP_INDEX = "Invalid group index detected";

    private static final Logger logger = LogsCenter.getLogger(AddGroupCommand.class);

    private final AddGroup toAdd;
    private boolean shouldCommit;

    /**
     * Receives people and group needed for adding.
     *
     * @param toAdd AddGroup containing required group and persons for adding.
     */
    public AddGroupCommand(AddGroup toAdd) {
        requireAllNonNull(toAdd);
        this.toAdd = toAdd;
        this.shouldCommit = true;
    }

    /**
     * Adds people to a group.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return Successful command result.
     * @throws CommandException If index is invalid or duplicate persons found.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (!toAdd.validGroupIndex(lastShownGroupList.size())) {
            logger.log(Level.WARNING, LOG_INVALID_GROUP_INDEX);
            throw new CommandException(MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        } else if (!toAdd.validPersonIndexSet(lastShownPersonList.size())) {
            logger.log(Level.WARNING, LOG_INVALID_PERSON_INDEX);
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        toAdd.setPersonSet(lastShownPersonList);
        toAdd.setGroupSet(lastShownGroupList);

        if (model.hasPersonInGroup(toAdd)) {
            logger.log(Level.WARNING, LOG_DUPLICATE_PERSONS);
            throw new CommandException(MESSAGE_DUPLICATE_PERSONS);
        }

        model.addGroup(toAdd);
        logger.log(Level.INFO, LOG_COMMAND_SUCCESS);

        if (shouldCommit) {
            model.commitAddressBook();
            logger.log(Level.INFO, LOG_COMMIT);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

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
                || (other instanceof AddGroupCommand // instanceof handles nulls
                && toAdd.equals(((AddGroupCommand) other).toAdd));
    }

    /**
     * Sets flag for committing.
     *
     * @param shouldCommit Committing flag.
     */
    public void setShouldCommit(boolean shouldCommit) {
        this.shouldCommit = shouldCommit;
    }
}
