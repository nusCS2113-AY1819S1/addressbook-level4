package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.List;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import com.sun.mail.smtp.SMTPSendFailedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EmailUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.email.Message;
import seedu.address.model.email.Subject;
import seedu.address.model.person.Person;


/**
 * Sends an email to a person identified using it's displayed index from the address book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "sendmail";
    public static final String COMMAND_WORD_2 = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sends an email to the recipient identified "
            + "by the index number used in the displayed person list\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_MESSAGE + "MESSAGE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBJECT + "Tutorial location "
            + PREFIX_MESSAGE + "Dear students, tutorial location has been changed to E01-04";

    public static final String MESSAGE_SUCCESS = "Email sent";
    public static final String MESSAGE_FAIL = "Unable to send email due to unforeseen error.";
    public static final String MESSAGE_FAIL_DUE_TO_SPAM = "Unable to use this account to send more emails due to spam.";
    public static final String MESSAGE_NO_LOGIN = "No login credentials found. Please login using 'login' command";
    public static final String MESSAGE_AUTHENTICATION_FAIL = "Invalid login credentials entered";

    private boolean isSingleTarget;
    private Index targetIndex;
    private List<Index> targetMultipleIndex;
    private List<Person> toSend = new ArrayList<>();
    private Subject toSubject;
    private Message toMessage;

    /**
     * Creates a EmailCommand to add the specified {@code Person} as the recipient
     */
    public EmailCommand(Index targetIndex, Subject subject, Message message) {
        this.targetIndex = targetIndex;
        this.toSubject = subject;
        this.toMessage = message;
        this.isSingleTarget = true;
    }

    /**
     * Creates a EmailCommand to add multiple {@code Persons} as the recipient
     */
    public EmailCommand(List<Index> targetMultipleIndex, Subject subject, Message message) {
        this.targetMultipleIndex = targetMultipleIndex;
        this.toSubject = subject;
        this.toMessage = message;
        this.isSingleTarget = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!EmailUtil.hasLoginCredentials()) {
            throw new CommandException(MESSAGE_NO_LOGIN);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (isSingleTarget) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person personToSend = lastShownList.get(targetIndex.getZeroBased());
            toSend.add(personToSend);
        } else {
            for (Index targetIndex : targetMultipleIndex) {
                try {
                    Person personToSend = lastShownList.get(targetIndex.getZeroBased());
                    toSend.add(personToSend);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(Messages.MESSAGE_INVALID_MULTIPLE_DISPLAYED_INDEX);
                }
            }
        }

        try {
            EmailUtil.sendEmail(toSend, toSubject, toMessage);
        } catch (AuthenticationFailedException afe) {
            throw new CommandException(MESSAGE_AUTHENTICATION_FAIL);
        } catch (SMTPSendFailedException sfe) {
            throw new CommandException(MESSAGE_FAIL_DUE_TO_SPAM);
        } catch (MessagingException e) {
            throw new CommandException(MESSAGE_FAIL);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toSend));
    }
}
