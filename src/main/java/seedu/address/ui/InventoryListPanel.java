package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InventoryPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.drink.Drink;

/**
 * Panel containing the list of drinks.
 */
public class InventoryListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InventoryListPanel.class);

    @javafx.fxml.FXML
    private ListView<Drink> drinkListView;

    public InventoryListPanel(ObservableList<Drink> inventoryList) {
        super(FXML);
        setConnections(inventoryList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Drink> inventoryList) {
        drinkListView.setItems(inventoryList);
        drinkListView.setCellFactory(listView -> new InventoryListPanel.InventoryListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        drinkListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in inventory list panel changed to : '" + newValue + "'");
                        raise(new InventoryPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code DrinkCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            drinkListView.scrollTo(index);
            drinkListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Drink} using a {@code DrinkCard}.
     */
    class InventoryListViewCell extends ListCell<Drink> {
        @Override
        protected void updateItem(Drink drink, boolean empty) {
            super.updateItem(drink, empty);

            if (empty || drink == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DrinkCard(drink, getIndex() + 1).getRoot());
            }
        }
    }
}