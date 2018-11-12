package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.workout.Workout;

/**
 * Provides a handle for {@code WorkoutListPanel} containing the list of {@code WorkoutCard}.
 */
public class WorkoutListPanelHandle extends NodeHandle<ListView<Workout>> {
    public static final String WORKOUT_LIST_VIEW_ID = "#workoutListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Workout> lastRememberedSelectedWorkoutCard;

    public WorkoutListPanelHandle(ListView<Workout> workoutListPanelNode) {
        super(workoutListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code WorkoutCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WorkoutCardHandle getHandleToSelectedCard() {
        List<Workout> selectedWorkoutList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedWorkoutList.size() != 1) {
            throw new AssertionError("Workout list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(WorkoutCardHandle::new)
                .filter(handle -> handle.equals(selectedWorkoutList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Workout> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code workout}.
     */
    public void navigateToCard(Workout workout) {
        if (!getRootNode().getItems().contains(workout)) {
            throw new IllegalArgumentException("Workout does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(workout);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code WorkoutCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the workout card handle of a workout associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WorkoutCardHandle getWorkoutCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(WorkoutCardHandle::new)
                .filter(handle -> handle.equals(getWorkout(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Workout getWorkout(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code WorkoutCard} in the list.
     */
    public void rememberSelectedWorkoutCard() {
        List<Workout> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedWorkoutCard = Optional.empty();
        } else {
            lastRememberedSelectedWorkoutCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code WorkoutCard} is different from the value remembered by the most recent
     * {@code rememberSelectedWorkoutCard()} call.
     */
    public boolean isSelectedWorkoutCardChanged() {
        List<Workout> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedWorkoutCard.isPresent();
        } else {
            return !lastRememberedSelectedWorkoutCard.isPresent()
                    || !lastRememberedSelectedWorkoutCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
