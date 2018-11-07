package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.EmailUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Email;


/**
 * Sends an email to a person identified using it's displayed index from the address book.
 */
public class EmailLoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_WORD_2 = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters your email credentials into the system \n"
            + "Parameters: "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "abc@hotmail.com "
            + PREFIX_PASSWORD + "password123";

    public static final String MESSAGE_SUCCESS = "Login credentials entered";
    private static final Logger logger = LogsCenter.getLogger(EmailLoginCommand.class);

    private String userEmail;
    private String userPassword;

    public EmailLoginCommand(Email email, String password) {
        this.userEmail = email.toString();
        this.userPassword = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        logger.log(Level.WARNING, "User Email Is Entered");
        EmailUtil.setUserEmailAddress(userEmail);
        logger.log(Level.WARNING, "User Password Is Entered");
        EmailUtil.setUserEmailPassword(userPassword);

        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }
}
