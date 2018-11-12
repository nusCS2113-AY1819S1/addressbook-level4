package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEPARTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.Department;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.PositionContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private static final String SORT_ORDER = "asc";
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_onlyDepartmentFieldPresent_success() {
        DepartmentContainsKeywordsPredicate expectedDepartmentPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("Human Resource"));
        PositionContainsKeywordsPredicate expectedPositionPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList(""));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SORT_ORDER + DEPARTMENT_DESC_BOB,
                new FilterCommand(expectedDepartmentPredicate, expectedPositionPredicate, SORT_ORDER));
    }

    @Test
    public void parse_onlyPositionFieldPresent_success() {
        DepartmentContainsKeywordsPredicate expectedDepartmentPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList(""));
        PositionContainsKeywordsPredicate expectedPositionPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList("Intern"));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SORT_ORDER + POSITION_DESC_BOB,
                new FilterCommand(expectedDepartmentPredicate, expectedPositionPredicate, SORT_ORDER));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        DepartmentContainsKeywordsPredicate expectedDepartmentPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("Human Resource"));
        PositionContainsKeywordsPredicate expectedPositionPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList("Intern"));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SORT_ORDER + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB,
                new FilterCommand(expectedDepartmentPredicate, expectedPositionPredicate, SORT_ORDER));
    }

    @Test
    public void parse_multipleFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

        // multiple departments
        assertParseFailure(parser, SORT_ORDER + DEPARTMENT_DESC_AMY + DEPARTMENT_DESC_BOB, expectedMessage);

        // multiple positions
        assertParseFailure(parser, SORT_ORDER + POSITION_DESC_AMY + POSITION_DESC_BOB, expectedMessage);

        // multiple departments and positions
        assertParseFailure(parser, SORT_ORDER + DEPARTMENT_DESC_AMY + DEPARTMENT_DESC_BOB
                + POSITION_DESC_AMY + POSITION_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_noField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

        // no department and no position
        assertParseFailure(parser, SORT_ORDER, expectedMessage);

        // no sort order
        assertParseFailure(parser, DEPARTMENT_DESC_BOB + POSITION_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid department
        assertParseFailure(parser, SORT_ORDER + INVALID_DEPARTMENT_DESC,
                Department.MESSAGE_DEPARTMENT_KEYWORD_CONSTRAINTS);

        // invalid position
        assertParseFailure(parser, SORT_ORDER + INVALID_POSITION_DESC,
                Position.MESSAGE_POSITION_KEYWORD_CONSTRAINTS);
    }
}
