package systemtests;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static com.t13g2.forum.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static com.t13g2.forum.logic.commands.CommandTestUtil.INVALID_MODULE_TITLE_DESC;
import static com.t13g2.forum.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static com.t13g2.forum.logic.commands.CommandTestUtil.MODULE_CODE_DESC_GET1020;
import static com.t13g2.forum.logic.commands.CommandTestUtil.MODULE_CODE_DESC_MA1508E;
import static com.t13g2.forum.logic.commands.CommandTestUtil.MODULE_TITLE_DESC_CS1231;
import static com.t13g2.forum.logic.commands.CommandTestUtil.MODULE_TITLE_DESC_MA1508E;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_MODULE_CODE_GET1020;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_MODULE_TITLE_MA1508E;
import static com.t13g2.forum.testutil.TypicalModules.CS1231;
import static com.t13g2.forum.testutil.TypicalModules.MA1508E;

import org.junit.Test;

import com.t13g2.forum.logic.commands.CreateModuleCommand;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.testutil.ModuleBuilder;
import com.t13g2.forum.testutil.ModuleUtil;
import com.t13g2.forum.testutil.UserBuilder;

//@@author xllx1
public class CreateModuleCommandSystemTest extends ForumBookSystemTest {

    @Test
    public void add() {
        //set the current logged in user as an admin.
        User validAdmin = new UserBuilder().build();
        Context.getInstance().setCurrentUser(validAdmin);

        /* Case: add a new module to forum book, command with leading spaces and trailing spaces
         * -> added
         */
        /* Case: missing code -> rejected */
        String command = CreateModuleCommand.COMMAND_WORD + MODULE_TITLE_DESC_MA1508E;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateModuleCommand.MESSAGE_USAGE));

        Module toAdd = MA1508E;
        command = "   " + CreateModuleCommand.COMMAND_WORD + "  " + MODULE_CODE_DESC_MA1508E + "  "
            + MODULE_TITLE_DESC_MA1508E + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a module with all fields same as another module in the forum book except code -> added */
        toAdd = new ModuleBuilder(MA1508E).withCode(VALID_MODULE_CODE_GET1020).build();
        command = CreateModuleCommand.COMMAND_WORD + MODULE_CODE_DESC_GET1020 + MODULE_TITLE_DESC_MA1508E;
        assertCommandSuccess(command, toAdd);

        /* Case: add a module with parameters in random order -> added */
        toAdd = CS1231;
        command = CreateModuleCommand.COMMAND_WORD + MODULE_TITLE_DESC_CS1231 + MODULE_CODE_DESC_CS1231;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate module -> rejected */
        command = ModuleUtil.getCreateCommand(CS1231);
        assertCommandFailure(command, String.format(CreateModuleCommand.MESSAGE_DUPLICATE_MODULE,
            CS1231.getModuleCode()));

        /* Case: add a duplicate module except with different title -> rejected */
        toAdd = new ModuleBuilder(CS1231).withTitle(VALID_MODULE_TITLE_MA1508E).build();
        command = ModuleUtil.getCreateCommand(toAdd);
        assertCommandFailure(command, String.format(CreateModuleCommand.MESSAGE_DUPLICATE_MODULE,
            CS1231.getModuleCode()));

        /* Case: missing title -> rejected */
        command = CreateModuleCommand.COMMAND_WORD + MODULE_CODE_DESC_MA1508E;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateModuleCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "creates " + ModuleUtil.getModuleDetails(toAdd);
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid cpde -> rejected */
        command = CreateModuleCommand.COMMAND_WORD + INVALID_MODULE_CODE_DESC + MODULE_TITLE_DESC_MA1508E;
        assertCommandFailure(command, Module.MESSAGE_MODULE_CODE_CONSTRAINTS);

        /* Case: invalid title -> rejected */
        command = CreateModuleCommand.COMMAND_WORD + MODULE_CODE_DESC_MA1508E + INVALID_MODULE_TITLE_DESC;
        assertCommandFailure(command, Module.MESSAGE_MODULE_TITLE_CONSTRAINTS);
    }

    /**
     * Executes the {@code CreateModuleCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code CreateModuleCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ForumBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Module toAdd) {
        assertCommandSuccess(ModuleUtil.getCreateCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see CreateModuleCommandSystemTest#assertCommandSuccess(Module)
     */
    private void assertCommandSuccess(String command, Module toAdd) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(CreateModuleCommand.MESSAGE_SUCCESS, toAdd.getModuleCode());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see CreateModuleCommandSystemTest#assertCommandSuccess(String, Module)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ForumBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
