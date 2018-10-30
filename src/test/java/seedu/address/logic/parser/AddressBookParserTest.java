package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearExpenseCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteExpenseCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditExpenseCommand;
import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExpenseTrendCommand;
import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MonthlyExpenseCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RedoExpenseCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.ExpenseUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
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
    public void parseCommand_findaddress() throws Exception {
        List<String> keywords = Arrays.asList("College", "Sentosa", "Singapore");
        FindAddressCommand command = (FindAddressCommand) parser.parseCommand(
                FindAddressCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindAddressCommand(new AddressContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findname() throws Exception {
        List<String> keywords = Arrays.asList("Mario", "Gara", "Momo");
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindNameCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findphone() throws Exception {
        List<String> keywords = Arrays.asList("98835761", "13371337", "1234567");
        FindPhoneCommand command = (FindPhoneCommand) parser.parseCommand(
                FindPhoneCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPhoneCommand(new PhoneContainsKeywordsPredicate(keywords)), command);
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
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
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

    //@@author QzSG
    @Test
    public void parseCommand_backup() throws Exception {
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD) instanceof BackupCommand);
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD + " github FAKE_TOKEN") instanceof BackupCommand);
    }
    //@@author
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


    //========== Expense ====================================================

    @Test
    public void parseCommand_addExpense() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        assertTrue(parser.parseCommand(ExpenseUtil.getAddExpenseCommand(expense)) instanceof AddExpenseCommand);
    }

    @Test
    public void parseCommand_clearExpense() throws Exception {
        assertTrue(parser.parseCommand(ClearExpenseCommand.COMMAND_WORD) instanceof ClearExpenseCommand);
        assertTrue(parser.parseCommand(ClearExpenseCommand.COMMAND_WORD + " 3") instanceof ClearExpenseCommand);
    }

    @Test
    public void parseCommand_deleteExpense() throws Exception {
        DeleteExpenseCommand command = (DeleteExpenseCommand) parser.parseCommand(
                DeleteExpenseCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteExpenseCommand(INDEX_FIRST_EXPENSE), command);
    }

    @Test
    public void parseCommand_editExpense() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(expense).build();
        EditExpenseCommand command = (EditExpenseCommand) parser.parseCommand(EditExpenseCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + ExpenseUtil.getEditExpenseDescriptorDetails(descriptor));
        assertEquals(new EditExpenseCommand(INDEX_FIRST_EXPENSE, descriptor), command);
    }

    @Test
    public void parseCommand_expenseTrend() throws Exception {
        assertTrue(parser.parseCommand(ExpenseTrendCommand.COMMAND_WORD) instanceof ExpenseTrendCommand);
        assertTrue(parser.parseCommand(ExpenseTrendCommand.COMMAND_WORD + " 3") instanceof ExpenseTrendCommand);
    }

    @Test
    public void parseCommand_monthlyExpense() throws Exception {
        assertTrue(parser.parseCommand(MonthlyExpenseCommand.COMMAND_WORD + " 10/2018")
                instanceof MonthlyExpenseCommand);
    }

    @Test
    public void parseCommand_redoExpenseCommandWord_returnsRedoExpenseCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoExpenseCommand.COMMAND_WORD) instanceof RedoExpenseCommand);
        assertTrue(parser.parseCommand("redoExpense 1") instanceof RedoExpenseCommand);
    }

    @Test
    public void parseCommand_undoExpenseCommandWord_returnsUndoExpenseCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoExpenseCommand.COMMAND_WORD) instanceof UndoExpenseCommand);
        assertTrue(parser.parseCommand("undoExpense 3") instanceof UndoExpenseCommand);
    }
}
