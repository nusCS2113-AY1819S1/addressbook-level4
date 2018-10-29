package seedu.address.model.transaction;

import static org.junit.Assert.assertFalse;
import static seedu.address.model.testutil.Assert.assertThrows;
import static seedu.address.model.transaction.TransactionType.IMPORT;
import static seedu.address.model.transaction.TransactionType.SALE;
import static seedu.address.model.transaction.testUtil.TypicalTransactions.SALE_COKE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;

public class TransactionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Transaction(null,
                null, null, null));

        assertThrows(NullPointerException.class, () -> new Transaction(SALE,
                null, null, null));

        assertThrows(NullPointerException.class, () -> new Transaction(IMPORT,
                null, null, null));

        assertThrows(NullPointerException.class, () -> new Transaction(null,
                null, null, new Price("20.00")));

        assertThrows(NullPointerException.class, () -> new Transaction(null,
                null, new Quantity("20"), null));

        assertThrows(NullPointerException.class, () -> new Transaction(null,
                new Drink(new Name("bla")), null, null));
    }

    // TODO: constructor
//    @Test
//    public void constructor_invalidInput_throwIllegalArgumentException() {
//        assertThrows(IllegalArgumentException.class, new Transaction(TransactionType.valueOf("bla"),
//                new Drink(new Name(VALID_DRINK_NAME)), new Quantity(VALID_DRINK_QUANTITY),
//                new Price(VALID_TRANSACTION_AMOUNT_MONEY)));
//}

    @Test
    public void equals() {
        // same values -> return false
        Transaction transactionA = SALE_COKE;
        assertFalse(transactionA.equals(SALE_COKE));

    }


}
