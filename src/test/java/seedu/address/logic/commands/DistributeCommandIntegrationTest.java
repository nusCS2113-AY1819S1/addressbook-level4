package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.DistributeUtil.MESSAGE_DUPLICATE_GROUP;
import static seedu.address.model.distribute.DistributeAlgorithm.MESSAGE_INVALID_SIZE;
import static seedu.address.model.distribute.DistributeAlgorithm.MESSAGE_TO_BE_IMPLEMENTED;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.distribute.DistributeAlgorithm;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

public class DistributeCommandIntegrationTest {

    private static final String COMMAND_DISTRIBUTE_DEFAULT = "distinto 3 n/CS2113- g/0 nat/0";
    private static final String COMMAND_DISTRIBUTE_RANDOMLY_WITH_GENDER = "distinto 3 n/CS2113- g/1 nat/0";
    private static final String COMMAND_DISTRIBUTE_RANDOMLY_WITH_NATIONALITY = "distinto 3 n/CS2113- g/0 nat/1";
    private static final String COMMAND_DISTRIBUTE_RANDOMLY_WITH_GENDER_AND_NATIONALITY =
            "distinto 3 n/CS2113- g/1 nat/1";
    private static final String COMMAND_DISTRIBUTE_DUPLICATE_GROUP = "distinto 3 n/CS2113- g/0 nat/0";
    private static final String COMMAND_DISTRIBUTE_EMPTY_GROUP = "distinto 0 n/CS2113- g/0 nat/0";
    private static final String COMMAND_DISTRIBUTE_INVALID_NUM_OF_GROUP = "distinto 10 n/CS2113- g/0 nat/0";
    private static final String COMMAND_DISTRIBUTE_INVALID_GENDER_FLAG = "distinto 3 n/CS2113- g/2 nat/0";
    private static final String COMMAND_DISTRIBUTE_INVALID_NATIONALITY_FLAG = "distinto 3 n/CS2113- g/0 nat/2";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private AddressBook addressBook;
    private UserPrefs userPrefs;
    private CommandHistory commandHistory = new CommandHistory();


    @Before
    public void setUp() {
        addressBook = getTypicalAddressBook();
        userPrefs = new UserPrefs();
        model = new ModelManager(addressBook, userPrefs);
    }

    /**
     * This method contains the necessary code to Assert for IntegrationTest.
     * @param cmdInput : Input command string
     * @throws ParseException : When parsing command encounters an error.
     * @throws CommandException : When command is invalid.
     */
    private void executeCommand(String cmdInput) throws ParseException, CommandException {
        AddressBookParser addressBookParser = new AddressBookParser();
        Command command = addressBookParser.parseCommand(cmdInput);
        CommandResult commandResult = command.execute(model, commandHistory);
        assertEquals(DistributeCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_distributionDefaultCommand_success() throws ParseException, CommandException {
        executeCommand(COMMAND_DISTRIBUTE_DEFAULT);
    }

    @Test
    public void execute_distributionGenderCommand_success() throws ParseException, CommandException {
        executeCommand(COMMAND_DISTRIBUTE_RANDOMLY_WITH_GENDER);
    }

    @Test
    public void execute_distributionNationalityCommand_success() throws ParseException, CommandException {
        executeCommand(COMMAND_DISTRIBUTE_RANDOMLY_WITH_NATIONALITY);
    }

    @Test
    public void execute_distributionStrictCommand_throwsCommandException() throws ParseException, CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_TO_BE_IMPLEMENTED);
        executeCommand(COMMAND_DISTRIBUTE_RANDOMLY_WITH_GENDER_AND_NATIONALITY);
    }

    @Test
    public void execute_duplicateGroupFound_throwsCommandException() throws CommandException, ParseException {
        Group createDuplicateGroup = new GroupBuilder().withGroupName("CS2113-1").build();
        new CreateGroupCommand(createDuplicateGroup).execute(model, commandHistory);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_GROUP);
        executeCommand(COMMAND_DISTRIBUTE_DUPLICATE_GROUP);
    }

    @Test
    public void execute_zeroGroupToBeCreated_throwsParseException() throws CommandException, ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistributeCommand.MESSAGE_USAGE));
        executeCommand(COMMAND_DISTRIBUTE_EMPTY_GROUP);
    }

    @Test
    public void execute_tooManyGroupsToBeCreated_throwsCommandException() throws CommandException, ParseException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_INVALID_SIZE);
        executeCommand(COMMAND_DISTRIBUTE_INVALID_NUM_OF_GROUP);
    }

    @Test
    public void execute_invalidGenderFlag_throwsParseException() throws CommandException, ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(DistributeAlgorithm.MESSAGE_FLAG_ERROR);
        executeCommand(COMMAND_DISTRIBUTE_INVALID_GENDER_FLAG);
    }

    @Test
    public void execute_invalidNationalityFlag_throwsParseException() throws CommandException, ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(DistributeAlgorithm.MESSAGE_FLAG_ERROR);
        executeCommand(COMMAND_DISTRIBUTE_INVALID_NATIONALITY_FLAG);
    }


}
