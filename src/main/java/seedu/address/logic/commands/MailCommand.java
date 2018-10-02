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
import seedu.address.model.tag.Tag;

/**
 * Opens the system's default email application.
 * If arguments are present, the email attributes are filled accordingly.
 */
public class MailCommand extends Command {
    public static final String COMMAND_WORD = CliSyntax.COMMAND_MAIL;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails the person using default application.";
    private static final String MESSAGE_SUCCESS = "Mailing to: ";
    private static final String MESSAGE_UNSUPPORTED = "System mail application is unsupported.";

    public static final int TYPE_SELECTION = 1;
    public static final int TYPE_GROUPS = 2;
    public static final int TYPE_ALL = 3;

    /**
     * Instance variables
     */
    private int mailType;
    private String mailArgs;
    private Desktop desktop;

    /**
     * Creates a default Mail command
     */
    public MailCommand() {
        desktop = Desktop.getDesktop();
    }

    public MailCommand(int mailType) {
        this.mailType = mailType;
            desktop = Desktop.getDesktop();
    }

    public MailCommand(int mailType, String mailArgs) {
        this.mailType = mailType;
        this.mailArgs = mailArgs;
            desktop = Desktop.getDesktop();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        URI uriToMail;

        // Unsupported desktops will lead to error and crash
        if (!Desktop.isDesktopSupported()) {
            throw new CommandException(MESSAGE_UNSUPPORTED);
        }

        switch(mailType) {
        case TYPE_SELECTION:
            uriToMail = mailToSelection(model);
            break;
        case TYPE_GROUPS:
            uriToMail = mailToGroups(model, new Tag(mailArgs.trim()));
            break;
        default:
            uriToMail = mailToAll(model);
        }

        try {
            desktop.mail(uriToMail);
        } catch (UnsupportedOperationException | IOException | SecurityException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    private URI mailToSelection(Model model) throws CommandException {
        List<Person> list = model.getSelectedPersons();
        ArrayList<String> emailList = retrieveEmails(list);
        return createUri(emailList);
    }

    /**
     * Creates an URI that consists of email address from given groups
     * @param model containing the groups
     * @return the URI consisting of extracted email addresses
     * @throws CommandException if there is error in creating URI
     */
    private URI mailToGroups(Model model, Tag tag) throws CommandException {
        ArrayList<String> emailList = retrieveEmailsFromGroups(model.getFilteredPersonList(), tag);
        return createUri(emailList);
    }

    /**
     * Creates an URI that consists of email address from all contacts
     * @param model containing the contacts
     * @return the URI consisting of extracted email addresses
     * @throws CommandException if there is error in creating URI
     */
    private URI mailToAll(Model model) throws CommandException {
        ArrayList<String> emailList = retrieveEmails(model.getFilteredPersonList());
        return createUri(emailList);
    }

    /**
     * Extracts all emails given a list of Person.
     * @param personList the list of Person.
     * @return the list of extracted emails.
     */
    private ArrayList<String> retrieveEmails(List<Person> personList) {
        ArrayList<String> emailList = new ArrayList<>();
        for (Person person : personList) {
            emailList.add(person.getEmail().value);
        }
        return emailList;
    }

    private ArrayList<String> retrieveEmailsFromGroups(List<Person> personList, Tag tag) {
        ArrayList<String> emailList = new ArrayList<>();
        for (Person person : personList) {
            if (person.getTags().contains(tag)) {
                emailList.add(person.getEmail().value);
            }
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