package seedu.address.logic.commands;

import static seedu.address.logic.commands.MailCommand.TYPE_ALL;
import static seedu.address.logic.commands.MailCommand.TYPE_GROUPS;
import static seedu.address.logic.commands.MailCommand.TYPE_SELECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class MailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_selectedPersons_success() {
        MailCommand mailCommand = new MailCommand(TYPE_SELECTION);

        // Selects the first 3 persons in the address book.
        model.setSelectedPersons(new ArrayList<>(model.getFilteredPersonList().subList(0, 3)));

        String expectedMessage = MailCommand.MESSAGE_SUCCESS
                + buildRecipients(new ArrayList<>(model.getSelectedPersons()));

        CommandTestUtil.assertCommandSuccess(mailCommand, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void execute_allPersons_success() {
        MailCommand mailCommand = new MailCommand(TYPE_ALL);

        String expectedMessage = MailCommand.MESSAGE_SUCCESS
                + buildRecipients(new ArrayList<>(model.getFilteredPersonList()));

        CommandTestUtil.assertCommandSuccess(mailCommand, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void execute_selectedGroups_success() {
        Tag tagToUse = (Tag) model.getFilteredPersonList().get(0).getTags().toArray()[0];
        String tagToUseInString = tagToUse.toString();

        MailCommand mailCommand = new MailCommand(TYPE_GROUPS, tagToUseInString);

        // Retrieve the list of persons with that tag and build the expected message with it.
        ArrayList<Person> mailingList = new ArrayList<>(model.getFilteredPersonList());
        mailingList.removeIf(person -> !person.getTags().contains(tagToUse));
        String expectedMessage = MailCommand.MESSAGE_SUCCESS + buildRecipients(mailingList);

        CommandTestUtil.assertCommandSuccess(mailCommand, model, commandHistory, expectedMessage, model);
    }

    /**
     * Builds the string of names of recipients mailed to.
     * @param mailingList the list of recipients.
     * @return the string including all recipients.
     */
    private String buildRecipients(ArrayList<Person> mailingList) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < mailingList.size(); i++) {
            output.append(mailingList.get(i).getName().fullName);
            if (i < mailingList.size() - 1) {
                output.append(", ");
            }
        }
        return output.toString();
    }
}
