package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import seedu.address.model.StorageController;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.testutil.GradebookBuilder;

public class GradebookDeleteCommandTest {
    GradebookManager gradebookManager = new GradebookManager();
    private static GradebookBuilder dummyGradebook = new GradebookBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }
}
