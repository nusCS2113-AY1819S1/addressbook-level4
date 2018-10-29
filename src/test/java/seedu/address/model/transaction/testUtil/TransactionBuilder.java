package seedu.address.model.transaction.testUtil;

import seedu.address.model.drink.Date;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;
import seedu.address.model.transaction.TransactionType;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {
    public static final String DEFAULT_NAME = "Coca Cola";
    public static final String DEFAULT_TRANSACTION_TYPE = "SALE";
    public static final String DEFAULT_DATE = new Date().toString();
    public static final String DEFAULT_QUANTITY = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_AMOUNT_EARNED = "";

    private TransactionType transactionType;
    private Date transactionDate;
    private Drink drinkTransacted;
    private Quantity quantityTransacted;
    private Price amountMoney;


    public TransactionBuilder() {

    }








}
