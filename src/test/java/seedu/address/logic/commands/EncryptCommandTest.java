package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EncryptCommand.SHOWING_ENCRYPTION_TOGGLED;
import static seedu.address.logic.commands.EncryptCommand.SHOWING_ENCRYPTION_UNTOGGLED;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class EncryptCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeEncryption_success() {
        assertCommandSuccess(new EncryptCommand(), model, commandHistory, SHOWING_ENCRYPTION_TOGGLED, expectedModel);
        assertTrue(model.getUserPrefs().getEncryption());
        assertCommandSuccess(new EncryptCommand(), model, commandHistory, SHOWING_ENCRYPTION_UNTOGGLED, expectedModel);
        assertFalse(model.getUserPrefs().getEncryption());
    }
}
