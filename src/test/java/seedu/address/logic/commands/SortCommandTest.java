package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getEmptyAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//Reused from https://github.com/CS2103JAN2018-F14-B1/main/pull/57/files with minor modifications
//@@author Woonhian
public class SortCommandTest {

    @Rule
    public ExpectedException error = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model emptyModel = new ModelManager(getEmptyAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void noEmployees() {
        SortCommand command = prepareCommand("name", "asc");
        assertCommandFailure(command, emptyModel, commandHistory, SortCommand.MESSAGE_EMPTY_BOOK);
    }

    @Test
    public void emptySortField_throwsNullPointerEx() {
        error.expect(NullPointerException.class);
        new SortCommand("name", null);
    }

    @Test
    public void emptySortOrder_throwsNullPointerEx() {
        error.expect(NullPointerException.class);
        new SortCommand(null, "asc");
    }

    @Test
    public void execute_wrongField_throwsAssertionError() {
        Assertions.assertThrows(AssertionError.class, () -> {
            new SortCommand("invalid", "asc").execute(model, commandHistory);
        }, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void execute_wrongOrder_throwsAssertionError() {
        Assertions.assertThrows(AssertionError.class, () -> {
            new SortCommand("name", "invalid").execute(model, commandHistory);
        }, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void sortByName_success() {
        SortCommand command = prepareCommand("name", "asc");
        String expected = String.format(MESSAGE_SUCCESS, "name", "asc");
        assertCommandSuccess(command, model, commandHistory, expected, model);
    }

    @Test
    public void sortByNameDesc_success() {
        SortCommand command = prepareCommand("name", "desc");
        String expected = String.format(MESSAGE_SUCCESS, "name", "desc");
        assertCommandSuccess(command, model, commandHistory, expected, model);
    }

    @Test
    public void sortByDepartment_success() {
        SortCommand command = prepareCommand("department", "asc");
        String expected = String.format(MESSAGE_SUCCESS, "department", "asc");
        assertCommandSuccess(command, model, commandHistory, expected, model);
    }

    @Test
    public void sortByDepartmentDesc_success() {
        SortCommand command = prepareCommand("department", "desc");
        String expected = String.format(MESSAGE_SUCCESS, "department", "desc");
        assertCommandSuccess(command, model, commandHistory, expected, model);
    }

    /**
     * Returns a {@code sortCommand} with the parameters {@code field and @code order}.
     */
    private SortCommand prepareCommand(String field, String order) {
        SortCommand sortCommand = new SortCommand(field, order);
        return sortCommand;
    }
}
