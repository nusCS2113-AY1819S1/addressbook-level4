package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.GradebookEditCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.testutil.GradebookBuilder;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookEditCommand.MESSAGE_EDIT_GRADEBOOK_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

public class GradebookEditCommandTest {
    private static GradebookManager gradebookManager = new GradebookManager();
    private GradebookEditCommandParser parser = new GradebookEditCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }

    @Test
    public void execute_gradebookEditWithNewComponentName_success() throws ParseException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String newGradebookComponentName = "Test";

        GradebookEditCommand gradebookEditCommand = parser.parse(" "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_ITEM_EDIT
                + newGradebookComponentName);

        assertNotNull(gradebookEditCommand);
    }
}
