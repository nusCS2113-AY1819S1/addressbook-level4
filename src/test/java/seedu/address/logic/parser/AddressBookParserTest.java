package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITYLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.CheckLoginStatusCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLeaveCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterDepartmentCommand;
import seedu.address.logic.commands.FilterLeaveCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListDepartmentCommand;
import seedu.address.logic.commands.ListLeaveCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.ResetCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetDepartmentCommand;
import seedu.address.logic.commands.SetPriorityLevelCommand;
import seedu.address.logic.commands.SetScheduleCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.NricContainsKeywordsPredicate;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.Mode;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().withoutSchedule().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteLeave() throws Exception {
        DeleteLeaveCommand command = (DeleteLeaveCommand) parser.parseCommand(
                DeleteLeaveCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteLeaveCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_reset() throws Exception {
        assertTrue(parser.parseCommand(ResetCommand.COMMAND_WORD) instanceof ResetCommand);
        assertTrue(parser.parseCommand(ResetCommand.COMMAND_WORD + " 3") instanceof ResetCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_filterLeave() throws Exception {
        List<String> keywords = Arrays.asList("S1111111E");
        FilterLeaveCommand command = (FilterLeaveCommand) parser.parseCommand(
                FilterLeaveCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterLeaveCommand(new NricContainsKeywordsPredicate(keywords)), command);
    }
    @Test
    public void parseCommand_filterdepartment() throws Exception {
        List<String> keywords = Arrays.asList("junior", "senior");
        FilterDepartmentCommand command = (FilterDepartmentCommand) parser.parseCommand(
                FilterDepartmentCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FilterDepartmentCommand(new DepartmentContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_listDepartment() throws Exception {
        assertTrue(parser.parseCommand(ListDepartmentCommand.COMMAND_WORD) instanceof ListDepartmentCommand);
        assertTrue(parser.parseCommand(ListDepartmentCommand.COMMAND_WORD + " 3")
                instanceof ListDepartmentCommand);
    }

    @Test
    public void parseCommand_listLeave() throws Exception {
        assertTrue(parser.parseCommand(ListLeaveCommand.COMMAND_WORD) instanceof ListLeaveCommand);
        assertTrue(parser.parseCommand(ListLeaveCommand.COMMAND_WORD + " 3") instanceof ListLeaveCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " "
                + SortCommandParser.ACCEPTED_FIELDS.get(0) + " "
                + SortCommandParser.ACCEPTED_ORDERS.get(0)) instanceof SortCommand);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        assertTrue(parser.parseCommand(ScheduleCommand.COMMAND_WORD + " 1") instanceof ScheduleCommand);
    }

    @Test
    public void parseCommand_setSchedule() throws Exception {
        assertTrue(parser.parseCommand(SetScheduleCommand.COMMAND_WORD + " 1 " + PREFIX_TIME_START
                + "1000 " + PREFIX_TIME_END + "1600 " + PREFIX_VENUE + "Toilet") instanceof SetScheduleCommand);
    }

    @Test
    public void parseCommand_setDepartment() throws Exception {
        assertTrue(parser.parseCommand(SetDepartmentCommand.COMMAND_WORD + " 2 " + PREFIX_DEPARTMENT
                + "Junior Management") instanceof SetDepartmentCommand);
    }

    @Test
    public void parseCommand_setPriorityLevel() throws Exception {
        assertTrue(parser.parseCommand(SetPriorityLevelCommand.COMMAND_WORD + " 2 " + PREFIX_PRIORITYLEVEL
                + "3") instanceof SetPriorityLevelCommand);
    }

    @Test
    public void parseCommand_checkLoginStatus() throws Exception {
        assertTrue(parser.parseCommand(CheckLoginStatusCommand.COMMAND_WORD) instanceof CheckLoginStatusCommand);
        assertTrue(parser.parseCommand(CheckLoginStatusCommand.COMMAND_WORD + " hi")
                instanceof CheckLoginStatusCommand);
    }

    @Test
    public void parseCommand_logout() throws Exception {
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD) instanceof LogoutCommand);
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD + " hi") instanceof LogoutCommand);
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

    @Test
    public void parseCommand_check() throws Exception {
        final Mode mode = new Mode("in");

        CheckCommand command = (CheckCommand) parser.parseCommand(CheckCommand.COMMAND_WORD + " "
            + PREFIX_MODE + mode.value);
        assertEquals(new CheckCommand(mode), command);
    }
}
