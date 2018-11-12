package seedu.planner.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.planner.testutil.Assert;
//@@author tenvinc
public class MoneyFlowTest {

    @Test
    public void isValidMoneyFlow() {
        // null money flow
        Assert.assertThrows(NullPointerException.class, () -> MoneyFlow.isValidMoneyFlow(null));

        // invalid money flow
        assertFalse(MoneyFlow.isValidMoneyFlow("")); // empty string
        assertFalse(MoneyFlow.isValidMoneyFlow(" ")); // spaces only
        assertFalse(MoneyFlow.isValidMoneyFlow("Blk 456, Den Road, #01-355")); // letters
        assertFalse(MoneyFlow.isValidMoneyFlow("**")); // Symbols which are not '-', '+', '.'
        assertFalse(MoneyFlow.isValidMoneyFlow("+-123.0")); // Can only have 1 '+' or '-'
        assertFalse(MoneyFlow.isValidMoneyFlow("+")); // Must have a number after the sign
        assertFalse(MoneyFlow.isValidMoneyFlow("+123.442.456")); // multiple decimal points
        assertFalse(MoneyFlow.isValidMoneyFlow("-01.23")); // whole number can only start with 0 if it is 1 digit
        assertFalse(MoneyFlow.isValidMoneyFlow("-1.")); // there must be at least 1 digit after the decimal point
        assertFalse(MoneyFlow.isValidMoneyFlow("-.1")); // there must be at least 1 digit before decimal point
        assertFalse(MoneyFlow.isValidMoneyFlow("-123.0011")); // moneyflow with decimal places greater than 2
        assertFalse(MoneyFlow.isValidMoneyFlow("-9999999999999.99")); // moneyflow with whole number part as 13 digits

        // valid money flow
        assertTrue(MoneyFlow.isValidMoneyFlow("-0")); // 0 is valid
        assertTrue(MoneyFlow.isValidMoneyFlow("-123456789.00")); // long moneyflow
        assertTrue(MoneyFlow.isValidMoneyFlow("+12.30")); // standard moneyflow
        assertTrue(MoneyFlow.isValidMoneyFlow("+12")); // no need for the decimal point
        assertTrue(MoneyFlow.isValidMoneyFlow("-999999999999")); // moneyflow with 12 digit whole number
    }
}
