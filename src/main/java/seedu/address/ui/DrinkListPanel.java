package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
public class DrinkListPanel extends UiPart<Region> {
    private static final String FXML = "DrinkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DrinkListPanel.class);

    @FXML
    private ListView<Drink> drinkListView;

    public DrinkListPanel(ObservableList<Drink> drinkList) {
        super(FXML);
        setConnections(drinkList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Drink> drinkList) {
        drinkListView.setItems(drinkList);
        drinkListView.setCellFactory(listView -> new DrinkListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        drinkListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in drink list panel changed to : '" + newValue + "'");
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
    class DrinkListViewCell extends ListCell<Drink> {
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
