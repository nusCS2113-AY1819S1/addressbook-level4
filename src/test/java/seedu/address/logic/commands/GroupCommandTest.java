package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GroupCommand.SECTION_UNDER_CONSTRUCTION;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class GroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() {
        assertCommandFailure(new GroupCommand(), model, new CommandHistory(), SECTION_UNDER_CONSTRUCTION);
    }
}
