package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.divelog.model.dive.DiveSession;

/**
 * Provides a handle for {@code DiveListPanel} containing the list of {@code DiveSessionCard}.
 */
public class DiveListPanelHandle extends NodeHandle<ListView<DiveSession>> {
    public static final String DIVE_SESSION_LIST_VIEW_ID = "#diveListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<DiveSession> lastRememberedSelectedDiveCard;

    public DiveListPanelHandle(ListView<DiveSession> diveSessionListPanelNode) {
        super(diveSessionListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code DiveSessionCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public DiveSessionCardHandle getHandleToSelectedCard() {
        List<DiveSession> selectedDiveSessionList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedDiveSessionList.size() != 1) {
            throw new AssertionError("Dive session list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(DiveSessionCardHandle::new)
                .filter(handle -> handle.equals(selectedDiveSessionList.get(0)))
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
        List<DiveSession> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code DiveSession}.
     */
    public void navigateToCard(DiveSession diveSession) {
        if (!getRootNode().getItems().contains(diveSession)) {
            throw new IllegalArgumentException("Dive session does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(diveSession);
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
     * Selects the {@code DiveSessionCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the dive card handle of a dive associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public DiveSessionCardHandle getDiveCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(DiveSessionCardHandle::new)
                .filter(handle -> handle.equals(getDive(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private DiveSession getDive(int index) {
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
     * Remembers the selected {@code DiveSessionCard} in the list.
     */
    public void rememberSelectedDiveCard() {
        List<DiveSession> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedDiveCard = Optional.empty();
        } else {
            lastRememberedSelectedDiveCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code DiveSessionCard} is different from the value remembered by the most recent
     * {@code rememberSelectedDiveCard()} call.
     */
    public boolean isSelectedDiveSessionCardChanged() {
        List<DiveSession> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedDiveCard.isPresent();
        } else {
            return !lastRememberedSelectedDiveCard.isPresent()
                    || !lastRememberedSelectedDiveCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
