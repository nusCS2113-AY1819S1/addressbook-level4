package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.DistributeUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.distribute.Distribute;
import seedu.address.model.group.Group;
import seedu.address.testutil.DistributeBuilder;
import seedu.address.testutil.GroupBuilder;

public class DistributeCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDistribute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DistributeCommand(null);
    }

    @Test
    public void execute_distributeAcceptedByModel_distributeSuccessful() throws ParseException, CommandException {
        AddressBook stubAddressBook = getTypicalAddressBook();
        UserPrefs stubUserPrefs = new UserPrefs();
        Model modelStub = new ModelManager(stubAddressBook, stubUserPrefs);
        Distribute validDistributeCommand = new DistributeBuilder().build();
        CommandResult commandResult = new DistributeCommand(validDistributeCommand).execute(modelStub, commandHistory);
        assertEquals(String.format(DistributeCommand.MESSAGE_SUCCESS, validDistributeCommand),
                commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateGroupFound_throwsCommandException() throws CommandException, ParseException {
        AddressBook stubAddressBook = getTypicalAddressBook();
        Group stubGroup = new GroupBuilder().withGroupName("CS2113-T13-1").build();
        stubAddressBook.createGroup(stubGroup);
        UserPrefs stubUserPrefs = new UserPrefs();
        Model modelStub = new ModelManager(stubAddressBook, stubUserPrefs);
        Distribute validDistributeCommand = new DistributeBuilder().build();
        thrown.expect(CommandException.class);
        thrown.expectMessage(DistributeUtil.MESSAGE_DUPLICATE_GROUP);
        new DistributeCommand(validDistributeCommand).execute(modelStub, commandHistory);
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
