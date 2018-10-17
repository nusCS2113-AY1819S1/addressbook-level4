package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_COUNTRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddHistCommand;
import seedu.address.testutil.MedHistoryBuilder;

public class AddHistCommandParserTest {
    private AddHistCommandParser parser = new AddHistCommandParser();

    @Test
    public void parseindexSpecifiedsuccess() {
        // have medical history
        Index targetIndex = INDEX_FIRST_PERSON;
        AddHistCommand expectedCommand = new AddHistCommand(INDEX_FIRST_PERSON, new MedHistoryBuilder().build());
        assertParseSuccess(parser, targetIndex.getOneBased()
                + " " + PREFIX_HISTORY_DATE + VALID_HISTORY_DATE
                + " " + PREFIX_HISTORY_ALLERGY + VALID_HISTORY_ALLERGY
                + " " + PREFIX_HISTORY_COUNTRY + VALID_HISTORY_COUNTRY, expectedCommand);
    }
    @Test
    public void parsemissingCompulsoryFieldfailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHistCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, AddHistCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, PREFIX_HISTORY_DATE + VALID_HISTORY_DATE
                + " " + PREFIX_HISTORY_ALLERGY + VALID_HISTORY_ALLERGY
                + " " + PREFIX_HISTORY_COUNTRY + VALID_HISTORY_COUNTRY, expectedMessage);
    }
}
