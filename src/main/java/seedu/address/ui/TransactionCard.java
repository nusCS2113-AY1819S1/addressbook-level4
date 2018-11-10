package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {
    private static final String FXML = "TransactionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label transType;
    @FXML
    private Label id;
    @FXML
    private Label transQuantity;
    @FXML
    private Label transDate;
    @FXML
    private Label transAmount;

    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        name.setText(transaction.getDrinkTransacted().getName().toString());
        transType.setText(transaction.getTransactionType().getValue());
        if (transaction.getTransactionType() == TransactionType.SALE) {
            transType.setStyle("-fx-background-color:LIGHTSEAGREEN");
        }
        transQuantity.setText("Quantity transacted: " + transaction.getQuantityTransacted());
        transAmount.setText("Amount transacted: $" + transaction.getAmountMoney());
        transDate.setText("Date of transaction: " + transaction.getTransactionDate());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }

        // state check
        TransactionCard card = (TransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}
