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
import seedu.address.commons.events.ui.JumpToRecommendListRequestEvent;
import seedu.address.commons.events.ui.WorkoutPanelSelectionChangedEvent;
import seedu.address.model.workout.Workout;

/**
 * Panel containing the list of workouts.
 */
public class WorkoutListPanel extends UiPart<Region> {
    private static final String FXML = "WorkoutListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WorkoutListPanel.class);

    @FXML
    private ListView<Workout> workoutListView;

    public WorkoutListPanel(ObservableList<Workout> workoutList) {
        super(FXML);
        setConnections(workoutList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Workout> workoutList) {
        workoutListView.setItems(workoutList);
        workoutListView.setCellFactory(listView -> new WorkoutListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        workoutListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in workout list panel changed to : '" + newValue + "'");
                        raise(new WorkoutPanelSelectionChangedEvent(newValue));
                    }
                });
    }
    /**
     * Scrolls to the {@code WorkoutCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            workoutListView.scrollTo(index);
            workoutListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToRecommendListRequestEvent(JumpToRecommendListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Workout} using a {@code WorkoutCard}.
     */
    class WorkoutListViewCell extends ListCell<Workout> {
        @Override
        protected void updateItem(Workout workout, boolean empty) {
            super.updateItem(workout, empty);

            if (empty || workout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WorkoutCard(workout, getIndex() + 1).getRoot());
            }
        }
    }

}
