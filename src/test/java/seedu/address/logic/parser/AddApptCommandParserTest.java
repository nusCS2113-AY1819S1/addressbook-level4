package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.model.timetable.Appt;

public class AddApptCommandParserTest {
    private AddApptCommandParser parser = new AddApptCommandParser();
    private final String nonEmptyAppt = "Some appt";

    @Test
    public void parse_indexSpecified_success() {
        // have appt
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_APPT_INFO + nonEmptyAppt;
        AddApptCommand expectedCommand = new AddApptCommand(INDEX_FIRST_PERSON, new Appt(nonEmptyAppt));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no appt
        userInput = targetIndex.getOneBased() + " " + PREFIX_APPT_INFO;
        expectedCommand = new AddApptCommand(INDEX_FIRST_PERSON, new Appt(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddApptCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddApptCommand.COMMAND_WORD + " " + nonEmptyAppt, expectedMessage);
    }
}
