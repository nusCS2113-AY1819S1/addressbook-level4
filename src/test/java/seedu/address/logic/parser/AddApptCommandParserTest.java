package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRNAME_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_APPT1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.testutil.ApptBuilder;

public class AddApptCommandParserTest {
    private AddApptCommandParser parser = new AddApptCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        // have appt
        Index targetIndex = INDEX_FIRST_PERSON;
        AddApptCommand expectedCommand = new AddApptCommand(INDEX_FIRST_PERSON, new ApptBuilder().build());
        assertParseSuccess(parser, targetIndex.getOneBased()
                + " " + PREFIX_APPT_START + VALID_START_APPT1
                + " " + PREFIX_APPT_END + VALID_END_APPT1
                + " " + PREFIX_APPT_VENUE + VALID_VENUE_APPT1
                + " " + PREFIX_APPT_INFO + VALID_INFO_APPT1
                + " " + PREFIX_APPT_DRNAME + VALID_DRNAME_APPT1, expectedCommand);

        // no appt --> MIGHT REMOVE
        // userInput = targetIndex.getOneBased() + " " + PREFIX_APPT_INFO;
        // expectedCommand = new AddApptCommand(INDEX_FIRST_PERSON, new Appt(""));
        // assertParseSuccess(parser, userInput, expectedCommand);
    }

    // MIGHT ADD ON SOME MORE
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddApptCommand.COMMAND_WORD, expectedMessage);

        // missing index
        assertParseFailure(parser, PREFIX_APPT_START + VALID_START_APPT1
                + " " + PREFIX_APPT_END + VALID_END_APPT1
                + " " + PREFIX_APPT_VENUE + VALID_VENUE_APPT1
                + " " + PREFIX_APPT_INFO + VALID_INFO_APPT1
                + " " + PREFIX_APPT_DRNAME + VALID_DRNAME_APPT1, expectedMessage);
    }
}
