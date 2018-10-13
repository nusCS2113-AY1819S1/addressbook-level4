package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERSON_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSON_INDEX_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalPersonIndexs.getSingleTypicalPersonIndexs;
import static seedu.address.testutil.TypicalPersonIndexs.getTypicalPersonIndexs;

import org.junit.Test;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.PersonIndex;

public class AddGroupCommandParserTest {
    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUP_NAME_DESC_TUT_1 + PERSON_INDEX_DESC_1,
                new AddGroupCommand(TUT_1.getGroupName(),getSingleTypicalPersonIndexs()));

        // multiple group names - last group name accepted
        assertParseSuccess(parser, GROUP_NAME_DESC_CS1010 + GROUP_NAME_DESC_TUT_1 + PERSON_INDEX_DESC_1,
                new AddGroupCommand(TUT_1.getGroupName(),getSingleTypicalPersonIndexs()));

        // multiple indexs - all accepted
        assertParseSuccess(parser, GROUP_NAME_DESC_TUT_1 + PERSON_INDEX_DESC_1 + PERSON_INDEX_DESC_2,
                new AddGroupCommand(TUT_1.getGroupName(),getTypicalPersonIndexs()));
    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_GROUP_NAME_TUT_1 + PERSON_INDEX_DESC_1,
                expectedMessage);

        // missing person index prefix
        assertParseFailure(parser, GROUP_NAME_DESC_TUT_1 + VALID_PERSON_INDEX_1,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_GROUP_NAME_TUT_1 + VALID_PERSON_INDEX_1,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid group name
        assertParseFailure(parser, INVALID_GROUP_NAME_DESC + PERSON_INDEX_DESC_1,
                GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser, GROUP_NAME_DESC_TUT_1 + INVALID_PERSON_INDEX_DESC,
                PersonIndex.MESSAGE_PERSON_INDEX_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_GROUP_NAME_DESC + INVALID_PERSON_INDEX_DESC,
                GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GROUP_NAME_DESC_TUT_1 + PERSON_INDEX_DESC_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
    }

}
