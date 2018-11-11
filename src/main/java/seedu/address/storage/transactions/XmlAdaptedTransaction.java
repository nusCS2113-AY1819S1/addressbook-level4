package seedu.address.storage.transactions;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.drink.Date;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionId;
import seedu.address.model.transaction.TransactionType;

/**
 * JAXB-friendly version of the Transaction.
 */
public class XmlAdaptedTransaction {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    @XmlElement(required = true)
    private String transactionType;
    @XmlElement(required = true)
    private String transactionDate;
    @XmlElement(required = true)
    private String drinkTransacted;
    @XmlElement(required = true)
    private String quantityTransacted;
    @XmlElement(required = true)
    private String amountMoney;
    @XmlElement(required = true)
    private String id;

    /**
     * Constructs an XmlAdaptedTransaction.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTransaction() {
    }

    /**
     * Constructs an {@code XmlAdaptedTransaction} with the given drink details.
     */
    public XmlAdaptedTransaction(String transactionType, String transactionDate, String drinkTransacted,
                                 String quantityTransacted, String amountMoney, String id) {
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.drinkTransacted = drinkTransacted;
        this.quantityTransacted = quantityTransacted;
        this.amountMoney = amountMoney;
        this.id = id;
    }

    /**
     * Converts a given Transaction into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTransaction
     */
    public XmlAdaptedTransaction(Transaction source) {
        transactionType = source.getTransactionType().toString();
        transactionDate = source.getTransactionDate().toString();
        drinkTransacted = source.getDrinkTransacted().getName().toString();
        quantityTransacted = source.getQuantityTransacted().toString();
        amountMoney = source.getAmountMoney().toString();
        id = source.getTransactionId().toString();
    }


    /**
     * Converts this jaxb-friendly adapted person object into the model's Transaction object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction
     */
    public Transaction toModelType() throws IllegalValueException {
        if (transactionType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionType.class.getSimpleName()));
        }

        final TransactionType modelTransactionType = TransactionType.valueOf(transactionType);


        if (transactionDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(transactionDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelTransactionDate = new Date(transactionDate);


        if (drinkTransacted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Drink.class.getSimpleName()));
        }
        if (!Name.isValidName(drinkTransacted)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Drink modelDrinkTransacted = new Drink(new Name(drinkTransacted));


        if (quantityTransacted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantityTransacted)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelQuantityTransactied = new Quantity(quantityTransacted);


        if (amountMoney == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(amountMoney)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelAmountMoney = new Price(amountMoney);


        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionId.class.getSimpleName()));
        }
        if (!TransactionId.isValidTransactionId(id)) {
            throw new IllegalValueException(TransactionId.MESSAGE_TRANSACTION_ID_CONSTRAINTS);
        }
        final TransactionId modelTransactionId = new TransactionId(id);

        return new Transaction(modelTransactionType, modelTransactionDate, modelDrinkTransacted,
                modelQuantityTransactied, modelAmountMoney, modelTransactionId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTransaction)) {
            return false;
        }

        XmlAdaptedTransaction otherTrans = (XmlAdaptedTransaction) other;
        return Objects.equals(transactionType, otherTrans.transactionType)
                && Objects.equals(transactionDate, otherTrans.transactionDate)
                && Objects.equals(drinkTransacted, otherTrans.drinkTransacted)
                && Objects.equals(quantityTransacted, otherTrans.quantityTransacted)
                && Objects.equals(amountMoney, otherTrans.amountMoney)
                && Objects.equals(id, otherTrans.id);
    }
}
