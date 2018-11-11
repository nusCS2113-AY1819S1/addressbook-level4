package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
/*import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENDITURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENDITURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EXPENDITURE;*/

import org.junit.Test;

//import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExpenditureCommand;
//import seedu.address.logic.commands.EditExpenditureCommand.EditExpenditureDescriptor;
import seedu.address.model.expenditureinfo.Category;
//import seedu.address.model.expenditureinfo.Description;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Money;
//import seedu.address.testutil.EditExpenditureDescriptorBuilder;

public class EditExpenditureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenditureCommand.MESSAGE_USAGE);

    private EditExpenditureCommandParser parser = new EditExpenditureCommandParser();

    @Test
    public void parsemissingPartsfailure() {
        // no index specified
        assertParseFailure(parser, VALID_EXPENDITURE_DATE_CLOTHES, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditExpenditureCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseinvalidPreamblefailure() {
        // negative index
        assertParseFailure(parser, "-5" + EXPENDITURE_CATEGORY_CLOTHES, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EXPENDITURE_CATEGORY_CLOTHES, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseinvalidValuefailure() {
        assertParseFailure(parser, "1" + INVALID_EXPENDITURE_CATEGORY_DESC, Category.MESSAGE_CATEGORY_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_EXPENDITURE_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EXPENDITURE_MONEY_DESC, Money.MESSAGE_MONEY_CONSTRAINTS); // invalid address

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_EXPENDITURE_MONEY_DESC + EXPENDITURE_CATEGORY_CLOTHES, Money.MESSAGE_MONEY_CONSTRAINTS);

    }

    /*@Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EXPENDITURE;
        String userInput = targetIndex.getOneBased() + EXPENDITURE_DESCRIPTION_CLOTHES +
                EXPENDITURE_CATEGORY_IPHONE + EXPENDITURE_DATE_CLOTHES + EXPENDITURE_MONEY_CLOTHES;

        EditExpenditureDescriptor descriptor = new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES)
                .withCategory(VALID_EXPENDITURE_CATEGORY_IPHONE).withDate(VALID_EXPENDITURE_DATE_CLOTHES).withMoney(VALID_EXPENDITURE_MONEY_CLOTHES).build();
        EditExpenditureCommand expectedExpenditureCommand = new EditExpenditureCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedExpenditureCommand);
    }*/

    /*@Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EXPENDITURE;
        String userInput = targetIndex.getOneBased() + EXPENDITURE_DATE_CLOTHES + EXPENDITURE_CATEGORY_IPHONE;

        EditExpenditureDescriptor descriptor = new EditExpenditureDescriptorBuilder().withDate(VALID_EXPENDITURE_DATE_CLOTHES)
                .withCategory(VALID_EXPENDITURE_CATEGORY_IPHONE).build();
        EditExpenditureCommand expectedCommand = new EditExpenditureCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }*/

    /*@Test
    public void parse_oneFieldSpecified_success() {
        // Description
        Index targetIndex = INDEX_THIRD_EXPENDITURE;
        String userInput = targetIndex.getOneBased() + EXPENDITURE_DESCRIPTION_CLOTHES;
        EditExpenditureDescriptor descriptor = new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build();
        EditExpenditureCommand expectedCommand = new EditExpenditureCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Category
        userInput = targetIndex.getOneBased() + EXPENDITURE_CATEGORY_IPHONE;
        descriptor = new EditExpenditureDescriptorBuilder().withCategory(VALID_EXPENDITURE_CATEGORY_IPHONE).build();
        expectedCommand = new EditExpenditureCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Date
        userInput = targetIndex.getOneBased() + EXPENDITURE_DATE_CLOTHES;
        descriptor = new EditExpenditureDescriptorBuilder().withDate(VALID_EXPENDITURE_DATE_CLOTHES).build();
        expectedCommand = new EditExpenditureCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Money
        userInput = targetIndex.getOneBased() + EXPENDITURE_MONEY_CLOTHES;
        descriptor = new EditExpenditureDescriptorBuilder().withMoney(VALID_EXPENDITURE_MONEY_CLOTHES).build();
        expectedCommand = new EditExpenditureCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }*/


    /*@Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EXPENDITURE;
        String userInput = targetIndex.getOneBased() + INVALID_EXPENDITURE_MONEY_DESC + EXPENDITURE_MONEY_CLOTHES;
        EditExpenditureDescriptor descriptor = new EditExpenditureDescriptorBuilder().withMoney(VALID_EXPENDITURE_MONEY_CLOTHES).build();
        EditExpenditureCommand expectedCommand = new EditExpenditureCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EXPENDITURE_DATE_CLOTHES + INVALID_EXPENDITURE_MONEY_DESC + EXPENDITURE_CATEGORY_CLOTHES
                + EXPENDITURE_DESCRIPTION_CLOTHES;
        descriptor = new EditExpenditureDescriptorBuilder().withMoney(VALID_EXPENDITURE_MONEY_CLOTHES).withCategory(VALID_EXPENDITURE_CATEGORY_CLOTHES)
                .withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build();
        expectedCommand = new EditExpenditureCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }*/
}
