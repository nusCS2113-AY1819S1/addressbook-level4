package seedu.planner.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

<<<<<<< HEAD:src/test/java/seedu/address/logic/parser/AddressBookParserTest.java
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.NameContainsKeywordsPredicate;
import seedu.address.model.record.Record;
import seedu.address.testutil.EditRecordDescriptorBuilder;
import seedu.address.testutil.RecordBuilder;
import seedu.address.testutil.RecordUtil;
import seedu.address.testutil.TypicalDates;

public class AddressBookParserTest {
=======
import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.HistoryCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SelectCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.NameContainsKeywordsPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.EditRecordDescriptorBuilder;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.RecordUtil;

public class FinancialPlannerParserTest {
>>>>>>> 936a266304811392cda80acfbf3d1820aac87fed:src/test/java/seedu/planner/logic/parser/FinancialPlannerParserTest.java
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinancialPlannerParser parser = new FinancialPlannerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Record record = new RecordBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(RecordUtil.getAddCommand(record));
        assertEquals(new AddCommand(record), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_deleteByDateEntry() throws Exception {
        DeleteCommandByDateEntry command = (DeleteCommandByDateEntry) parser.parseCommand(
                DeleteCommandByDateEntry.COMMAND_WORD + " " + TypicalDates.DATE_FIRST_INDEX_DATE.value);
        assertEquals(new DeleteCommandByDateEntry(TypicalDates.DATE_FIRST_INDEX_DATE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Record record = new RecordBuilder().build();
        EditCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(record).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RECORD.getOneBased() + " " + RecordUtil.getEditRecordDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_RECORD, descriptor), command);
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
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_RECORD), command);
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
