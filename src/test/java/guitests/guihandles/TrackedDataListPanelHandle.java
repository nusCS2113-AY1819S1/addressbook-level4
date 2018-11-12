package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.workout.Parameter;

/**
 * Provides a handle for {@code TrackedDataListPanel} containing the list of {@code TrackedDataListCard}.
 */
public class TrackedDataListPanelHandle extends NodeHandle<ListView<Parameter>> {
    public static final String PARAMETER_LIST_VIEW_ID = "#trackedDataListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Parameter> lastRememberedSelectedTrackedDataListCard;

    public TrackedDataListPanelHandle(ListView<Parameter> trackedDataListPanelNode) {
        super(trackedDataListPanelNode);
    }

    //TODO: Test the selection of parameters
    /**
     * Returns a handle to the selected {@code TrackedDataListCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public TrackedDataListCardHandle getHandleToSelectedCard() {
        List<Parameter> selectedTrackedDataList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedTrackedDataList.size() != 1) {
            throw new AssertionError("Tracked data list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(TrackedDataListCardHandle::new)
                .filter(handle -> handle.equals(selectedTrackedDataList.get(0)))
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
        List<Parameter> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code parameter}.
     */
    public void navigateToCard(Parameter parameter) {
        if (!getRootNode().getItems().contains(parameter)) {
            throw new IllegalArgumentException("Parameter does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(parameter);
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
     * Selects the {@code TrackedDataListCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the tracked data list card handle of a parameter associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public TrackedDataListCardHandle getTrackedDataListCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(TrackedDataListCardHandle::new)
                .filter(handle -> handle.equals(getParameter(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Parameter getParameter(int index) {
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
     * Remembers the selected {@code TrackedDataListCard} in the list.
     */
    public void rememberSelectedTrackedDataListCard() {
        List<Parameter> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTrackedDataListCard = Optional.empty();
        } else {
            lastRememberedSelectedTrackedDataListCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code TrackedDataListCard} is different from the value remembered by the most
     * recent {@code rememberSelectedTrackedDataListCard()} call.
     */
    public boolean isSelectedTrackedDataListCardChanged() {
        List<Parameter> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTrackedDataListCard.isPresent();
        } else {
            return !lastRememberedSelectedTrackedDataListCard.isPresent()
                    || !lastRememberedSelectedTrackedDataListCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
