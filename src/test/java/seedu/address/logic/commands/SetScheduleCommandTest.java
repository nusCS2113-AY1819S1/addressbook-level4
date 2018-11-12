package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import systemtests.SessionHelper;

public class SetScheduleCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private EditPersonDescriptor editPersonDescriptor;

    @Before
    public void setUp() {
        SessionHelper.forceLoginWithPriorityLevelOf(PriorityLevelEnum.ADMINISTRATOR.getPriorityLevelCode());
        expectedModel = model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        editPersonDescriptor = new EditPersonDescriptor();
    }

    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetScheduleCommand(null, null);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetScheduleCommand(INDEX_FIRST_PERSON, null);
    }

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        thrown.expect(NullPointerException.class);
        new SetScheduleCommand(null, editPersonDescriptor);
    }

    @Test
    public void execute_emptyModel_failure() {
        // Empty Address Book
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandFailure(new SetScheduleCommand(INDEX_FIRST_PERSON, editPersonDescriptor), emptyModel,
                commandHistory, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index index = Index.fromOneBased(8);
        // Index 8 (Person does not exist) in 7 people address book -> failure
        assertCommandFailure(new SetScheduleCommand(index, editPersonDescriptor), model,
                commandHistory, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_correctValues_success() {
        assertCommandSuccess(new SetScheduleCommand(INDEX_FIRST_PERSON, editPersonDescriptor), model,
                commandHistory, SetScheduleCommand.MESSAGE_SCHEDULE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final SetScheduleCommand standardCommand = new SetScheduleCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values
        final EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        SetScheduleCommand commandWithSameValues = new SetScheduleCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertNotEquals(standardCommand, commandWithSameValues);

        // same object
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different index -> returns false
        assertNotEquals(standardCommand, new SetScheduleCommand(INDEX_SECOND_PERSON, DESC_AMY));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new SetScheduleCommand(INDEX_FIRST_PERSON, DESC_BOB));
    }

    @After
    public void tearDown() {
        SessionHelper.logoutOfSession();
    }
}
