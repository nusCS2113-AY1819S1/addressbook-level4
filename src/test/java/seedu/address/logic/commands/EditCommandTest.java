package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.session.SessionManager;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private static final Person ALICEFOREDIT = new PersonBuilder().withName("Alice Pauline")
            .withNric("T2457888E").withPassword("ASd654").withPriorityLevel(0)
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@gmail.com")
            .withDepartment(ALICE.getDepartment().toString()).withPhone("85355255")
            .withTags("friends").build();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private SessionManager sessionManager;

    @Before
    public void setUp() {
        sessionManager = SessionManager.getInstance(model);
    }

    /**
     * PRE-CONDITION: NRIC of the person to edit MUST be the same.
     */
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Person editedPerson = ALICEFOREDIT;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        sessionManager.destroy();
        sessionManager = SessionManager.getInstance(model);
        sessionManager.loginToSession(ALICE.getNric(), ALICE.getPassword());

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        Person personInList = new PersonBuilder(lastPerson).build();
        Person editedPerson = new PersonBuilder(personInList).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();

        sessionManager.destroy();
        sessionManager = SessionManager.getInstance(model);
        sessionManager.loginToSession(lastPerson.getNric(), lastPerson.getPassword());

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws CommandException {
        EditCommand editCommand = new EditCommand(new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        sessionManager.loginToSession(editedPerson.getNric(), editedPerson.getPassword());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notLoggedIn_failure() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(descriptor);

        assertCommandFailure(editCommand, model, commandHistory, SessionManager.NOT_LOGGED_IN);
    }

    /**
     * This test attempts to edit all unmodifiable parameters and does not include any modifiable fields.
     * This test should succeed, but the person SHOULD NOT be edited.
     */
    @Test
    public void execute_editUneditableFields_success() throws CommandException {
        Person editedPerson = new PersonBuilder(ALICE).withNric("G8888888E").withPassword("qwerty33")
                .withDepartment("Test Management").withPriorityLevel(0).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(ALICE, ALICE);
        expectedModel.commitAddressBook();

        sessionManager.destroy();
        sessionManager = SessionManager.getInstance(model);
        sessionManager.loginToSession(ALICE.getNric(), ALICE.getPassword());

        assertCommandSuccess(new EditCommand(descriptor), model, commandHistory,
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, ALICE), expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB)));
    }

    @After
    public void tearDown() {
        sessionManager.logOutSession();
        sessionManager.destroy();
    }
}
