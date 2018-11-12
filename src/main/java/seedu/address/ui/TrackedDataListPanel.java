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
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.TrackedDataPanelSelectionChangedEvent;
import seedu.address.model.workout.Parameter;

/**
 * Panel containing the list of parameters being tracked.
 */
public class TrackedDataListPanel extends UiPart<Region> {
    private static final String FXML = "TrackedDataListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TrackedDataListPanel.class);

    @FXML
    private ListView<Parameter> trackedDataListView;

    public TrackedDataListPanel(ObservableList<Parameter> trackedDataList) {
        super(FXML);
        setConnections(trackedDataList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Parameter> trackedDataList) {
        trackedDataListView.setItems(trackedDataList);
        trackedDataListView.setCellFactory(listView -> new TrackedDataListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        trackedDataListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in tracked data list panel changed to : '" + newValue + "'");
                        raise(new TrackedDataPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TrackedDataListCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            trackedDataListView.scrollTo(index);
            trackedDataListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Parameter} using a {@code TrackedDataListCard}.
     */
    class TrackedDataListViewCell extends ListCell<Parameter> {
        @Override
        protected void updateItem(Parameter parameter, boolean empty) {
            super.updateItem(parameter, empty);

            if (empty || parameter == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TrackedDataListCard(parameter, getIndex() + 1).getRoot());
            }
        }
    }

}
