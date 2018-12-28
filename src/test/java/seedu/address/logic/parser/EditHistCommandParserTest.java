package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_COUNTRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DISCHARGE_STATUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_OLD_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DISCHARGE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_OLD_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditHistCommand;
import seedu.address.logic.commands.EditHistCommand.EditHistDescriptor;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.testutil.EditHistDescriptorBuilder;

//@@author xShaivan
public class EditHistCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditHistCommand.MESSAGE_USAGE);
    private EditHistCommandParser parser = new EditHistCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and no date specified
        assertParseFailure(parser, PREFIX_HISTORY_ALLERGY
                + VALID_HISTORY_ALLERGY, MESSAGE_INVALID_FORMAT);
        // has index but no date specified
        assertParseFailure(parser, INDEX_FIRST_PERSON + " " + PREFIX_HISTORY_ALLERGY
                + " " + VALID_HISTORY_ALLERGY, MESSAGE_INVALID_FORMAT);
        // no index but has date specified
        assertParseFailure(parser, PREFIX_HISTORY_DATE + VALID_HISTORY_DATE + " " + PREFIX_HISTORY_ALLERGY
                + VALID_HISTORY_ALLERGY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PREFIX_HISTORY_DATE + VALID_HISTORY_DATE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_HISTORY_DATE + VALID_HISTORY_DATE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_HISTORY_OLD_DATE + VALID_HISTORY_OLD_DATE
                + " " + PREFIX_HISTORY_DATE + VALID_HISTORY_DATE
                + " " + PREFIX_HISTORY_ALLERGY + VALID_HISTORY_ALLERGY
                + " " + PREFIX_HISTORY_COUNTRY + VALID_HISTORY_COUNTRY
                + " " + PREFIX_HISTORY_DISCHARGE_STATUS + VALID_HISTORY_DISCHARGE_STATUS;

        EditHistDescriptor descriptor = new EditHistDescriptorBuilder().withMedHistDate(VALID_HISTORY_DATE)
                .withAllergy(VALID_HISTORY_ALLERGY).withPrevCountry(VALID_HISTORY_COUNTRY)
                .withDischargeStatus(VALID_HISTORY_DISCHARGE_STATUS).build();
        EditHistCommand expectedCommand =
                new EditHistCommand(targetIndex, new MedHistDate(VALID_HISTORY_OLD_DATE), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
