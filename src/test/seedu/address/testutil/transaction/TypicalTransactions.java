package seedu.address.testutil.transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionList;

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

    public static final Transaction BUY_PEPSI = new TransactionBuilder().withTransactionType("purchase")
            .withDate("11/08/2018").withDrink("Pepsi woohoo").withQuantity("19").withAmountMoney("40.05").build();


    private TypicalTransactions() {
    } // prevents instantiation

    /**
     * Returns a {@code TransactionList} with all the typical transactions.
     */
    public static TransactionList getTypicalTransactionList() {
        TransactionList list = new TransactionList();
        for (Transaction transaction : getTypicalTransactions()) {
            list.addTransaction(transaction);
        }
        return list;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(SALE_COKE_1, BUY_PEPSI));
    }
}
