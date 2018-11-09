package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MILESTONEDESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_MILESTONE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_RANK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MILESTONE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RANK_DESC_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RANK_DESC_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.MILESTONE_DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MILESTONE_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.AddMilestoneCommand;
import seedu.address.model.task.Milestone;
import seedu.address.testutil.MilestoneBuilder;

//@@author JeremyInElysium
public class AddMilestoneCommandParserTest {
    private AddMilestoneCommandParser parser = new AddMilestoneCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Milestone expectedMilestone = new MilestoneBuilder().build();

        assertParseSuccess(parser,
                INDEX_DESC_1 + MILESTONE_DESCRIPTION_DESC_1 + RANK_DESC_1,
                new AddMilestoneCommand(INDEX_FIRST_TASK, expectedMilestone));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneCommand.MESSAGE_USAGE);

        //missing index prefix
        assertParseFailure(parser, VALID_INDEX_1 + MILESTONE_DESCRIPTION_DESC_1 + RANK_DESC_1, expectedMessage);

        //missing milestone description prefix
        assertParseFailure(parser, INDEX_DESC_1 + VALID_MILESTONE_DESCRIPTION_1 + RANK_DESC_1, expectedMessage);

        //missing rank prefix
        assertParseFailure(parser, INDEX_DESC_1 + MILESTONE_DESCRIPTION_DESC_1 + VALID_RANK_1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid index, cannot be zero
        assertParseFailure(parser, INVALID_INDEX_DESC_ZERO + MILESTONE_DESCRIPTION_DESC_1 + RANK_DESC_1,
                ParserUtil.MESSAGE_INVALID_INDEX);

        //invalid index, cannot be negative integers
        assertParseFailure(parser, INVALID_INDEX_DESC_NEGATIVE + MILESTONE_DESCRIPTION_DESC_1 + RANK_DESC_1,
                ParserUtil.MESSAGE_INVALID_INDEX);

        //invalid milestone description, cannot be more longer than 40 characters
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_MILESTONE_DESCRIPTION_DESC + RANK_DESC_1,
                String.format(MESSAGE_INVALID_MILESTONEDESCRIPTION, AddMilestoneCommand.MESSAGE_USAGE));

        //invalid rank, cannot be zero
        assertParseFailure(parser, INDEX_DESC_1 + MILESTONE_DESCRIPTION_DESC_1 + INVALID_RANK_DESC_ZERO,
                ParserUtil.MESSAGE_INVALID_RANK);

        //invalid rank, cannot be negative integers
        assertParseFailure(parser, INDEX_DESC_1 + MILESTONE_DESCRIPTION_DESC_1 + INVALID_RANK_DESC_NEGATIVE,
                ParserUtil.MESSAGE_INVALID_RANK);

        //empty index
        assertParseFailure(parser, EMPTY_INDEX_DESC + MILESTONE_DESCRIPTION_DESC_1 + RANK_DESC_1,
                ParserUtil.MESSAGE_EMPTY_INDEX);

        //empty milestone description
        assertParseFailure(parser, INDEX_DESC_1 + EMPTY_MILESTONE_DESCRIPTION_DESC + RANK_DESC_1,
                ParserUtil.MESSAGE_EMPTY_MILESTONE);

        //empty rank
        assertParseFailure(parser, INDEX_DESC_1 + MILESTONE_DESCRIPTION_DESC_1 + EMPTY_RANK_DESC,
                ParserUtil.MESSAGE_EMPTY_RANK);
    }
}
