//package seedu.address.logic.commands;
//
//import org.junit.jupiter.api.Test;
//import seedu.address.logic.CommandHistory;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Group;
//import seedu.address.model.person.Name;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS1231;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2040C;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//public class GroupCommandTest {
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//
//    @Test
//    public void execute() {
//        final Name name = new Name("test");
//        assertCommandFailure(new GroupCommand(
//                new Group(name)), model, new CommandHistory(), String.format(COMMAND_ARGS, name));
//    }
//
//    @Test
//    public void equals(){
//        final GroupCommand normalCommand = new GroupCommand(Group(new Name(VALID_GROUP_NAME_CS1231)));
//
//        // same values means returns true
//        GroupCommand normalCommand2 = new GroupCommand(VALID_GROUP_NAME_CS1231);
//        assertTrue(normalCommand.equals(normalCommand2));
//        // same object means returns true
//        assertTrue(normalCommand.equals(normalCommand));
//        // null means returns false
//        // assertFalse(normalCommand.equals(null));
//        // different types means returns false
//        assertFalse(normalCommand.equals(new ClearCommand()));
//        // different group name means returns false
//        assertFalse(normalCommand.equals(new GroupCommand(VALID_GROUP_NAME_CS2040C)));
//    }
//}
