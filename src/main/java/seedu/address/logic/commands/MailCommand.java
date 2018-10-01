//@@author lekoook
package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

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
        URI uriToMail;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            ArrayList<String> emailList = retrieveEmails(model.getFilteredPersonList());
            uriToMail = createUri(emailList);

        } else {
            throw new CommandException("Not supported");
        }

        try {
            desktop.mail(uriToMail);
        } catch (UnsupportedOperationException | IOException | SecurityException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Extracts all emails given a list of Person.
     * @param lastShownList the list of Person.
     * @return the list of extracted emails.
     */
    private ArrayList<String> retrieveEmails(List<Person> lastShownList) {
        ArrayList<String> emailList = new ArrayList<>();
        for (Person person : lastShownList) {
            emailList.add(person.getEmail().value);
        }
        return emailList;
    }

    /**
     * Builds the URI to be used in opening the mail application.
     * @param emailList the list of extracted emails to send to.
     * @return the URI to be used by the mail application.
     * @throws CommandException if there is syntax error in the URI.
     */
    private URI createUri(ArrayList<String> emailList) throws CommandException {
        StringBuilder uriToMail = new StringBuilder("mailto:");
        URI uri;
        for (String email : emailList) {
            uriToMail.append(email).append(",");
        }

        try {
            uri = new URI(uriToMail.toString());
        } catch (URISyntaxException e) {
            throw new CommandException(e.getMessage());
        }
        return uri;
    }
}