package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.script.CommandType;
import seedu.address.model.script.TextFile;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains tests for ScriptCommand.
 */
public class ScriptCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    private Person novin = new PersonBuilder().withName("Novin Tong Yong Kang").withGender("M").withNationality("SG")
            .withAddress("Pasir Ris street 99,#02-25").withEmail("novin@example.com")
            .withGrade("69").withPhone("99999999").withTags("friends").build();

    private Person weiQuan = new PersonBuilder().withName("Tsu Wei Quan").withGender("F").withNationality("SG")
            .withAddress("Tampines street 82,#02-33").withEmail("TsuTheBoss@gmail.com")
            .withGrade("99").withPhone("66666666").withTags("owesMoney").build();

    private final String testFilesLocation = "/src/test/data/ScriptCommandTest/";
    private final String validAddTextFile = "ValidAddScriptCommand";
    private final String missingTextFile = "MissingTextFile";
    private final String invalidTextFile = "InvalidAddCommand";
    private final String invalidLinesNumbers = "1,2";
    private final String addCommand = AddCommand.COMMAND_WORD;
    private final String deleteCommand = DeleteCommand.COMMAND_WORD;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    }

    @Test
    public void execute_scriptAddCommand_successful() {
        String validFileName = validAddTextFile;
        String expectedMessage = String.format(ScriptCommand.MESSAGE_SUCCESS,
                validAddTextFile + ScriptCommand.TEXT_EXTENSION);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(novin);
        expectedModel.commitAddressBook();

        expectedModel.addPerson(weiQuan);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ScriptCommand(new TextFile(validFileName), new CommandType(addCommand)),
                model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fileMissing_throwsIoException() {
        String expectedMessage = String.format(ScriptCommand.MESSAGE_FILE_MISSING,
                missingTextFile + ScriptCommand.TEXT_EXTENSION);
        ScriptCommand scriptCommand = new ScriptCommand(new TextFile(missingTextFile), new CommandType(addCommand));
        CommandResult commandResult = scriptCommand.execute(model, commandHistory);
        assertEquals(commandResult.feedbackToUser, expectedMessage);
    }

    @Test
    public void execute_wrongCommand_throwsParseException() {
        String expectedMessage = String.format(ScriptCommand.MESSAGE_ADD_ERROR,
                invalidLinesNumbers, validAddTextFile + ScriptCommand.TEXT_EXTENSION);

        ScriptCommand scriptCommand = new ScriptCommand(new TextFile(validAddTextFile), new CommandType(deleteCommand));

        CommandResult commandResult = scriptCommand.execute(model, commandHistory);
        assertEquals(commandResult.feedbackToUser, expectedMessage);
    }

    @Test
    public void equals() {
        ScriptCommand validScriptCommand = new ScriptCommand(
                new TextFile(validAddTextFile), new CommandType(addCommand));
        ScriptCommand invalidScriptCommmamd = new ScriptCommand(
                new TextFile(validAddTextFile), new CommandType(deleteCommand));

        // same object -> returns true
        assertTrue(validScriptCommand.equals(validScriptCommand));

        // different types -> returns false
        assertFalse(validAddTextFile.equals(1));

        // null -> returns false
        assertFalse(validAddTextFile.equals(null));

        // different person -> returns false
        assertFalse(validAddTextFile.equals(invalidScriptCommmamd));
    }
}
