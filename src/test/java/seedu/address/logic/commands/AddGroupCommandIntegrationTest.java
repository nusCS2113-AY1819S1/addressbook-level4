package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalPersons.ALICE;


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

///**
// * Contains integration tests (interaction with the Model) for {@code AddGroupCommand}.
// */
//public class AddGroupCommandIntegrationTest {
//
//    private Model model;
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Before
//    public void setUp() {
//        AddressBook ab = new AddressBook();
//        ab.addPerson(ALICE);
//        ab.createGroup(TUT_1);
//        model = new ModelManager(ab, new UserPrefs());
//    }
//
//    // TODO assertCommandSuccessTest
//    @Test
//    public void execute_addGroup_success() {
//
//        AddGroup validAddGroup = ADD_GROUP_3;
//
//        ObservableList<Group> group = FXCollections.observableArrayList();
//        group.add(TUT_1);
//        ObservableList<Person> person = FXCollections.observableArrayList();
//        person.add(ALICE);
//
//        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        validAddGroup.setGroupSet(group);
//        validAddGroup.setPersonSet(person);
//
//        expectedModel.addGroup(validAddGroup);
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(new AddGroupCommand(validAddGroup), model, commandHistory,
//                String.format(AddGroupCommand.MESSAGE_SUCCESS, validAddGroup), expectedModel);
//    }
//
//}
