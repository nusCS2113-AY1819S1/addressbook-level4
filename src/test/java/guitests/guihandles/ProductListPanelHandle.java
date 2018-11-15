package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.product.Product;

//@@garagaristahir
/**
 * Provides a handle for {@code ProductListPanel} containing the list of {@code ProductCard}.
 */
public class ProductListPanelHandle extends NodeHandle<ListView<Product>> {
    public static final String PRODUCT_LIST_VIEW_ID = "#productListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Product> lastRememberedSelectedProductCard;

    public ProductListPanelHandle(ListView<Product> productListPanelNode) {
        super(productListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ProductCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ProductCardHandle getHandleToSelectedCard() {
        List<Product> selectedProductList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedProductList.size() != 1) {
            throw new AssertionError("Product list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ProductCardHandle::new)
                .filter(handle -> handle.equals(selectedProductList.get(0)))
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
        List<Product> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code product}.
     */
    public void navigateToCard(Product product) {
        if (!getRootNode().getItems().contains(product)) {
            throw new IllegalArgumentException("Product does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(product);
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
     * Selects the {@code ProductCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the product card handle of a product associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ProductCardHandle getProductCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ProductCardHandle::new)
                .filter(handle -> handle.equals(getProduct(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Product getProduct(int index) {
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
     * Remembers the selected {@code ProductCard} in the list.
     */
    public void rememberSelectedProductCard() {
        List<Product> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedProductCard = Optional.empty();
        } else {
            lastRememberedSelectedProductCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ProductCard} is different from the value remembered by the most recent
     * {@code rememberSelectedProductCard()} call.
     */
    public boolean isSelectedProductCardChanged() {
        List<Product> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedProductCard.isPresent();
        } else {
            return !lastRememberedSelectedProductCard.isPresent()
                    || !lastRememberedSelectedProductCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
