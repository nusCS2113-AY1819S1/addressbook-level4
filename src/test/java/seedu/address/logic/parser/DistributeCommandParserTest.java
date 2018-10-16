package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_FLAG_FALSE;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_FLAG_INVALID_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_FLAG_INVALID_WORD;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_FLAG_TRUE;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NATIONALITY_FLAG_FALSE;
import static seedu.address.logic.commands.CommandTestUtil.NATIONALITY_FLAG_INVALID_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.NATIONALITY_FLAG_INVALID_WORD;
import static seedu.address.logic.commands.CommandTestUtil.NATIONALITY_FLAG_TRUE;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_GROUPS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_FLAG_FALSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NATIONALITY_FLAG_FALSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_GROUPS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DistributeCommand;
import seedu.address.model.distribute.Distribute;
import seedu.address.model.distribute.DistributeAlgorithm;
import seedu.address.model.group.GroupName;

public class DistributeCommandParserTest {
    DistributeCommandParser parser = new DistributeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        GroupName groupName = new GroupName("CS1010");
        Distribute expectedDistribution = new Distribute(5, groupName,false,false);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010
                + GENDER_FLAG_FALSE + NATIONALITY_FLAG_FALSE, new DistributeCommand(expectedDistribution));

        // multiple group names - last groupname accepted
        assertParseSuccess(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_TUT_1 + GROUP_NAME_DESC_CS1010
                + GENDER_FLAG_FALSE + NATIONALITY_FLAG_FALSE, new DistributeCommand(expectedDistribution));

        // multiple gender flag - last gender flag is accepted
        assertParseSuccess(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + GENDER_FLAG_TRUE
                + GENDER_FLAG_FALSE + NATIONALITY_FLAG_FALSE, new DistributeCommand(expectedDistribution));

        // multiple nationality flag - last gender flag is accepted
        assertParseSuccess(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + GENDER_FLAG_FALSE
                + NATIONALITY_FLAG_TRUE + NATIONALITY_FLAG_FALSE, new DistributeCommand(expectedDistribution));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistributeCommand.MESSAGE_USAGE);

        // missing group name prefix
        assertParseFailure(parser, NUMBER_OF_GROUPS + GENDER_FLAG_FALSE + NATIONALITY_FLAG_FALSE,
                expectedMessage);

        // missing gender flag prefix
        assertParseFailure(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + NATIONALITY_FLAG_FALSE,
                expectedMessage);

        // missing nationality flag prefix
        assertParseFailure(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + GENDER_FLAG_FALSE,
                expectedMessage);

        // missing all prefix
        assertParseFailure(parser, VALID_NUMBER_OF_GROUPS + VALID_GROUP_NAME_CS1010
                        + VALID_GENDER_FLAG_FALSE + VALID_NATIONALITY_FLAG_FALSE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid numberOfGroups Eg. 0
        assertParseFailure(parser, INVALID_GROUP_NUMBER_DESC + VALID_GROUP_NAME_CS1010 + GENDER_FLAG_FALSE
                + NATIONALITY_FLAG_FALSE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistributeCommand.MESSAGE_USAGE));

        // invalid numberOfGroups Eg. out of range or larger than number of person
        assertParseFailure(parser, INVALID_GROUP_NUMBER_DESC + VALID_GROUP_NAME_CS1010 + GENDER_FLAG_FALSE
                + NATIONALITY_FLAG_FALSE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistributeCommand.MESSAGE_USAGE));

        // invalid group name
        assertParseFailure(parser, NUMBER_OF_GROUPS + INVALID_GROUP_NAME_DESC + GENDER_FLAG_FALSE
                + NATIONALITY_FLAG_FALSE, GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);

        // invalid gender flag (numbers that are not 1 or 0)
        assertParseFailure(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + GENDER_FLAG_INVALID_NUMBER
                + NATIONALITY_FLAG_FALSE, DistributeAlgorithm.MESSAGE_FLAG_ERROR);

        // invalid gender flag (word that is not true or false)
        assertParseFailure(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + GENDER_FLAG_INVALID_WORD
                + NATIONALITY_FLAG_FALSE, DistributeAlgorithm.MESSAGE_FLAG_ERROR);

        // invalid nationality flag (numbers that are not 1 or 0)
        assertParseFailure(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + GENDER_FLAG_FALSE
                + NATIONALITY_FLAG_INVALID_NUMBER, DistributeAlgorithm.MESSAGE_FLAG_ERROR);

        // invalid nationality flag (word that is not true or false)
        assertParseFailure(parser, NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010 + GENDER_FLAG_FALSE
                + NATIONALITY_FLAG_INVALID_WORD, DistributeAlgorithm.MESSAGE_FLAG_ERROR);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_GROUP_NUMBER_DESC + INVALID_GROUP_NAME_DESC + GENDER_FLAG_FALSE
                + NATIONALITY_FLAG_FALSE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistributeCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NUMBER_OF_GROUPS + GROUP_NAME_DESC_CS1010
                        + GENDER_FLAG_FALSE + NATIONALITY_FLAG_FALSE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistributeCommand.MESSAGE_USAGE));
    }
}
