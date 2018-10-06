package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.item.Item;

/**
 * An UI component that displays information of a {@code Item}.
 */
public class ItemCard extends UiPart<Region> {

    private static final String FXML = "ItemListCard.fxml";
    private static final String QUANTITY_TEXT = "Quantity : ";
    private static final String MIN_QUANTITY_TEXT = "Minimum Quantity : ";
    private static final String STATUS_READY_TEXT = "Status-Ready : ";
    private static final String STATUS_ON_LOAN_TEXT = "Status-On_Loan : ";
    private static final String STATUS_FAULTY_TEXT = "Status-Faulty : ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on StockList level 4</a>
     */

    public final Item item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label quantity;
    @FXML
    private Label minQuantity;
    @FXML
    private Label statusReady;
    @FXML
    private Label statusOnLoan;
    @FXML
    private Label statusFaulty;
    @FXML
    private Label quantityText;
    @FXML
    private Label minQuantityText;
    @FXML
    private Label statusReadyText;
    @FXML
    private Label statusOnLoanText;
    @FXML
    private Label statusFaultyText;
    @FXML
    private FlowPane tags;

    public ItemCard(Item item, int displayedIndex) {
        super(FXML);
        this.item = item;
        id.setText(displayedIndex + ". ");
        name.setText(item.getName().fullName);
        quantity.setText(item.getQuantity().toString());
        minQuantity.setText(item.getMinQuantity().toString());
        statusReady.setText(item.getStatus().get(0).toString());
        statusOnLoan.setText(item.getStatus().get(1).toString());
        statusFaulty.setText(item.getStatus().get(2).toString());
        item.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        quantityText.setText(QUANTITY_TEXT);
        minQuantityText.setText(MIN_QUANTITY_TEXT);
        statusReadyText.setText(STATUS_READY_TEXT);
        statusOnLoanText.setText(STATUS_ON_LOAN_TEXT);
        statusFaultyText.setText(STATUS_FAULTY_TEXT);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemCard)) {
            return false;
        }

        // state check
        ItemCard card = (ItemCard) other;
        return id.getText().equals(card.id.getText())
                && item.equals(card.item);
    }
}
