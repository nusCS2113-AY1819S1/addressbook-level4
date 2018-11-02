package seedu.planner.commons.util;

import static junit.framework.TestCase.assertTrue;
import static seedu.planner.commons.util.CompareUtil.compareDate;
import static seedu.planner.commons.util.CompareUtil.compareMonth;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.INDO;

import org.junit.Test;

import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;

public class CompareUtilTest {

    Month MONTH_AUG_2018 = new Month("aug-2018");
    Month MONTH_FEB_2018 = new Month("feb-2018");
    Month MONTH_AUG_2017 = new Month("aug-2017");

    @Test
    public void compareNameAttribute_equalsTest() { // test exactly similar names
        Record record = new RecordBuilder(CAIFAN).withName("Indo").build();
        int compareResult = CompareUtil.compareNameAttribute().compare(INDO, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareNameAttributeLowerCase_equalsTest() { // test similar names but different case
        Record record = new RecordBuilder(CAIFAN).withName("indo").build();
        int compareResult = CompareUtil.compareNameAttribute().compare(INDO, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareNameAttribute_lessThanTest() { // test different names
        Record record = new RecordBuilder(CAIFAN).withName(("indo")).build();
        int compareResult = CompareUtil.compareNameAttribute().compare(CAIFAN, record);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareNameAttribute_greaterThanTest() {
        Record record = new RecordBuilder(INDO).withName(("caifan")).build();
        int compareResult = CompareUtil.compareNameAttribute().compare(INDO, record);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareDateAttribute_equalsTest() {
        Record record = new RecordBuilder(INDO).withDate((CAIFAN.getDate().value)).build();
        int compareResult = CompareUtil.compareDateAttribute().compare(CAIFAN, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareDateAttribute_lessThanTest() { // test different names
        Record record = new RecordBuilder(INDO).withDate((CAIFAN.getDate().value)).build();
        int compareResult = CompareUtil.compareDateAttribute().compare(INDO, record);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareDateAttribute_greaterThanTest() {
        Record record = new RecordBuilder(CAIFAN).withDate((INDO.getDate().value)).build();
        int compareResult = CompareUtil.compareDateAttribute().compare(CAIFAN, record);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareMoneyflowAttribute_equalsTest() {
        Record record = new RecordBuilder(CAIFAN).withMoneyFlow((INDO.getMoneyFlow().value)).build();
        int compareResult = CompareUtil.compareMoneyflowAttribute().compare(INDO, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareMoneyflowAttribute_lessThanTest() { // test different names
        Record record = new RecordBuilder(INDO).withMoneyFlow((CAIFAN.getMoneyFlow().value)).build();
        int compareResult = CompareUtil.compareMoneyflowAttribute().compare(INDO, record);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareMoneyflowAttribute_greaterThanTest() {
        Record record = new RecordBuilder(CAIFAN).withMoneyFlow((INDO.getMoneyFlow().value)).build();
        int compareResult = CompareUtil.compareMoneyflowAttribute().compare(CAIFAN, record);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareDate_equalsTest() {
        int compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-04-2018"));
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareDate_lessThanTest() {
        int compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-04-2019"));
        assertTrue(compareResult <= -1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-05-2018"));
        assertTrue(compareResult <= -1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("27-04-2018"));
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareDate_moreThanTest() {
        int compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-04-2017"));
        assertTrue(compareResult >= 1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-03-2018"));
        assertTrue(compareResult >= 1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("24-04-2018"));
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareMonth_equalsTest() {
        int compareResult = compareMonth().compare(MONTH_AUG_2018, new Month("AUG-2018"));
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareMonth_lessThanTest() {
        int compareResult = compareMonth().compare(MONTH_AUG_2017, MONTH_AUG_2018);
        assertTrue(compareResult <= -1);
        compareResult = compareMonth().compare(MONTH_FEB_2018, MONTH_AUG_2018);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareMonth_moreThanTest() {
        int compareResult = compareMonth().compare(MONTH_AUG_2018, MONTH_FEB_2018);
        assertTrue(compareResult >= 1);
        compareResult = compareMonth().compare(MONTH_AUG_2018, MONTH_AUG_2017);
        assertTrue(compareResult >= 1);
    }

}
