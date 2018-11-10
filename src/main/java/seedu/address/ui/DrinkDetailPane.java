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

    /**
     * Constructs a drink detail pane with the input drink
     * For drinks with no batches, "No batches available" will be printed
     * @param input a Drink object
     */
    public DrinkDetailPane(Drink input) {
        super(FXML);
        this.drink = input;
        if (drink != null && drink.getName() != null) {
            name.setText(drink.getName().toString());
            sellingPrice.setText("$ " + drink.getRetailPrice().toString());
            quantity.setText(Integer.toString(drink.getQuantity().getValue()));
            costPrice.setText("$ " + drink.getCostPrice().toString());
            batchesInStock.setText(Integer.toString(drink.getNumberBatches()));
            if (drink.getNumberBatches() == 0) {
                earliestBatchDate.setText("No batches available");
                latestBatchDate.setText("No batches available");
            } else {
                earliestBatchDate.setText(drink.getEarliestBatchDate().toString());
                latestBatchDate.setText(drink.getLatestBatchDate().toString());
            }
        } else {
            name.setText("Please select a drink");
            sellingPrice.setText("");
            quantity.setText("");
            costPrice.setText("");
            batchesInStock.setText("");
            earliestBatchDate.setText("");
            latestBatchDate.setText("");
        }
    }
}
