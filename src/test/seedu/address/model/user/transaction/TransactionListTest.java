package seedu.address.model.user.transaction;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.transaction.TypicalTransactions.getTypicalTransactionList;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.transaction.TransactionList;

public class TransactionListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TransactionList transactionList = new TransactionList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), transactionList.getTransactions());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        transactionList.resetData(null);
    }

    @Test
    public void resetData_withValidTransactionList_replacesData() {
        TransactionList newData = getTypicalTransactionList();
        transactionList.resetData(newData);
        assertEquals(newData, transactionList);
    }

}
