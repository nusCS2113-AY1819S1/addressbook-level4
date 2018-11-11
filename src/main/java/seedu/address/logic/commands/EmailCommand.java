package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.email.Message.MESSAGE_MESSAGE_CONSTRAINTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import com.google.common.base.Throwables;
import com.sun.mail.smtp.SMTPSendFailedException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EmailUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.email.Message;
import seedu.address.model.email.Subject;
import seedu.address.model.group.Group;
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
            + "INDEX (must be a positive integer) or multiple INDEX separated by comma or g/INDEX "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_MESSAGE + "MESSAGE\n"
            + "Example: " + COMMAND_WORD + " 1 or 1,2,3 or g/2 "
            + PREFIX_SUBJECT + "Tutorial location "
            + PREFIX_MESSAGE + "Dear students, tutorial location has been changed to E01-04";

    public static final String MESSAGE_SUCCESS = "Email sent";
    public static final String MESSAGE_FAIL = "Send failed";
    public static final String MESSAGE_INVALID_ADDRESSES = "Invalid address found: "
            + "example@example.com (Recipient address reserved by RFC 2606)";
    public static final String MESSAGE_NO_RECIPIENT = "Group contains no recipient";
    public static final String MESSAGE_NO_LOGIN = "No login credentials found. Please login using 'login' command";
    public static final String MESSAGE_AUTHENTICATION_FAIL = "Invalid login credentials entered";
    public static final String SMTP_FAIL_EXCEPTION_MESSAGE = "Account unable to send any more emails due to spam";
    private static final Logger logger = LogsCenter.getLogger(EmailCommand.class);

    private boolean isSingleTarget = false;
    private boolean isMultipleTarget = false;
    private boolean isGroupTarget = false;
    private Index targetIndex;
    private Index targetGroup;
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
        this.isMultipleTarget = true;
    }

    /**
     * Creates a EmailCommand to add multiple {@code Persons} as the recipient
     */
    public EmailCommand(Index targetGroup, Subject subject, Message message, boolean isGroupTarget) {
        this.targetGroup = targetGroup;
        this.toSubject = subject;
        this.toMessage = message;
        this.isGroupTarget = isGroupTarget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!EmailUtil.hasLoginCredentials()) {
            logger.log(Level.WARNING, "No Login Credentials In System For EmailCommand");
            throw new CommandException(MESSAGE_NO_LOGIN);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (isSingleTarget) {
            logger.log(Level.INFO, "Creating a single target EmailCommand...");
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                logger.log(Level.WARNING, "EmailCommand Received Invalid Index For Single Target Email");
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person personToSend = lastShownList.get(targetIndex.getZeroBased());
            logger.log(Level.INFO, "Recipient: " + personToSend.getName() + " is added to recipient list");
            toSend.add(personToSend);
        }

        if (isMultipleTarget) {
            logger.log(Level.INFO, "Creating a multiple target EmailCommand...");
            for (Index targetIndex : targetMultipleIndex) {
                try {
                    Person personToSend = lastShownList.get(targetIndex.getZeroBased());
                    logger.log(Level.INFO, "Recipient: " + personToSend.getName() + " is added to recipient list");
                    toSend.add(personToSend);
                } catch (IndexOutOfBoundsException e) {
                    logger.log(Level.WARNING, "EmailCommand Received An Invalid Index For Multiple Target Email");
                    throw new CommandException(Messages.MESSAGE_INVALID_MULTIPLE_DISPLAYED_INDEX);
                }
            }
        }

        if (isGroupTarget) {
            logger.log(Level.INFO, "Creating a group target EmailCommand...");
            List<Group> groupList = model.getFilteredGroupList();
            if (targetGroup.getZeroBased() >= groupList.size()) {
                logger.log(Level.WARNING, "EmailCommand Received Invalid Index For Group Email");
                throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
            Group groupToSend = groupList.get(targetGroup.getZeroBased());
            Set<Person> personsInGroup = groupToSend.getPersons();
            for (Person person : personsInGroup) {
                logger.log(Level.INFO, "Recipient: " + person.getName() + " is added to recipient list");
            }
            toSend.addAll(personsInGroup);
        }

        try {
            EmailUtil.sendEmail(toSend, toSubject, toMessage);
        } catch (AuthenticationFailedException afe) {
            logger.log(Level.WARNING, "Email Credentials Entered But Incorrect");
            throw new CommandException(MESSAGE_AUTHENTICATION_FAIL);
        } catch (SMTPSendFailedException ssfe) {
            logger.log(Level.WARNING, Throwables.getStackTraceAsString(ssfe));
            throw new CommandException(setErrorMessageForSendFailedException(ssfe.getMessage()));
        } catch (SendFailedException sfe) {
            logger.log(Level.WARNING, Throwables.getStackTraceAsString(sfe));
            throw new CommandException(setErrorMessageForSendFailedException(sfe.getMessage()));
        } catch (MessagingException e) {
            logger.log(Level.WARNING, "General Failure, Please Refer To Stacktrace\n"
                    + Throwables.getStackTraceAsString(e));
            throw new CommandException(MESSAGE_FAIL);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toSend));
    }

    public static String setErrorMessageForSendFailedException(String e) {
        if (e.contains("MessageSubmissionExceededException")) {
            logger.log(Level.WARNING, "Message Size Limit Exceeded");
            return MESSAGE_FAIL + ": " + MESSAGE_MESSAGE_CONSTRAINTS;
        } else if (e.contains("OutboundSpamException")) {
            logger.log(Level.WARNING, "Email Blocked For Spam");
            return MESSAGE_FAIL + ": " + SMTP_FAIL_EXCEPTION_MESSAGE;
        } else if (e.contains("Invalid Addresses")) {
            logger.log(Level.WARNING, "An Invalid Recipient Is Found");
            return MESSAGE_FAIL + ": " + MESSAGE_INVALID_ADDRESSES;
        } else if (e.contains("No recipient addresses")) {
            logger.log(Level.WARNING, "No Recipients Specified");
            return MESSAGE_FAIL + ": " + MESSAGE_NO_RECIPIENT;
        } else {
            return MESSAGE_FAIL;
        }
    }
}
