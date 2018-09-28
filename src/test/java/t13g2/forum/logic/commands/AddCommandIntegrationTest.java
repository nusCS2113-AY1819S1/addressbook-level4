package t13g2.forum.logic.commands;

import org.junit.Before;
import org.junit.Test;

import t13g2.forum.logic.CommandHistory;
import t13g2.forum.model.Model;
import t13g2.forum.model.ModelManager;
import t13g2.forum.model.UserPrefs;
import t13g2.forum.model.person.Person;
import t13g2.forum.testutil.PersonBuilder;
import t13g2.forum.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getForumBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);
        expectedModel.commitForumBook();

        CommandTestUtil.assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getForumBook().getPersonList().get(0);
        CommandTestUtil.assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
