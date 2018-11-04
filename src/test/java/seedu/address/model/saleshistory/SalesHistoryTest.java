package seedu.address.model.saleshistory;

import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.testutil.Assert;

public class SalesHistoryTest {

    @Test
    public void getDaysTransactions() {

        SalesHistory salesHistory = new SalesHistory();

        // null date
        Assert.assertThrows(NullPointerException.class, () -> salesHistory.getDaysTransactions(null));

        // empty string date
        Assert.assertThrows(InvalidTimeFormatException.class, () -> salesHistory.getDaysTransactions(""));

        // not even a date
        Assert.assertThrows(InvalidTimeFormatException.class, () -> salesHistory.getDaysTransactions("lalala"));

        // invalid month
        Assert.assertThrows(InvalidTimeFormatException.class, () -> salesHistory.getDaysTransactions("2018/13/12"));

        // invalid day
        Assert.assertThrows(InvalidTimeFormatException.class, () -> salesHistory.getDaysTransactions("2018/12/35"));
    }

    @Test
    public void addTransaction_invalidTimeFormat() {
        SalesHistory salesHistory = new SalesHistory();
        Assert.assertThrows(
                InvalidTimeFormatException.class, () -> salesHistory.addTransaction(new InvalidTransactionTimeStub()));
    }

    @Test
    public void addTransaction_isValidTimeFormat() {
        SalesHistory salesHistory = new SalesHistory();
        try {
            salesHistory.addTransaction(new ValidTransactionTimeStub());
        } catch (Exception e) {
            fail("Valid transaction should be added with no exception");
        }
    }

    @Test
    public void addReminder_invalidTimeFormat() {
        SalesHistory salesHistory = new SalesHistory();
        Assert.assertThrows(
                InvalidTimeFormatException.class, () -> salesHistory.addReminder(new InvalidReminderTimeStub()));
    }

    @Test
    public void addReminder_validTimeFormat() {
        SalesHistory salesHistory = new SalesHistory();
        try {
            salesHistory.addReminder(new ValidReminderTimeStub());
        } catch (Exception e) {
            fail("Valid reminder should be added with no exception");
        }
    }

    //=================== Stubs =================================

    private class InvalidTransactionTimeStub extends Transaction {
        @Override
        public String getTransactionTime() {
            return "incorrect date";
        }
    }

    private class ValidTransactionTimeStub extends Transaction {
        @Override
        public String getTransactionTime() {
            return "2018/10/28 01:00:00";
        }
    }

    private class InvalidReminderTimeStub extends Reminder {
        @Override
        public String getReminderTime() {
            return "incorrect date";
        }
    }

    private class ValidReminderTimeStub extends Reminder {
        @Override
        public String getReminderTime() {
            return "2017/12/23 01:20:15";
        }
    }
}
