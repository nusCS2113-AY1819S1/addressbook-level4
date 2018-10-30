package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static seedu.planner.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;

public class CategoryStatisticTest {

    @Test
    public void constructor_recordWithZeroMoney_throwsException() {
        final String zeroMoneyflow = "-0";
        Record recordWithZeroMoney = new RecordBuilder().withMoneyFlow(zeroMoneyflow).build();
        assertThrows(IllegalStateException.class, () -> new CategoryStatistic(recordWithZeroMoney));
    }

    @Test
    public void constructor_recordWithPositiveMoney_initTotalIncome() {
        final String positiveMoneyString = "+100";
        final Double expectedIncome = 100.0;
        final Double expectedExpense = 0.0;
        Record recordWithPositiveMoney = new RecordBuilder().withMoneyFlow(positiveMoneyString).build();
        CategoryStatistic toTest = new CategoryStatistic(recordWithPositiveMoney);
        assertEquals(expectedIncome, toTest.getTotalIncome());
        assertEquals(expectedExpense, toTest.getTotalExpense());
    }

    @Test
    public void constructor_recordWithNegativeMoney_initTotalExpense() {
        final String negativeMoneyString = "-59";
        final Double expectedIncome = 0.0;
        final Double expectedExpense = 59.0;
        Record recordWithPositiveMoney = new RecordBuilder().withMoneyFlow(negativeMoneyString).build();
        CategoryStatistic toTest = new CategoryStatistic(recordWithPositiveMoney);
        assertEquals(expectedExpense, toTest.getTotalExpense());
        assertEquals(expectedIncome, toTest.getTotalIncome());
    }

    @Test
    public void add_recordWithPositiveMoney_addToTotalIncome() {
        final String positiveMoneyString = "+100";
        final String startingMoney = "-50";
        final Double expectedExpense = 50.0;
        final Double expectedIncome = 100.0;
        Record recordWithStartingIncome = new RecordBuilder().withMoneyFlow(startingMoney).build();
        Record recordWithPositiveMoneyString = new RecordBuilder().withMoneyFlow((positiveMoneyString)).build();
        CategoryStatistic toTest = new CategoryStatistic(recordWithStartingIncome);
        toTest.add(recordWithPositiveMoneyString);
        assertEquals(expectedExpense, toTest.getTotalExpense());
        assertEquals(expectedIncome, toTest.getTotalIncome());
    }

    @Test
    public void add_recordWithNegativeMoney_addTotalExpense() {
        final String negativeMoneyString = "-110";
        final String startingMoney = "+50";
        final Double expectedExpense = 110.0;
        final Double expectedIncome = 50.0;
        Record recordWithStartingIncome = new RecordBuilder().withMoneyFlow(startingMoney).build();
        Record recordWithPositiveMoneyString = new RecordBuilder().withMoneyFlow((negativeMoneyString)).build();
        CategoryStatistic toTest = new CategoryStatistic(recordWithStartingIncome);
        toTest.add(recordWithPositiveMoneyString);
        assertEquals(expectedExpense, toTest.getTotalExpense());
        assertEquals(expectedIncome, toTest.getTotalIncome());
    }
}
