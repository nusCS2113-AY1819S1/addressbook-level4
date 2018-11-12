package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;
import systemtests.SessionHelper;

public class ResetCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();

    @Test
    void execute_notOfThisPriorityLevel_throwsCommandException() {
        SessionHelper.forceLoginWithPriorityLevelOf(PriorityLevelEnum.ADMINISTRATOR.getPriorityLevelCode());
        final CommandException msg = Assertions.assertThrows(CommandException.class, () -> {
            new ResetCommand().execute(model, commandHistory);
        });
        Assertions.assertEquals(String.format(PriorityLevel.NOTOF_CERTAIN_PRIORITYLEVEL, PriorityLevelEnum.IT_UNIT),
            msg.getMessage());
    }

    @Test
    void execute_notLoggedIn_throwsCommandException() {
        Assertions.assertThrows(CommandException.class, () -> {
            new ResetCommand().execute(model, commandHistory);
        }, SessionManager.NOT_LOGGED_IN);
    }

    @Test
    void execute_sufficientPriorityLevel_success() {
        SessionHelper.forceLoginWithPriorityLevelOf(PriorityLevelEnum.IT_UNIT.getPriorityLevelCode());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(new ResetCommand(), model, commandHistory, ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                expectedModel);
    }

    @AfterEach
    void tearDown() {
        SessionHelper.logoutOfSession();
    }
}
