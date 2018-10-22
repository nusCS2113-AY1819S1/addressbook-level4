package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.distribute.Distribute;
import seedu.address.model.group.GroupName;

public class DistributeCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        //TODO create a builder for this.
        Distribute first = new Distribute(3, new GroupName("First-Group"), true, false);
        Distribute second = new Distribute(3, new GroupName("First-Group"), true, false);
        Distribute differentIndex = new Distribute(4, new GroupName("First-Group"), true, false);
        Distribute differentGroupName = new Distribute(4, new GroupName("Different-Group"), true, false);
        Distribute differentGenderFlag = new Distribute(4, new GroupName("First-Group"), false, false);
        Distribute differentNationalityFlag = new Distribute(4, new GroupName("First-Group"), true, true);
        Distribute allDifferent = new Distribute(2, new GroupName("CS2113-T13-"), false, true);

        DistributeCommand distributeFirstCommand = new DistributeCommand(first);
        DistributeCommand distributeSecondCommand = new DistributeCommand(second);
        DistributeCommand differentIndexCommand = new DistributeCommand(differentIndex);
        DistributeCommand differentGroupNameCommand = new DistributeCommand(differentGroupName);
        DistributeCommand differentGenderFlagCommand = new DistributeCommand(differentGenderFlag);
        DistributeCommand differentNationalityFlagCommand = new DistributeCommand(differentNationalityFlag);
        DistributeCommand allDifferentCommand = new DistributeCommand(allDifferent);


        // same object -> returns true
        assertTrue(distributeFirstCommand.equals(distributeSecondCommand));

        // same values -> returns true
        DistributeCommand forthCommand = new DistributeCommand(new Distribute(3,
                new GroupName("First-Group"), true, false));
        assertTrue(forthCommand.equals(distributeFirstCommand));


        // different Index
        assertFalse(distributeFirstCommand.equals(differentIndexCommand));

        // different GroupName
        assertFalse(distributeFirstCommand.equals(differentGroupNameCommand));

        // different GenderFlag
        assertFalse(distributeFirstCommand.equals(differentGenderFlagCommand));

        // different NationalityFlag
        assertFalse(distributeFirstCommand.equals(differentNationalityFlagCommand));

        // null -> returns false
        assertFalse(distributeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(distributeFirstCommand.equals(allDifferentCommand));
    }

}
