package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.drink.Drink;

/**
 * An UI component that displays information of a {@code Drink}.
 */
public class DrinkCard extends UiPart<Region> {
    private static final String FXML = "DrinkListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Drink drink;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label stock;
    @FXML
    private FlowPane tags;
    @FXML
    private Label costPrice;
    @FXML
    private Label retailPrice;

    public DrinkCard(Drink drink, int displayedIndex) {
        super(FXML);
        this.drink = drink;
        id.setText(displayedIndex + ". ");
        name.setText(drink.getName().name);
        stock.setText(Integer.toString(drink.getQuantity().getValue()));
        costPrice.setText("$" + drink.getCostPrice().toString());
        retailPrice.setText("$" + drink.getRetailPrice().toString());
        drink.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        DrinkCard card = (DrinkCard) other;
        return id.getText().equals(card.id.getText())
                && drink.equals(card.drink);
    }
}
