package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLUB_NAME_DESC_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.CLUB_NAME_DESC_ECE;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_TURNOUT_DESC_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_TURNOUT_DESC_ECE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLUB_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_TURNOUT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUMBER_OF_EVENTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_EVENTS_DESC_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_EVENTS_DESC_ECE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_TURNOUT_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_EVENTS_COMPUTING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalClubBudgetElements.COMPUTING_CLUB;

import org.junit.Test;

import seedu.address.logic.commands.BudgetCommand;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.budgetelements.ExpectedTurnout;
import seedu.address.model.budgetelements.NumberOfEvents;
import seedu.address.testutil.ClubBudgetElementsBuilder;

public class BudgetCommandParserTest {
    private BudgetCommandParser parser = new BudgetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ClubBudgetElements expectedClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CLUB_NAME_DESC_COMPUTING
                + EXPECTED_TURNOUT_DESC_COMPUTING + NUMBER_OF_EVENTS_DESC_COMPUTING, new BudgetCommand(expectedClub));

        // multiple  club names - last  club name accepted
        assertParseSuccess(parser, CLUB_NAME_DESC_ECE + CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_COMPUTING
                + NUMBER_OF_EVENTS_DESC_COMPUTING, new BudgetCommand(expectedClub));

        // multiple expected turnouts  - last expected turnout accepted
        assertParseSuccess(parser, CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_ECE
                + EXPECTED_TURNOUT_DESC_COMPUTING + NUMBER_OF_EVENTS_DESC_COMPUTING, new BudgetCommand(expectedClub));

        // multiple number of events - last number of events accepted
        assertParseSuccess(parser, CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_COMPUTING
                + NUMBER_OF_EVENTS_DESC_ECE + NUMBER_OF_EVENTS_DESC_COMPUTING, new BudgetCommand(expectedClub));


    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE);

        // missing club name prefix
        assertParseFailure(parser, VALID_CLUB_NAME_COMPUTING + EXPECTED_TURNOUT_DESC_COMPUTING
                        + NUMBER_OF_EVENTS_DESC_COMPUTING,
                expectedMessage);

        // missing expected turnout prefix
        assertParseFailure(parser, CLUB_NAME_DESC_COMPUTING + VALID_EXPECTED_TURNOUT_COMPUTING
                        + NUMBER_OF_EVENTS_DESC_COMPUTING,
                expectedMessage);

        // missing number of events prefix
        assertParseFailure(parser, CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_COMPUTING
                        + VALID_NUMBER_OF_EVENTS_COMPUTING,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_CLUB_NAME_COMPUTING + VALID_EXPECTED_TURNOUT_COMPUTING
                        + VALID_NUMBER_OF_EVENTS_COMPUTING,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid club name
        assertParseFailure(parser, INVALID_CLUB_NAME_DESC + EXPECTED_TURNOUT_DESC_COMPUTING
                + NUMBER_OF_EVENTS_DESC_COMPUTING, ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS);

        // invalid expected turnout
        assertParseFailure(parser, CLUB_NAME_DESC_COMPUTING + INVALID_EXPECTED_TURNOUT_DESC
                + NUMBER_OF_EVENTS_DESC_COMPUTING, ExpectedTurnout.MESSAGE_EXPECTED_TURNOUT_CONSTRAINTS);

        // invalid number of events
        assertParseFailure(parser, CLUB_NAME_DESC_COMPUTING + EXPECTED_TURNOUT_DESC_COMPUTING
                + INVALID_NUMBER_OF_EVENTS_DESC, NumberOfEvents.MESSAGE_NUMBER_OF_EVENTS_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_CLUB_NAME_DESC + INVALID_EXPECTED_TURNOUT_DESC
                        + NUMBER_OF_EVENTS_DESC_COMPUTING,
                ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CLUB_NAME_DESC_COMPUTING
                        + EXPECTED_TURNOUT_DESC_COMPUTING + NUMBER_OF_EVENTS_DESC_COMPUTING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
    }
}
