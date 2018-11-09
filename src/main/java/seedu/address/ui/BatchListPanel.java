package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InventoryPanelSelectionChangedEvent;
import seedu.address.model.drink.Batch;

/**
 * Panel containing the list of drinks.
 */
public class BatchListPanel extends UiPart<Region> {
    private static final String FXML = "BatchListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BatchListPanel.class);

    @FXML
    private ListView<Batch> batchListView;

    public BatchListPanel(ObservableList<Batch> batchList) {
        super(FXML);
        setConnections(batchList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Batch> batchList) {
        batchListView.setItems(batchList);
        batchListView.setCellFactory(listView -> new BatchListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        batchListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in drink list panel changed to : '" + newValue + "'");
                        //raise(new InventoryPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    @Subscribe
    private void handleInventoryPanelSelectionChangedEvent(InventoryPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        // insert what to do here
        batchListView.setItems(event.getNewSelection().getObservableBatchList());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Drink} using a {@code DrinkCard}.
     */
    class BatchListViewCell extends ListCell<Batch> {
        @Override
        protected void updateItem(Batch batch, boolean empty) {
            super.updateItem(batch, empty);

            if (empty || batch == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BatchCard(batch, getIndex() + 1).getRoot());
            }
        }
    }
}
