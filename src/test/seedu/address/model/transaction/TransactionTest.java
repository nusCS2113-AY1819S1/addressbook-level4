package model.transaction;

import static logic.CommandTestUtil.VALID_DRINK_QUANTITY;
import static logic.CommandTestUtil.VALID_TRANSACTION_AMOUNT_MONEY;
import static logic.CommandTestUtil.VALID_TRANSACTION_QUANTITY;
import static model.transaction.testutil.TypicalTransactions.SALE_COKE_1;
import static model.transaction.testutil.TypicalTransactions.SALE_COKE_2;
import static model.transaction.testutil.TypicalTransactions.SALE_PEPSI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.model.transaction.TransactionType.PURCHASE;
import static seedu.address.model.transaction.TransactionType.SALE;
import static testutil.Assert.assertThrows;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.testutil.DrinkBuilder;
import model.transaction.testutil.TransactionBuilder;

import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;
import seedu.address.model.transaction.Transaction;

public class TransactionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Drink drink = new DrinkBuilder ().build();

        // transaction type null
        assertThrows(NullPointerException.class, () -> new Transaction (null,
                drink, new Quantity(VALID_TRANSACTION_QUANTITY), new Price(VALID_TRANSACTION_AMOUNT_MONEY)));

        // drink null, with transaction type sale
        assertThrows(NullPointerException.class, () -> new Transaction(SALE,
                null, new Quantity(VALID_DRINK_QUANTITY), new Price(VALID_TRANSACTION_AMOUNT_MONEY)));

        // drink null, with transaction type purchase
        assertThrows(NullPointerException.class, () -> new Transaction(PURCHASE,
                null, new Quantity(VALID_DRINK_QUANTITY), new Price(VALID_TRANSACTION_AMOUNT_MONEY)));

        // quantity null
        assertThrows(NullPointerException.class, () -> new Transaction(SALE,
                drink, null, new Price(VALID_TRANSACTION_AMOUNT_MONEY)));

        // amount money null
        assertThrows(NullPointerException.class, () -> new Transaction(SALE,
                drink, new Quantity(VALID_DRINK_QUANTITY), null));
    }


    @Test
    public void equals() {
        // same values, same id -> return true
        assertEquals(SALE_COKE_1, SALE_COKE_1);

        // same values , but different id based on time -> return false
        Transaction transaction = new TransactionBuilder (SALE_COKE_2).build();
        assertNotEquals(SALE_COKE_1, transaction);

        // different drink -> return false
        assertNotEquals(SALE_COKE_1, SALE_PEPSI);
    }


}
