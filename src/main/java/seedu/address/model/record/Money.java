package seedu.address.model.record;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/*
* This class represents the money itself. Mainly used for the limitCommand.
* */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS =
            "Money Parameter only include the number of the money, which is a double value. ";

    public static final String MONEY_VALIDATION_REGEX ="\\p{Digit}+";

    public String moneyValue;

    public double money;

    public Money (String moneyIn){
        moneyValue=moneyIn;
        money=parseDouble(moneyIn);
    }

    public double getMoney () {return money;}

    @Override
    public String toString (){return moneyValue;}

    public static boolean isValidMoneyName(String test) {
        return test.matches(MONEY_VALIDATION_REGEX);
    }

    public boolean isLarger (double moneyIn){ if (moneyIn>money) return true; else return false;}

    public boolean isSmaller (double moneyIn){ if (moneyIn<money) return true; else return false;}

    public boolean isEqual (double moneyIn){ if (moneyIn == money) return true; else return false;}
}
