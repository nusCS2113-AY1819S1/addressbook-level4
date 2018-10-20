package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Email;

public class EmailLoginCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_login_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        String expectedMessage = String.format(EmailLoginCommand.MESSAGE_SUCCESS);
        Email validEmail = new Email("stubEmail@stubEmail.com");
        String password = "stubPassword123";

        assertCommandSuccess(new EmailLoginCommand(validEmail, password), model, commandHistory, expectedMessage,
                expectedModel);
    }

}
