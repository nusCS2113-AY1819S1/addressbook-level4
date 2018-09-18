package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRecords.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.record.Expense;
import seedu.address.model.record.Income;
import seedu.address.model.record.Name;
import seedu.address.model.record.Date;
import seedu.address.testutil.Assert;

public class XmlAdaptedRecordTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "+651234";
    private static final String INVALID_EXPENSE = " ";
    private static final String INVALID_INCOME = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final String VALID_INCOME = BENSON.getIncome().toString();
    private static final String VALID_EXPENSE = BENSON.getExpense().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRecordDetails_returnsRecord() throws Exception {
        XmlAdaptedRecord record = new XmlAdaptedRecord(BENSON);
        assertEquals(BENSON, record.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(INVALID_NAME, VALID_DATE, VALID_INCOME, VALID_EXPENSE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(null, VALID_DATE, VALID_INCOME, VALID_EXPENSE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_NAME, INVALID_DATE, VALID_INCOME, VALID_EXPENSE, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_NAME, null, VALID_INCOME, VALID_EXPENSE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidIncome_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_NAME, VALID_DATE, INVALID_INCOME, VALID_EXPENSE, VALID_TAGS);
        String expectedMessage = Income.MESSAGE_INCOME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullIncome_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_NAME, VALID_DATE, null, VALID_EXPENSE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Income.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidExpense_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_NAME, VALID_DATE, VALID_INCOME, INVALID_EXPENSE, VALID_TAGS);
        String expectedMessage = Expense.MESSAGE_EXPENSE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullExpense_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_NAME, VALID_DATE, VALID_INCOME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Expense.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_NAME, VALID_DATE, VALID_INCOME, VALID_EXPENSE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, record::toModelType);
    }

}
