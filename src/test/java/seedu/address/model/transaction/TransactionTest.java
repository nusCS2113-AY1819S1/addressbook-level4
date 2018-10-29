package seedu.address.model.transaction;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.testutil.Assert.assertThrows;
import static seedu.address.model.transaction.TransactionType.IMPORT;
import static seedu.address.model.transaction.TransactionType.SALE;
import static seedu.address.model.transaction.testUtil.TypicalTransactions.SALE_COKE_1;
import static seedu.address.model.transaction.testUtil.TypicalTransactions.SALE_COKE_2;
import static seedu.address.model.transaction.testUtil.TypicalTransactions.SALE_PEPSI;

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


    @Test
    public void equals() {
        // same values, same id -> return true
        assertEquals(SALE_COKE_1, SALE_COKE_1);

        // same values , but different id based on time -> return false
        assertNotEquals(SALE_COKE_1, SALE_COKE_2);

        // different drink -> return false
        assertNotEquals(SALE_COKE_1, SALE_PEPSI);
    }


}
