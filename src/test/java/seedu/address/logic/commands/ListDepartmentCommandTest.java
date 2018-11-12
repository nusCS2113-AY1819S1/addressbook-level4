package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author Woonhian
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDepartmentCommand.
 */
public class ListDepartmentCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_departmentListIsFiltered_showsDepartments() {

        List<Person> lastShownList = model.getFilteredPersonList();
        List<String> departments = new ArrayList<>();

        for (Person department : lastShownList) {
            departments.add(department.getDepartment().toString());
        }

        Set<String> hs = new HashSet<>(departments);
        departments.clear();
        departments.addAll(hs);

        Collections.sort(departments);

        String expectedMessage = String.format(ListDepartmentCommand.MESSAGE_SUCCESS,
                String.join(", ", departments));

        assertCommandSuccess(new ListDepartmentCommand(), model, commandHistory,
                expectedMessage, expectedModel);
    }
}
