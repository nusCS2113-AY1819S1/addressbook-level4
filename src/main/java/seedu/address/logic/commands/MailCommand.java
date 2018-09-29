//@@author lekoook
package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;

/**
 * Opens the system's default email application.
 * If arguments are present, the email attributes are filled accordingly.
 */
public class MailCommand extends Command {
    public static final String COMMAND_WORD = CliSyntax.COMMAND_MAIL;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails the person using default application.";
    public static final String MESSAGE_SUCCESS = "Mail application opened.";

    /**
     * Creates a default Mail command
     */
    public MailCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        } else {
            throw new CommandException("Not supported");
        }

        try {
            desktop.mail();
        } catch (UnsupportedOperationException | IOException | SecurityException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}