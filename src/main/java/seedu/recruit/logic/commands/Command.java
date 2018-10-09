package seedu.recruit.logic.commands;

import java.io.IOException;
import java.security.GeneralSecurityException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;

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
     * @throws IOException Something to do with the email command
     * @throws GeneralSecurityException Something to do with email command
     */
    public abstract CommandResult execute(Model model, CommandHistory history)
            throws CommandException, IOException, GeneralSecurityException;

}
