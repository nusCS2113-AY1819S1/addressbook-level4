package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.AddMilestoneCommand;
import seedu.address.model.task.Milestone;
import seedu.address.testutil.MilestoneBuilder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.MILESTONE_DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MILESTONE_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

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
}
