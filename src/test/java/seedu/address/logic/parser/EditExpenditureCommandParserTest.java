package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXPENDITURE_CATEGORY_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.EXPENDITURE_CATEGORY_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.EXPENDITURE_DATE_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.EXPENDITURE_DATE_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPENDITURE_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPENDITURE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPENDITURE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPENDITURE_MONEY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_CATEGORY_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_CATEGORY_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_DATE_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_DATE_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_DESCRIPTION_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_DESCRIPTION_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_MONEY_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_MONEY_IPHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENDITURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENDITURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EXPENDITURE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExpenditureCommand;
import seedu.address.logic.commands.EditExpenditureCommand.EditExpenditureDescriptor;
import seedu.address.model.expenditureinfo.Category;
import seedu.address.model.expenditureinfo.Description;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Money;
import seedu.address.testutil.EditExpenditureDescriptorBuilder;

public class EditExpenditureCommandParserTest {
}
