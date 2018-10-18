package seedu.planner.commons.util;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import seedu.planner.model.record.MoneyFlow;

/**
 * Contains helper methods to process data of {@code MoneyFlow}
 */
public class MoneyUtil {

    /**
     * Takes in 2 {@code MoneyFlow} parameters and returns the sum
     */
    public static final MoneyFlow add(MoneyFlow money1, MoneyFlow money2) {
        requireAllNonNull(money1, money2);
        double newMoney = money1.toDouble() + money2.toDouble();
        return new MoneyFlow(formatIntoMoneyFlowFormat(newMoney));
    }

    /**
     * Subtracts {@code MoneyFlow} money2 from {@code MoneyFlow} money1 and returns the result
     */
    public static final MoneyFlow subtract(MoneyFlow money1, MoneyFlow money2) {
        requireAllNonNull(money1, money2);
        double newMoney = money1.toDouble() - money2.toDouble();
        return new MoneyFlow(formatIntoMoneyFlowFormat(newMoney));
    }

    /**
     * Formats a string into a string that is readable by {@code MoneyFlow}
     */
    //TODO: implement an alternative representation for 0 in MoneyFlow parser
    private static final String formatIntoMoneyFlowFormat(double money) {
        String formattedMoney;
        if (money == 0) {
            formattedMoney = String.format("-0");
        } else if (money > 0) {
            formattedMoney = String.format("+%.2f", money);
        } else {
            formattedMoney = String.format("%.2f", money);
        }
        return formattedMoney;
    }
}
