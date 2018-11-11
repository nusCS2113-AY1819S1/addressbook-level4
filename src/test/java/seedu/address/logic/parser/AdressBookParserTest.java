package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class AdressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final AddressBookParser parser = new AddressBookParser();
    private Command command;


    @Test
    public void parseCommand_clear() throws Exception {
        parser.parseCommand(ClearCommand.COMMAND_WORD);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ClearCommand);
        parser.parseCommand(ClearCommand.COMMAND_WORD + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ClearCommand);
    }

    @Test
    public void parseCommandAlias_clear() throws Exception {
        parser.parseCommand(ClearCommand.COMMAND_WORD_ALIAS);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ClearCommand);
        parser.parseCommand(ClearCommand.COMMAND_WORD_ALIAS + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        parser.parseCommand(ExitCommand.COMMAND_WORD);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ExitCommand);
        parser.parseCommand(ExitCommand.COMMAND_WORD + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void parseCommandAlias_exit() throws Exception {
        parser.parseCommand(ExitCommand.COMMAND_WORD_ALIAS);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ExitCommand);
        parser.parseCommand(ExitCommand.COMMAND_WORD_ALIAS + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand command = (FindCommand) parser.parseCommandArguments();
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommandAlias_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        parser.parseCommand(
                FindCommand.COMMAND_WORD_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand command = (FindCommand) parser.parseCommandArguments();
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        parser.parseCommand(HelpCommand.COMMAND_WORD);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HelpCommand);
        parser.parseCommand(HelpCommand.COMMAND_WORD + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void parseCommandAlias_help() throws Exception {
        parser.parseCommand(HelpCommand.COMMAND_WORD_ALIAS);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HelpCommand);
        parser.parseCommand(HelpCommand.COMMAND_WORD_ALIAS + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        parser.parseCommand(HistoryCommand.COMMAND_WORD);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HistoryCommand);
        parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            command = parser.parseCommandArguments();
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommandAlias_history() throws Exception {
        parser.parseCommand(HistoryCommand.COMMAND_WORD_ALIAS);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HistoryCommand);
        parser.parseCommand(HistoryCommand.COMMAND_WORD_ALIAS + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        parser.parseCommand(ListCommand.COMMAND_WORD);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ListCommand);
        parser.parseCommand(ListCommand.COMMAND_WORD + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parseCommandAlias_list() throws Exception {
        parser.parseCommand(ListCommand.COMMAND_WORD_ALIAS);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ListCommand);
        parser.parseCommand(ListCommand.COMMAND_WORD_ALIAS + " 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        SelectCommand command = (SelectCommand) parser.parseCommandArguments();
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAlias_select() throws Exception {
        parser.parseCommand(
                SelectCommand.COMMAND_WORD_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        SelectCommand command = (SelectCommand) parser.parseCommandArguments();
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        parser.parseCommand(RedoCommand.COMMAND_WORD);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof RedoCommand);
        parser.parseCommand("redo 1");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof RedoCommand);
    }


    @Test
    public void parseCommandAlias_redoCommandWord_returnsRedoCommand() throws Exception {
        parser.parseCommand(RedoCommand.COMMAND_WORD_ALIAS);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof RedoCommand);
        parser.parseCommand("redo 1");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof RedoCommand);
    }


    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        parser.parseCommand(UndoCommand.COMMAND_WORD);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof UndoCommand);
        parser.parseCommand("undo 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof UndoCommand);
    }

    @Test
    public void parseCommandAlias_undoCommandWord_returnsUndoCommand() throws Exception {
        parser.parseCommand(UndoCommand.COMMAND_WORD_ALIAS);
        command = parser.parseCommandArguments();
        assertTrue(command instanceof UndoCommand);
        parser.parseCommand("undo 3");
        command = parser.parseCommandArguments();
        assertTrue(command instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

}
