package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInfoCommand;
import seedu.address.model.person.Nric;

public class AddInfoCommandParserTest {
    private AddInfoCommandParser parser = new AddInfoCommandParser();
    private final String nonEmptyNric = "S8010517G";

    @Test
    public void parse_indexSpecified_success() {
        // Have NRIC
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ADD_INFO_NRIC + nonEmptyNric;
        AddInfoCommand expectedCommand = new AddInfoCommand(INDEX_FIRST_PERSON, new Nric(nonEmptyNric));
        assertParseSuccess(parser, userInput, expectedCommand);

        // No NRIC
        userInput = targetIndex.getOneBased() + " " + PREFIX_ADD_INFO_NRIC;
        assertParseFailure(parser, userInput, Nric.MESSAGE_NRIC_CONSTRAINTS);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInfoCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddInfoCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddInfoCommand.COMMAND_WORD + " " + nonEmptyNric, expectedMessage);
    }
}
