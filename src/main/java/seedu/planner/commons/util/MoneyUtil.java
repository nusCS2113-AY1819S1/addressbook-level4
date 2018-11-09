package seedu.planner.commons.util;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.model.record.MoneyFlow.FORMAT_STANDARD_MONEY;
import static seedu.planner.model.record.MoneyFlow.REPRESENTATION_ZERO;

import seedu.planner.model.record.MoneyFlow;
//@@author tenvinc
/**
 * Contains helper methods to process data of {@code MoneyFlow}
 */
public class MoneyUtil {

    /**
     * Takes in 2 {@code MoneyFlow} parameters and returns the sum
     */
    public static MoneyFlow add(MoneyFlow money1, MoneyFlow money2) {
        requireAllNonNull(money1, money2);
        Double newMoney = money1.toDouble() + money2.toDouble();
        return new MoneyFlow(formatIntoMoneyFlowFormat(newMoney));
    }

    /**
     * Formats a string into a string that is readable by {@code MoneyFlow}
     */
    private static String formatIntoMoneyFlowFormat(Double money) {
        String formattedMoney;
        if (money == 0) {
            formattedMoney = REPRESENTATION_ZERO;
        } else if (money > 0) {
            formattedMoney = String.format("+" + FORMAT_STANDARD_MONEY, money);
        } else {
            formattedMoney = String.format(FORMAT_STANDARD_MONEY, money);
        }
        return formattedMoney;
    }
}
