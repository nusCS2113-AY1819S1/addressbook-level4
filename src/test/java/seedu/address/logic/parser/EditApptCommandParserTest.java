package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRNAME_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_APPT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_APPT2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_ORIGINAL_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditApptCommand;
import seedu.address.logic.commands.EditApptCommand.EditApptDescriptor;
import seedu.address.model.appt.ApptDateTime;
import seedu.address.testutil.EditApptDescriptorBuilder;

//@@author brandonccm1996
public class EditApptCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditApptCommand.MESSAGE_USAGE);
    private static final String INVALID_INDEX = "-5";

    private EditApptCommandParser parser = new EditApptCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, VALID_START_APPT1, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, INVALID_INDEX + " " + PREFIX_APPT_ORIGINAL_START + VALID_START_APPT1,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_APPT_ORIGINAL_START + VALID_START_APPT1 + " "
                + PREFIX_APPT_START + VALID_START_APPT2 + " " + PREFIX_APPT_END + VALID_END_APPT2 + " "
                + PREFIX_APPT_VENUE + VALID_VENUE_APPT2 + " " + PREFIX_APPT_INFO + VALID_INFO_APPT2 + " "
                + PREFIX_APPT_DRNAME + VALID_DRNAME_APPT2;

        EditApptDescriptor descriptor = new EditApptDescriptorBuilder().withStartTime(VALID_START_APPT2)
                .withEndTime(VALID_END_APPT2).withVenue(VALID_VENUE_APPT2).withInfo(VALID_INFO_APPT2)
                .withDrName(VALID_DRNAME_APPT2).build();
        EditApptCommand expectedCommand = new EditApptCommand(targetIndex, new ApptDateTime(VALID_START_APPT1),
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
