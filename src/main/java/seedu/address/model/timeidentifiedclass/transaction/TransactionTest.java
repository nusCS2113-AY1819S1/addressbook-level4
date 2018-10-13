package seedu.address.model.timeidentifiedclass.transaction;

import seedu.address.model.timeidentifiedclass.transaction.exceptions.ClosedTransactionException;

/**
 *  This program checks if Transaction objects behave as they should. If all is well, it should output the current time
 *  as output on the terminal.
 */
public class TransactionTest {
	public static void main(String[] args) {
		Transaction transaction = new Transaction();
		System.out.println(transaction.getTime());
		try {
			for (char i = 'a'; i < 'a' + 5; i++) {
				transaction.addProduct("" + i);
				transaction.addProduct("" + i);
			}
			transaction.closeTransaction();
			transaction.addProduct("should not add");
		} catch (ClosedTransactionException c) {
			System.out.println("Successfully caught ClosedTransactionException");
		}
		System.out.println(transaction.getTransactionRecord());
		System.out.println("Expected output consists of a,b,c,d,e, all of quantity 2.");
	}
}
