package seedu.divelog.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.divelog.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.divelog.testutil.TypicalIndexes.INDEX_FIRST_DIVE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.divelog.logic.commands.AddCommand;
import seedu.divelog.logic.commands.ClearCommand;
import seedu.divelog.logic.commands.DeleteCommand;
import seedu.divelog.logic.commands.EditCommand;
import seedu.divelog.logic.commands.ExitCommand;
import seedu.divelog.logic.commands.FindCommand;
import seedu.divelog.logic.commands.HelpCommand;
import seedu.divelog.logic.commands.HistoryCommand;
import seedu.divelog.logic.commands.ListCommand;
import seedu.divelog.logic.commands.RedoCommand;
import seedu.divelog.logic.commands.SelectCommand;
import seedu.divelog.logic.commands.UndoCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.LocationContainsKeywordPredicate;
import seedu.divelog.testutil.DiveSessionBuilder;
import seedu.divelog.testutil.DiveUtil;
import seedu.divelog.testutil.EditDiveDescriptorBuilder;

public class DiveLogParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DiveLogParser parser = new DiveLogParser();

    @Test
    public void parseCommand_add() throws Exception {
        DiveSession diveSession = new DiveSessionBuilder().withPressureGroupAtEnd("l").build();
        AddCommand command = (AddCommand) parser.parseCommand(DiveUtil.getAddCommand(diveSession));
        assertEquals(new AddCommand(diveSession), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_DIVE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_DIVE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        DiveSession diveSession = new DiveSessionBuilder().build();
        EditCommand.EditDiveDescriptor descriptor = new EditDiveDescriptorBuilder(diveSession).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_DIVE.getOneBased() + " " + DiveUtil.getEditDiveDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_DIVE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new LocationContainsKeywordPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_DIVE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_DIVE), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
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
