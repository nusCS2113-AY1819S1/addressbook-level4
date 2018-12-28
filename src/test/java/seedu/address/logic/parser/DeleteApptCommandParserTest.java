package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_APPT_WPREFIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT_WPREFIX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.util.DateTimeUtil.DATE_TIME_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteApptCommand;
import seedu.address.model.appt.ApptDateTime;

public class DeleteApptCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteApptCommand.MESSAGE_USAGE);
    private DeleteApptCommandParser parser = new DeleteApptCommandParser();
    private Index targetIndex = INDEX_FIRST_PERSON;

    @Test
    public void parse_indexSpecifiedWithAllCompulsoryFields_success() {
        DeleteApptCommand expectedCommand = new DeleteApptCommand(INDEX_FIRST_PERSON,
                new ApptDateTime(VALID_START_APPT1));
        assertParseSuccess(parser, targetIndex.getOneBased() + VALID_START_APPT_WPREFIX,
                expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        // no parameters
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // missing index
        assertParseFailure(parser, VALID_START_APPT_WPREFIX, MESSAGE_INVALID_FORMAT);

        // missing appt start prefix
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_START_APPT1, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid appt start
        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_START_APPT_WPREFIX,
                DATE_TIME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-2" + VALID_START_APPT_WPREFIX, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_START_APPT_WPREFIX, MESSAGE_INVALID_FORMAT);

        // non-numeric index
        assertParseFailure(parser, "a" + VALID_START_APPT_WPREFIX, MESSAGE_INVALID_FORMAT);
    }
}
