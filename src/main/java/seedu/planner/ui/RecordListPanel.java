package seedu.planner.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.JumpToListRequestEvent;
import seedu.planner.commons.events.ui.RecordPanelSelectionChangedEvent;
import seedu.planner.model.record.Record;

/**
 * Panel containing the list of records.
 */
public class RecordListPanel extends UiPart<Region> {
    private static final String FXML = "RecordListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecordListPanel.class);

    @FXML
    private ListView<Record> recordListView;

    public RecordListPanel(ObservableList<Record> recordList) {
        super(FXML);
        setConnections(recordList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Record> recordList) {
        recordListView.setItems(recordList);
        recordListView.setCellFactory(listView -> new RecordListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        recordListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in record list panel changed to : '" + newValue + "'");
                        raise(new RecordPanelSelectionChangedEvent(newValue));
                    } else {
                        logger.fine("Selection in record list panel unselected");
                        raise(new RecordPanelSelectionChangedEvent(null));
                    }
                });
    }

    /**
     * Scrolls to the {@code RecordCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            recordListView.scrollTo(index);
            recordListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Record} using a {@code RecordCard}.
     */
    class RecordListViewCell extends ListCell<Record> {
        @Override
        protected void updateItem(Record record, boolean empty) {
            super.updateItem(record, empty);

            if (empty || record == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecordCard(record, getIndex() + 1).getRoot());
            }
        }
    }

}
