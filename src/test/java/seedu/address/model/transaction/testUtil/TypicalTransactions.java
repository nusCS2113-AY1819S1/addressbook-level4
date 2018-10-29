package seedu.address.model.transaction.testUtil;

import seedu.address.model.transaction.Transaction;

/**
 * A utility class containing {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {
    public static final Transaction SALE_COKE_1 = new TransactionBuilder().withTransactionType("sale")
            .withDate("11/08/2018").withDrink("Coke Zero").withQuantity("19").withAmountMoney("40.05").build();
    public static final Transaction SALE_COKE_2 = new TransactionBuilder().withTransactionType("sale")
            .withDate("11/08/2018").withDrink("Coke Zero").withQuantity("19").withAmountMoney("40.05").build();
    public static final Transaction SALE_PEPSI = new TransactionBuilder().withTransactionType("sale")
            .withDate("11/08/2018").withDrink("Pepsi woohoo").withQuantity("19").withAmountMoney("40.05").build();
}
