package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.ClosedTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;


/**
 * This class adapts Transaction for JAXB.
 */

public class XmlAdaptedTransaction {

    private static final String INCORRECT_TRANSACTION_TIME_MESSAGE_FORMAT = "Incorrect transaction time of %s found!";
    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";
    private static final String PRODUCT_QUANTITY_MISMATCH_MESSAGE = "Transaction %s "
            + "has unequal product and quantity list sizes!\n"
            + "Product list size: %s"
            + "Quantity list size: %s";
    private static final String TRANSACTION_CLOSED_ERROR_MESSAGE = "Transaction %s was set as closed!";

    @XmlElement(required = true)
    private String transactionTime;
    @XmlElement(required = true)
    private List<String> productNames;
    @XmlElement(required = true)
    private List<String> productQuantities;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTransaction() {}

    /**
     * Constructs an {@code XmlAdaptedReminder} with the required transaction details.
     * @param transactionTime
     * @param productNames
     * @param productQuantities
     */

    public XmlAdaptedTransaction(String transactionTime, List<String> productNames, List<String> productQuantities) {
        this.transactionTime = transactionTime;
        this.productNames = productNames;
        this.productQuantities = productQuantities;
    }

    /**
     * Converts a given transaction into this class for JAXB use.
     *
     * @param transaction
     */

    public XmlAdaptedTransaction(Transaction transaction) {
        transactionTime = transaction.getTransactionTime();
        productNames = new ArrayList<>();
        productQuantities = new ArrayList<>();

        Set transactionSet = transaction.getTransactionRecord().entrySet();
        Iterator it = transactionSet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String productName = (String) entry.getKey();
            if (productName != null && !productName.equals("")) {
                productNames.add((String) entry.getKey());
                productQuantities.add(entry.getValue().toString());
            }
        }
    }

    /**
     * This method converts the XmlAdaptedReminder into a Transacation object, and returns it.
     * @return transaction
     * @throws IllegalValueException
     */

    public Transaction toModelType() throws IllegalValueException {

        if (transactionTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "transaction time"));
        }

        if (productNames == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "product names"));
        }

        if (productQuantities == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "product quantities"));
        }

        if (productNames.size() != productQuantities.size()) {
            throw new IllegalValueException(String.format(PRODUCT_QUANTITY_MISMATCH_MESSAGE,
                    transactionTime, productNames.size(), productQuantities.size()));
        }

        Transaction transaction;

        try {
            transaction = new Transaction(transactionTime);
            transaction.openTransaction();

            for (int i = 0; i < productNames.size(); i++) {
                transaction.addProduct(productNames.get(i), Integer.parseInt(productQuantities.get(i)));
            }
        } catch (InvalidTimeFormatException e) {
            throw new IllegalValueException(String.format(INCORRECT_TRANSACTION_TIME_MESSAGE_FORMAT,
                    transactionTime));
        } catch (ClosedTransactionException e) {
            throw new IllegalValueException(String.format(TRANSACTION_CLOSED_ERROR_MESSAGE, transactionTime));
        }
        return transaction;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReminder)) {
            return false;
        }

        XmlAdaptedTransaction otherTransaction = (XmlAdaptedTransaction) other;
        return Objects.equals(productNames, otherTransaction.productNames)
                && Objects.equals(productQuantities, otherTransaction.productQuantities)
                && Objects.equals(transactionTime, otherTransaction.transactionTime);
    }

}
