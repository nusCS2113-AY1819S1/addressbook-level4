package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.record.Record;

/**
 * Provides a handle for {@code RecordListPanel} containing the list of {@code RecordCard}.
 */
public class RecordListPanelHandle extends NodeHandle<ListView<Record>> {
    public static final String RECORD_LIST_VIEW_ID = "#recordListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Record> lastRememberedSelectedRecordCard;

    public RecordListPanelHandle(ListView<Record> recordListPanelNode) {
        super(recordListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RecordCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RecordCardHandle getHandleToSelectedCard() {
        List<Record> selectedRecordList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedRecordList.size() != 1) {
            throw new AssertionError("Record list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(RecordCardHandle::new)
                .filter(handle -> handle.equals(selectedRecordList.get(0)))
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
        List<Record> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code record}.
     */
    public void navigateToCard(Record record) {
        if (!getRootNode().getItems().contains(record)) {
            throw new IllegalArgumentException("Record does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(record);
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
     * Selects the {@code RecordCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the record card handle of a record associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RecordCardHandle getRecordCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(RecordCardHandle::new)
                .filter(handle -> handle.equals(getRecord(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Record getRecord(int index) {
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
     * Remembers the selected {@code RecordCard} in the list.
     */
    public void rememberSelectedRecordCard() {
        List<Record> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedRecordCard = Optional.empty();
        } else {
            lastRememberedSelectedRecordCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code RecordCard} is different from the value remembered by the most recent
     * {@code rememberSelectedRecordCard()} call.
     */
    public boolean isSelectedRecordCardChanged() {
        List<Record> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedRecordCard.isPresent();
        } else {
            return !lastRememberedSelectedRecordCard.isPresent()
                    || !lastRememberedSelectedRecordCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
