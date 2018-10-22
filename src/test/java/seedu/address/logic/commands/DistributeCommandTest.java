package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.distribute.Distribute;
import seedu.address.testutil.DistributeBuilder;

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
    public void equals() throws ParseException {
        Distribute distributeIntoThree = new DistributeBuilder().build();
        Distribute differentIndexDistribution = new DistributeBuilder().setIndex("4").build();
        Distribute differentGroupNameDistribution = new DistributeBuilder().setGroupName("Different-Group").build();
        Distribute differentGenderFlagDistribution = new DistributeBuilder().setGenderFlag("true").build();
        Distribute differentNationalityFlagDistribution = new DistributeBuilder().setNationalityFlag("true").build();
        Distribute allDifferent = new DistributeBuilder().setIndex("6").setGroupName("New-Group")
                .setGenderFlag("true").setNationalityFlag("true").build();

        DistributeCommand distributeFirstCommand = new DistributeCommand(distributeIntoThree);
        DistributeCommand differentIndexCommand = new DistributeCommand(differentIndexDistribution);
        DistributeCommand differentGroupNameCommand = new DistributeCommand(differentGroupNameDistribution);
        DistributeCommand differentGenderFlagCommand = new DistributeCommand(differentGenderFlagDistribution);
        DistributeCommand differentNationalityFlagCommand = new DistributeCommand(differentNationalityFlagDistribution);
        DistributeCommand allDifferentCommand = new DistributeCommand(allDifferent);


        // same object -> returns true
        assertTrue(distributeFirstCommand.equals(distributeFirstCommand));

        // same values -> returns true
        DistributeCommand differentIndexCommandCopy = new DistributeCommand(differentIndexDistribution);
        assertTrue(differentIndexCommandCopy.equals(differentIndexCommand));


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
