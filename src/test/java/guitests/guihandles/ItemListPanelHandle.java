package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.item.Item;

/**
 * Provides a handle for {@code ItemListPanel} containing the list of {@code ItemCard}.
 */
public class ItemListPanelHandle extends NodeHandle<ListView<Item>> {
    public static final String ITEM_LIST_VIEW_ID = "#itemListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Item> lastRememberedSelectedItemCard;

    public ItemListPanelHandle(ListView<Item> itemListPanelNode) {
        super(itemListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ItemCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ItemCardHandle getHandleToSelectedCard() {
        List<Item> selectedItemList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItemList.size() != 1) {
            throw new AssertionError("Item list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ItemCardHandle::new)
                .filter(handle -> handle.equals(selectedItemList.get(0)))
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
        List<Item> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code item}.
     */
    public void navigateToCard(Item item) {
        if (!getRootNode().getItems().contains(item)) {
            throw new IllegalArgumentException("Item does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(item);
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
     * Selects the {@code ItemCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the item card handle of a item associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ItemCardHandle getItemCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ItemCardHandle::new)
                .filter(handle -> handle.equals(getItem(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Item getItem(int index) {
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
     * Remembers the selected {@code ItemCard} in the list.
     */
    public void rememberSelectedItemCard() {
        List<Item> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedItemCard = Optional.empty();
        } else {
            lastRememberedSelectedItemCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ItemCard} is different from the value remembered by the most recent
     * {@code rememberSelectedItemCard()} call.
     */
    public boolean isSelectedItemCardChanged() {
        List<Item> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedItemCard.isPresent();
        } else {
            return !lastRememberedSelectedItemCard.isPresent()
                    || !lastRememberedSelectedItemCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
