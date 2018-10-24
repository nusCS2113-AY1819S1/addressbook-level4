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
import seedu.address.commons.events.ui.DistributorPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.distributor.Distributor;

/**
 * Panel containing the list of products.
 */
public class DistributorListPanel extends UiPart<Region> {
    private static final String FXML = "DistributorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DistributorListPanel.class);

    @FXML
    private ListView<Distributor> distributorListView;

    public DistributorListPanel(ObservableList<Distributor> distributorList) {
        super(FXML);
        setConnections(distributorList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Distributor> distributorList) {
        distributorListView.setItems(distributorList);
        distributorListView.setCellFactory(listView -> new DistributorListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        distributorListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in distributor list panel changed to : '" + newValue + "'");
                        raise(new DistributorPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code DistributorCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            distributorListView.scrollTo(index);
            distributorListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Distributor} using a {@code DistributorCard}.
     */
    class DistributorListViewCell extends ListCell<Distributor> {
        @Override
        protected void updateItem(Distributor distributor, boolean empty) {
            super.updateItem(distributor, empty);

            if (empty || distributor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DistributorCard(distributor, getIndex() + 1).getRoot());
            }
        }
    }
}
