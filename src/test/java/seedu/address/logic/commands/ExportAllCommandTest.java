package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Filetype;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jitwei98
/**
 * Contains integration tests (interaction with the Model) and unit tests for ExportAllCommand.
 */
public class ExportAllCommandTest {

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
        assertCommandSuccess(new ExportAllCommand(FILETYPE_CSV), model, new CommandHistory(),
                ExportAllCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final ExportAllCommand standardCommand = new ExportAllCommand(FILETYPE_CSV);

        // same value -> returns true
        ExportAllCommand commandWithSameArgument = new ExportAllCommand(FILETYPE_CSV);
        assertTrue(standardCommand.equals(commandWithSameArgument));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null value -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different filetype -> returns false
        assertFalse(standardCommand.equals(new ExportAllCommand(FILETYPE_VCF)));
    }
}
