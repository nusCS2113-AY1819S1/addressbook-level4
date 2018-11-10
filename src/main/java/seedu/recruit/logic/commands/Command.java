package seedu.recruit.logic.commands;

import java.io.IOException;
import java.security.GeneralSecurityException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing of command params
     */
    public abstract CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs)
            throws CommandException, ParseException;

}
