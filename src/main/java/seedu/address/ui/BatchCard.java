package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.drink.Batch;

/**
 * An UI component that displays information of a {@code Drink}.
 */
public class BatchCard extends UiPart<Region> {
    private static final String FXML = "BatchListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Batch batch;

    @FXML
    private HBox cardPane;
    @FXML
    private Label batchId;
    @FXML
    private Label id;
    @FXML
    private Label batchQuantity;
    @FXML
    private Label batchDate;

    public BatchCard(Batch batch, int displayedIndex) {
        super(FXML);
        this.batch = batch;
        id.setText(displayedIndex + ". ");
        batchId.setText(batch.getBatchId().toString());
        batchQuantity.setText("Quantity in batch: " + Integer.toString(batch.getBatchQuantity().getValue()));
        batchDate.setText("Batch Date: " + batch.getBatchDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BatchCard)) {
            return false;
        }

        // state check
        BatchCard card = (BatchCard) other;
        return id.getText().equals(card.id.getText())
                && batch.equals(card.batch);
    }
}
