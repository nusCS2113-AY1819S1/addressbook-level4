package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEEID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEDICALEXPENSES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEDICALEXPENSES_DESC_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.MISCELLANEOUSEXPENSES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MISCELLANEOUSEXPENSES_DESC_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.TRAVELEXPENSES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TRAVELEXPENSES_DESC_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSESAMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICALEXPENSES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MISCELLANEOUSEXPENSES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAVELEXPENSES_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.expenses.TypicalExpenses.BOB_CLAIM;

import org.junit.Test;

import seedu.address.logic.commands.AddExpensesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenses.Expenses;
import seedu.address.testutil.expenses.ExpensesBuilder;

public class AddExpensesCommandParserTest {
    private AddExpensesCommandParser parser = new AddExpensesCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expenses expectedExpenses = new ExpensesBuilder(BOB_CLAIM).build();

        AddExpensesCommand.EditExpensesDescriptor editExpensesDescriptor = new AddExpensesCommand
                .EditExpensesDescriptor();

        try {
            editExpensesDescriptor.setExpensesAmount(ParserUtil.parseExpensesAmount(VALID_EXPENSESAMOUNT_BOB));
            editExpensesDescriptor.setTravelExpenses(ParserUtil.parseTravelExpenses(VALID_TRAVELEXPENSES_BOB));
            editExpensesDescriptor.setMedicalExpenses(ParserUtil.parseMedicalExpenses(VALID_MEDICALEXPENSES_BOB));
            editExpensesDescriptor.setMiscellaneousExpenses(ParserUtil.parseMiscellaneousExpenses(
                    VALID_MISCELLANEOUSEXPENSES_BOB));

        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        assertParseSuccess(parser, EMPLOYEEID_DESC_BOB + TRAVELEXPENSES_DESC_BOB + MEDICALEXPENSES_DESC_BOB
                + MISCELLANEOUSEXPENSES_DESC_BOB, new AddExpensesCommand(expectedExpenses, editExpensesDescriptor));
    }

    @Test
    public void parse_noFieldPresent_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpensesCommand.MESSAGE_USAGE);

        assertParseFailure(parser, EMPLOYEEID_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_overLoadPrefixes_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT, AddExpensesCommand
                .MESSAGE_USAGE);

        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + TRAVELEXPENSES_DESC_BOB + MEDICALEXPENSES_DESC_BOB
                + MISCELLANEOUSEXPENSES_DESC_BOB + MISCELLANEOUSEXPENSES_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_noFieldsEdited_fail() {
        String expectedMessage = String.format(AddExpensesCommand.MESSAGE_NOT_EDITED);

        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + TRAVELEXPENSES_DESC_EMPTY
                + MEDICALEXPENSES_DESC_EMPTY + MISCELLANEOUSEXPENSES_DESC_EMPTY, expectedMessage);
    }

    @Test
    public void parse_travelExpensesPrefixRepeated_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpensesCommand
                .MESSAGE_USAGE);

        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + TRAVELEXPENSES_DESC_BOB
                + TRAVELEXPENSES_DESC_BOB + MISCELLANEOUSEXPENSES_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_medicalExpensesPrefixRepeated_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpensesCommand
                .MESSAGE_USAGE);

        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + TRAVELEXPENSES_DESC_BOB
                + MEDICALEXPENSES_DESC_BOB + MEDICALEXPENSES_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_miscellaneousExpensesPrefixRepeated_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpensesCommand
                .MESSAGE_USAGE);

        assertParseFailure(parser, EMPLOYEEID_DESC_BOB + MISCELLANEOUSEXPENSES_DESC_BOB
                + MEDICALEXPENSES_DESC_BOB + MISCELLANEOUSEXPENSES_DESC_BOB, expectedMessage);
    }

}
