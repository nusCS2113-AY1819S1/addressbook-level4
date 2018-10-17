package seedu.planner.storage;

import static org.junit.Assert.assertEquals;
import static seedu.planner.storage.xmljaxb.XmlAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Name;
import seedu.planner.storage.xmljaxb.XmlAdaptedRecord;
import seedu.planner.storage.xmljaxb.XmlAdaptedTag;
import seedu.planner.testutil.Assert;

public class XmlAdaptedRecordTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "+651234";
    private static final String INVALID_MONEYFLOW = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = CAIFAN.getName().toString();
    private static final String VALID_DATE = CAIFAN.getDate().toString();
    private static final String VALID_MONEYFLOW = CAIFAN.getMoneyFlow().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = CAIFAN.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRecordDetails_returnsRecord() throws Exception {
        XmlAdaptedRecord record = new XmlAdaptedRecord(CAIFAN);
        assertEquals(CAIFAN, record.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(INVALID_NAME, VALID_DATE, VALID_MONEYFLOW, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(null, VALID_DATE, VALID_MONEYFLOW, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_NAME, INVALID_DATE, VALID_MONEYFLOW, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_NAME, null, VALID_MONEYFLOW, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidMoneyFlow_throwsIllegalValueException() {
        XmlAdaptedRecord record =
                new XmlAdaptedRecord(VALID_NAME, VALID_DATE, INVALID_MONEYFLOW, VALID_TAGS);
        String expectedMessage = MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullMoneyFlow_throwsIllegalValueException() {
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_NAME, VALID_DATE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MoneyFlow.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRecord record = new XmlAdaptedRecord(VALID_NAME, VALID_DATE, VALID_MONEYFLOW, invalidTags);
        Assert.assertThrows(IllegalValueException.class, record::toModelType);
    }

}
