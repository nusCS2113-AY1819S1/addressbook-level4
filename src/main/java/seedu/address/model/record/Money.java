package seedu.address.model.record;

/*
* This class represents the money itself. Mainly used for the limitCommand.
* */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS =
            "Money Parameter only include the number of the money, which is a double value. ";
    public double Money;

    public double getMoney () {return Money;}

    public boolean isLarger (double Moneyin){ if (Moneyin>Money) return true; else return false;}

    public boolean isSmaller (double Moneyin){ if (Moneyin<Money) return true; else return false;}

    public boolean isEqual (double Moneyin){ if (Moneyin == Money) return true; else return false;}
}
