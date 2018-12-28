package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MARITAL_STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.OCCUPATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEIGHT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MARITAL_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCUPATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInfoCommand;
import seedu.address.logic.commands.AddInfoCommand.AddInfoPersonDescriptor;
import seedu.address.testutil.AddInfoPersonDescriptorBuilder;

public class AddInfoCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddInfoCommand.MESSAGE_USAGE);

    private AddInfoCommandParser parser = new AddInfoCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NRIC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AddInfoCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-1" + NRIC_DESC_AMY, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "0" + NRIC_DESC_AMY, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "A" + NRIC_DESC_AMY, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 c/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NRIC_DESC_AMY + DOB_DESC_AMY + HEIGHT_DESC_AMY + WEIGHT_DESC_AMY
                + GENDER_DESC_AMY + BLOODTYPE_DESC_AMY + OCCUPATION_DESC_AMY + MARITAL_STATUS_DESC_AMY;

        AddInfoPersonDescriptor descriptor = new AddInfoPersonDescriptorBuilder().withNric(VALID_NRIC_AMY)
                .withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).withHeight(VALID_HEIGHT_AMY).withWeight(VALID_WEIGHT_AMY)
                .withGender(VALID_GENDER_AMY).withBloodType(VALID_BLOODTYPE_AMY).withOccupation(VALID_OCCUPATION_AMY)
                .withMaritalStatus(VALID_MARITAL_STATUS_AMY).build();
        AddInfoCommand expectedCommand = new AddInfoCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
