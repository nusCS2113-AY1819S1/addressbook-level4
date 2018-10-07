package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EmailUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.email.Message;
import seedu.address.model.email.Subject;
import seedu.address.model.person.Person;

public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "sendmail";
    public static final String COMMAND_WORD_2 = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sends an email to the recipient identified " +
            "by the index number used in the displayed person list\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_MESSAGE + "MESSAGE\n"
            +"Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBJECT + "Tutorial location "
            + PREFIX_MESSAGE + "Dear students, tutorial location has been changed to E01-04";

    public static final String MESSAGE_SUCCESS = "Email sent";
    public static final String MESSAGE_FAIL = "Unable to send email due to unforeseen error.";

    private final Index targetIndex;
    private final List<Person> toSend = new ArrayList<>();
    private final Subject toSubject;
    private final Message toMessage;

    /**
     * Creates a EmailCommand to add the specified {@code Person} as the recipient
     */
    public EmailCommand(Index targetIndex, Subject subject, Message message) {
        this.targetIndex = targetIndex;
        toSubject = subject;
        toMessage = message;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if(targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSend = lastShownList.get(targetIndex.getZeroBased());
        toSend.add(personToSend);

        try {
            EmailUtil.sendEmail(toSend, toSubject, toMessage);
        } catch (MessagingException e) {
            throw new CommandException(MESSAGE_FAIL);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toSend));
    }
}
