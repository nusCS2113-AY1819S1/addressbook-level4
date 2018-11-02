package seedu.planner.commons.util;

import static junit.framework.TestCase.assertTrue;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.INDO;

import org.junit.Test;

import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;

public class SortUtilTest {

    @Test
    public void compareNameAttribute_equalsTest() { // test exactly similar names
        Record record = new RecordBuilder(CAIFAN).withName("Indo").build();
        int compareResult = SortUtil.compareNameAttribute().compare(INDO, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareNameAttributeLowerCase_equalsTest() { // test similar names but different case
        Record record = new RecordBuilder(CAIFAN).withName("indo").build();
        int compareResult = SortUtil.compareNameAttribute().compare(INDO, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareNameAttribute_lessThanTest() { // test different names
        Record record = new RecordBuilder(CAIFAN).withName(("indo")).build();
        int compareResult = SortUtil.compareNameAttribute().compare(CAIFAN, record);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareNameAttribute_greaterThanTest() {
        Record record = new RecordBuilder(INDO).withName(("caifan")).build();
        int compareResult = SortUtil.compareNameAttribute().compare(INDO, record);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareDateAttribute_equalsTest() {
        Record record = new RecordBuilder(INDO).withDate((CAIFAN.getDate().value)).build();
        int compareResult = SortUtil.compareDateAttribute().compare(CAIFAN, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareDateAttribute_lessThanTest() { // test different names
        Record record = new RecordBuilder(INDO).withDate((CAIFAN.getDate().value)).build();
        int compareResult = SortUtil.compareDateAttribute().compare(INDO, record);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareDateAttribute_greaterThanTest() {
        Record record = new RecordBuilder(CAIFAN).withDate((INDO.getDate().value)).build();
        int compareResult = SortUtil.compareDateAttribute().compare(CAIFAN, record);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareMoneyflowAttribute_equalsTest() {
        Record record = new RecordBuilder(CAIFAN).withMoneyFlow((INDO.getMoneyFlow().value)).build();
        int compareResult = SortUtil.compareMoneyflowAttribute().compare(INDO, record);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareMoneyflowAttribute_lessThanTest() { // test different names
        Record record = new RecordBuilder(INDO).withMoneyFlow((CAIFAN.getMoneyFlow().value)).build();
        int compareResult = SortUtil.compareMoneyflowAttribute().compare(INDO, record);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareMoneyflowAttribute_greaterThanTest() {
        Record record = new RecordBuilder(CAIFAN).withMoneyFlow((INDO.getMoneyFlow().value)).build();
        int compareResult = SortUtil.compareMoneyflowAttribute().compare(CAIFAN, record);
        assertTrue(compareResult >= 1);
    }

}
