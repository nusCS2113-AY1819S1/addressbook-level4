package seedu.address.logic.commands.EmailCommand;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javafx.collections.ObservableList;

import com.google.api.services.gmail.Gmail;

import seedu.address.commons.core.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Starts the 3-step process of Email
 */
public class EmailInitialiseCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails specified candidates about specified job offers"
            + "or specified companies about specified candidates.";
    protected static Gmail service;
    static {
        try {
            service = Email.init();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
    protected static ObservableList<?> recipients;
    protected static ObservableList<?> contents;
    public static boolean areRecipientsCandidates;
    protected static final String from = "cs2113.f09.4@gmail.com";
    protected static String to;
    protected static String subject;
    protected static String bodyText;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        LogicManager.setLogicState(EmailSelectRecipientsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectRecipientsCommand.MESSAGE_USAGE);
    }
}
