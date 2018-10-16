package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup3;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalGroups.getTut1;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code AddGroupCommand}.
 */
public class AddGroupCommandIntegrationTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        AddressBook ab = new AddressBook();
        ab.addPerson(ALICE);
        ab.createGroup(getTut1());
        model = new ModelManager(ab, new UserPrefs());

        AddressBook abCopy = new AddressBook();
        abCopy.addPerson(ALICE);
        abCopy.createGroup(getTut1());
        expectedModel = new ModelManager(abCopy, new UserPrefs());
    }

    // TODO assertCommandSuccessTest
    @Test
    public void execute_addGroup_success() {

        AddGroup validAddGroup = getAddGroup3();
        AddGroup validAddGroupCopy = getAddGroup3();

        List<Person> person = expectedModel.getFilteredPersonList();
        List<Group> group = expectedModel.getFilteredGroupList();

        validAddGroup.setGroupSet(group);
        validAddGroup.setPersonSet(person);

        expectedModel.addGroup(validAddGroup);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddGroupCommand(validAddGroupCopy), model, commandHistory,
                String.format(AddGroupCommand.MESSAGE_SUCCESS, validAddGroup), expectedModel);
    }

}
