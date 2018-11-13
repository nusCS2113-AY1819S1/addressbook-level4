package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.task.Task;

/**
 * Provides a handle for {@code TaskListPanel} containing the list of {@code TaskCard}.
 */
public class TaskListPanelHandle extends NodeHandle<ListView<Task>> {
    public static final String TASK_LIST_VIEW_ID = "#taskListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Task> lastRememberedSelectedTaskCard;

    public TaskListPanelHandle(ListView<Task> taskListPanelNode) {
        super(taskListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TaskCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public TaskCardHandle getHandleToSelectedCard() {
        List<Task> selectedTaskList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedTaskList.size() != 1) {
            throw new AssertionError("Task list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(TaskCardHandle::new)
                .filter(handle -> handle.equals(selectedTaskList.get(0)))
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
        List<Task> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code task}.
     */
    public void navigateToCard(Task task) {
        if (!getRootNode().getItems().contains(task)) {
            throw new IllegalArgumentException("Task does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(task);
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
     * Selects the {@code TaskCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the person card handle of a task associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public TaskCardHandle getTaskCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(TaskCardHandle::new)
                .filter(handle -> handle.equals(getTask(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Task getTask(int index) {
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
     * Remembers the selected {@code TaskCard} in the list.
     */
    public void rememberSelectedTaskCard() {
        List<Task> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTaskCard = Optional.empty();
        } else {
            lastRememberedSelectedTaskCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code TaskCard} is different from the value remembered by the most recent
     * {@code rememberSelectedTaskCard()} call.
     */
    public boolean isSelectedTaskCardChanged() {
        List<Task> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTaskCard.isPresent();
        } else {
            return !lastRememberedSelectedTaskCard.isPresent()
                    || !lastRememberedSelectedTaskCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }

}
