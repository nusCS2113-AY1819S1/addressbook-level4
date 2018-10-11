package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.request.Request;

/**
 * An UI component that displays information of a {@code Book}.
 */
public class RequestCard extends UiPart<Region> {

    private static final String FXML = "RequestCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on BookInventory level 4</a>
     */

    public final Request request;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label isbn;
    @FXML
    private Label quantity;
    @FXML
    private Label email;
    @FXML
    private Label name;
    @FXML
    private FlowPane tags;
    public RequestCard(Request request, int displayedIndex) {
        super(FXML);
        this.request = request;
        //id.setText(displayedIndex + ". ");
        name.setText("Request No. " + displayedIndex);
        isbn.setText("Isbn : " + request.getIsbn().value);
        quantity.setText("Number in demand: " + request.getQuantity().getValue());
        email.setText("Student's Email: " + request.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RequestCard)) {
            return false;
        }

        // state check
        RequestCard card = (RequestCard) other;
        return id.getText().equals(card.id.getText())
                && request.equals(card.request);
    }
}
