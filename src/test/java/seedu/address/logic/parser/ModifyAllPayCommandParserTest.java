package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BONUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BONUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BONUS_PARSER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BONUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BONUS_OVER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARSER_BONUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ModifyAllPayCommand;
import seedu.address.logic.commands.ModifyAllPayCommand.ModSalaryDescriptor;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.Salary;
import seedu.address.testutil.ModAllSalaryDescriptorBuilder;

public class ModifyAllPayCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyAllPayCommand.MESSAGE_USAGE);

    private ModifyAllPayCommandParser parser = new ModifyAllPayCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no prefix specified
        assertParseFailure(parser, VALID_SALARY_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyAllPayCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_SALARY_DESC, Salary.MESSAGE_SALARY_CONSTRAINTS); // invalid salary
        assertParseFailure(parser, INVALID_BONUS_DESC, Bonus.MESSAGE_BONUS_CONSTRAINTS); // invalid bonus

        // invalid salary followed by valid bonus
        assertParseFailure(parser, INVALID_SALARY_DESC + BONUS_DESC_AMY,
                Salary.MESSAGE_SALARY_CONSTRAINTS);

        // valid salary followed by invalid bonus.
        assertParseFailure(parser, SALARY_DESC_AMY + INVALID_BONUS_DESC,
                Bonus.MESSAGE_BONUS_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_SALARY_DESC + INVALID_BONUS_DESC,
                Salary.MESSAGE_SALARY_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = SALARY_DESC_BOB + BONUS_PARSER_DESC_AMY;

        ModSalaryDescriptor descriptor = new ModAllSalaryDescriptorBuilder().withSalary(VALID_SALARY_BOB)
                .withBonus(VALID_PARSER_BONUS_AMY).build();
        ModifyAllPayCommand expectedCommand = new ModifyAllPayCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // salary
        String userInput = SALARY_DESC_AMY;
        ModSalaryDescriptor descriptor = new ModAllSalaryDescriptorBuilder().withSalary(VALID_SALARY_AMY).build();
        ModifyAllPayCommand expectedCommand = new ModifyAllPayCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // bonus
        userInput = BONUS_PARSER_DESC_AMY;
        descriptor = new ModAllSalaryDescriptorBuilder().withBonus(VALID_PARSER_BONUS_AMY).build();
        expectedCommand = new ModifyAllPayCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = SALARY_DESC_AMY + BONUS_DESC_AMY
                + SALARY_DESC_AMY + BONUS_DESC_AMY + SALARY_DESC_BOB + BONUS_DESC_BOB;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = BONUS_DESC_BOB + BONUS_DESC_BOB;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_oneFieldSpecified_failure() {
        // bonus
        String userInput = INVALID_BONUS_OVER_DESC;
        String expectedOutput = Bonus.MESSAGE_BONUS_CONSTRAINTS;
        assertParseFailure(parser, userInput, expectedOutput);
    }
}
