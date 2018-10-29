package seedu.address.model.transaction.testUtil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_NAME;

import seedu.address.model.drink.Date;
import seedu.address.model.drink.Name;
import seedu.address.model.transaction.Transaction;

public class TypicalTransactions {
    public static final Transaction SALE_COKE = new TransactionBuilder().withTransactionType("sale")
            .withDate("11/08/2018").withDrink("Coke Zero").withQuantity("19").withAmountMoney("40.05").build();
}
