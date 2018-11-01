package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Filetype;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author jitwei98
/**
 * Contains integration tests (interaction with the Model) and unit tests for ExportCommand.
 */
public class ExportCommandTest {

    private static final Filetype FILETYPE_CSV = new Filetype("csv");
    private static final Filetype FILETYPE_VCF = new Filetype("vcf");

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute() {
        Person personToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS, personToExport);
        ExportCommand expectedCommand = new ExportCommand(INDEX_FIRST_PERSON, FILETYPE_CSV);

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ExportCommand standardCommand = new ExportCommand(INDEX_FIRST_PERSON, FILETYPE_CSV);

        // same value -> returns true
        ExportCommand commandWithSameArgument = new ExportCommand(INDEX_FIRST_PERSON, FILETYPE_CSV);
        assertTrue(standardCommand.equals(commandWithSameArgument));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null value -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ExportAllCommand(FILETYPE_CSV)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ExportCommand(INDEX_SECOND_PERSON, FILETYPE_CSV)));

        // different filetype -> returns false
        assertFalse(standardCommand.equals(new ExportCommand(INDEX_FIRST_PERSON, FILETYPE_VCF)));
    }
}
