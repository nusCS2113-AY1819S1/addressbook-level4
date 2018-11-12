package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CALORIES;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DIFFICULTY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GENDER;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_HEIGHT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_USERNAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_WEIGHT;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_CALORIES;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_DIFFICULTY;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_DURATION;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_GENDER;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_HEIGHT;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_USERNAME;
import static seedu.address.commons.core.Messages.MESSAGE_VALID_WEIGHT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ModifyCommand;

public class ModifyCommandParserTest {
    private ModifyCommandParser parser = new ModifyCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ModifyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidField_failure() {

        // invalid calories field specified
        assertParseFailure(parser, " calories/150.2", String.format(MESSAGE_INVALID_CALORIES,
                MESSAGE_VALID_CALORIES));

        // invalid difficulty field specified
        assertParseFailure(parser, " difficulty/easy", String.format(MESSAGE_INVALID_DIFFICULTY,
                MESSAGE_VALID_DIFFICULTY));

        // invalid duration field specified
        assertParseFailure(parser, " duration/30", String.format(MESSAGE_INVALID_DURATION,
                MESSAGE_VALID_DURATION));

        // invalid username field specified
        assertParseFailure(parser, " username/John@Doe", String.format(MESSAGE_INVALID_USERNAME,
                MESSAGE_VALID_USERNAME));
    }

    @Test
    public void parse_emptyField_failure() {

        // empty gender field specified
        assertParseFailure(parser, " gender/", String.format(MESSAGE_INVALID_GENDER, MESSAGE_VALID_GENDER));

        // empty calories field specified
        assertParseFailure(parser, " calories/", String.format(MESSAGE_INVALID_CALORIES,
                MESSAGE_VALID_CALORIES));

        // empty difficulty field specified
        assertParseFailure(parser, " difficulty/", String.format(MESSAGE_INVALID_DIFFICULTY,
                MESSAGE_VALID_DIFFICULTY));

        // empty duration field specified
        assertParseFailure(parser, " duration/", String.format(MESSAGE_INVALID_DURATION,
                MESSAGE_VALID_DURATION));

        // empty height field specified
        assertParseFailure(parser, " height/", String.format(MESSAGE_INVALID_HEIGHT,
                MESSAGE_VALID_HEIGHT));

        // empty username field specified
        assertParseFailure(parser, " username/", String.format(MESSAGE_INVALID_USERNAME,
                MESSAGE_VALID_USERNAME));

        // empty weight field specified
        assertParseFailure(parser, " weight/", String.format(MESSAGE_INVALID_WEIGHT,
                MESSAGE_VALID_WEIGHT));
    }

}
