package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.drink.Drink;

/**
 * Pane containing information about Drink
 */
public class DrinkDetailPane extends UiPart<Region> {
    private static final String FXML = "DrinkDetailPane.fxml";
    private static final String NO_DRINK_MSG = "No Drink Selected";
    public final Drink drink;

    @javafx.fxml.FXML
    private Label name;

    @FXML
    private Label sellingPrice;

    @FXML
    private Label quantity;

    @FXML
    private Label costPrice;

    @FXML
    private Label batchesInStock;

    @FXML
    private Label earliestBatchDate;

    @FXML
    private Label latestBatchDate;

    public DrinkDetailPane(Drink input) {
        super(FXML);
        this.drink = input;
        if (drink == null) {
        } else {
            name.setText(drink.getName().toString());
            sellingPrice.setText("$ " + drink.getRetailPrice().toString());
            quantity.setText(Integer.toString(drink.getQuantity().getValue()));
            costPrice.setText("$ " + drink.getCostPrice().toString());
            /*
            try {
                earliestBatchDate.setText(drink.getEarliestBatchDate().toString());
                latestBatchDate.setText(drink.getLatestBatchDate().toString());
            } catch (EmptyBatchListException e) {
                earliestBatchDate.setText("No available batches");
                latestBatchDate.setText("No available batches");
            }
            */
        }

    }
}
