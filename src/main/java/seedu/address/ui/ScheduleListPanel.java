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
import seedu.address.commons.events.ui.JumpToListScheduleRequestEvent;
import seedu.address.commons.events.ui.SchedulePanelSelectionChangedEvent;
import seedu.address.model.schedule.Schedule;

/**
 * Panel containing the list of persons.
 */
public class ScheduleListPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleListPanel.class);

    @FXML
    private ListView<Schedule> scheduleListView;

    public ScheduleListPanel(ObservableList<Schedule> scheduleList) {
        super(FXML);
        setConnections(scheduleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Schedule> scheduleList) {
        scheduleListView.setItems(scheduleList);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }


    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListScheduleRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            scheduleListView.scrollTo(index);
            scheduleListView.getSelectionModel().clearAndSelect(index);
        });
    }

    private void setEventHandlerForSelectionChangeEvent() {
        scheduleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new SchedulePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code schedule} using a {@code ScheduleCard}.
     */
    class ScheduleListViewCell extends ListCell<Schedule> {
        @Override
        protected void updateItem(Schedule schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduleCard(schedule, getIndex() + 1).getRoot());
            }
        }
    }

}
