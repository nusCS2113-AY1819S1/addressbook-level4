package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TEST_MARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEST_MARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TEST_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEST_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditTestMarksCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditTestMarksCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTestMarksCommand.MESSAGE_USAGE);
    private EditTestMarksCommandParser parser = new EditTestMarksCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_NAME_AMY + TEST_MARK_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_NAME_AMY + TEST_NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_NAME_AMY + TEST_NAME_DESC_AMY + TEST_NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_NAME_AMY + TEST_MARK_DESC_AMY + TEST_MARK_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_success() {
        String userInput = VALID_NAME_BOB + TEST_MARK_DESC_BOB + TEST_NAME_DESC_BOB;
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTest(VALID_TEST_BOB).build();
        List<String> nameList = new ArrayList<>();
        nameList.add("Bob");
        nameList.add("Choo");
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(nameList);
        EditTestMarksCommand expectedCommand = new EditTestMarksCommand(nameContainsKeywordsPredicate,
                TEST_NAME_DESC_BOB, TEST_MARK_DESC_BOB, null, nameList);

    }


}
