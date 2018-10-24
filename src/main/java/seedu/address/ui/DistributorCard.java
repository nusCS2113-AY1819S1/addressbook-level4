package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.distributor.Distributor;

/**
 * An UI component that displays information of a {@code Product}.
 */
public class DistributorCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ProductDatabase level 4</a>
     */

    public final Distributor distributor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;

    public DistributorCard(Distributor distributor, int displayedIndex) {
        super(FXML);
        this.distributor = distributor;
        id.setText(displayedIndex + "." + "\n");
        name.setText("Name: " + distributor.getDistName().fullDistName);
        phone.setText("Serial Number: " + distributor.getDistPhone().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DistributorCard)) {
            return false;
        }

        // state check
        DistributorCard card = (DistributorCard) other;
        return id.getText().equals(card.id.getText())
                && distributor.equals(card.distributor);
    }
}
