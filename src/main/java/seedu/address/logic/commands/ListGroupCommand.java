package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all groups in the address book to the user.
 */
public class ListGroupCommand extends Command {
    public static final String COMMAND_WORD = "listgroup";
    public static final String COMMAND_WORD_2 = "lg";
    public static final String MESSAGE_SUCCESS = "Listed all groups";
    public static final String LOG_PREDICATE_UPDATED = "Group predicate updated";

    private static final Logger logger = LogsCenter.getLogger(ListGroupCommand.class);

    /**
     * Lists groups.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return Successful command result.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        logger.log(Level.INFO, LOG_PREDICATE_UPDATED);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
