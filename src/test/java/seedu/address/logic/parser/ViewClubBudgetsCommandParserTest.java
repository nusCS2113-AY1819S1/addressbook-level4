package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLUB_NAME_DESC_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLUB_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_COMPUTING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ViewClubBudgetsCommand;
import seedu.address.model.budgetelements.ClubName;

public class ViewClubBudgetsCommandParserTest {
    private ViewClubBudgetsCommandParser parser = new ViewClubBudgetsCommandParser();

    /**@Test
    public void parse_allFieldsPresent_success() {
        FinalClubBudget expectedClub = new FinalClubBudgetBuilder(COMPUTING_CLUB).build();

        //need a typicaltotalbudget builder

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_TOTAL_BUDGET_DESC,
    new BudgetCalculationCommand(expectedClub));

        // multiple  club names - last  club name accepted
        assertParseSuccess(parser, CLUB_NAME_DESC_ECE + CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_COMPUTING
                + NUMBER_OF_EVENTS_DESC_COMPUTING, new BudgetCommand(expectedClub));

        // multiple expected turnouts  - last expected turnout accepted
        assertParseSuccess(parser, CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_ECE
                + EXPECTED_TURNOUT_DESC_COMPUTING + NUMBER_OF_EVENTS_DESC_COMPUTING, new BudgetCommand(expectedClub));

        // multiple number of events - last number of events accepted
        assertParseSuccess(parser, CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_COMPUTING
                + NUMBER_OF_EVENTS_DESC_ECE + NUMBER_OF_EVENTS_DESC_COMPUTING, new BudgetCommand(expectedClub));


    }*/

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewClubBudgetsCommand.MESSAGE_USAGE);

        // missing club name prefix
        assertParseFailure(parser, VALID_CLUB_NAME_COMPUTING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid club name
        assertParseFailure(parser, INVALID_CLUB_NAME_DESC, ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CLUB_NAME_DESC_COMPUTING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewClubBudgetsCommand.MESSAGE_USAGE));
    }
}
