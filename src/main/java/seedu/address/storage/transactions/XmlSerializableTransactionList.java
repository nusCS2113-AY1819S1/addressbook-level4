package seedu.address.storage.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.ReadOnlyTransactionList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionList;

/**
 * An Immutable Transaction List that is serializable to XML format
 */
@XmlRootElement(name = "transactionlist")
public class XmlSerializableTransactionList {

    @XmlElement
    private List<XmlAdaptedTransaction> transactions;

    /**
     * Creates an empty XmlSerializableTransactionList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTransactionList() {
        transactions = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTransactionList(ReadOnlyTransactionList src) {
        this();
        transactions.addAll(src.getTransactionList().stream().map(XmlAdaptedTransaction::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this transactionlist into the model's {@code TransactionList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in {@code XmlAdaptedTransaction}.
     */
    public TransactionList toModelType() throws IllegalValueException {
        TransactionList transactionList = new TransactionList();
        for (XmlAdaptedTransaction t : transactions) {
            Transaction transaction = t.toModelType();

            transactionList.addTransaction(transaction);
        }
        return transactionList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTransactionList)) {
            return false;
        }
        return transactions.equals(((XmlSerializableTransactionList) other).transactions);
    }
}
