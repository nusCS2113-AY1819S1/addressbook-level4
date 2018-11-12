package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.expenses.Expenses;

/**
 * Provides a handle for {@code ExpensesListPanelHandle} containing the list of {@code ExpensesCard}.
 */
public class ExpensesListPanelHandle extends NodeHandle<ListView<Expenses>> {
    public static final String EXPENSES_LIST_VIEW_ID = "#expensesListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Expenses> lastRememberedSelectedExpensesCard;

    public ExpensesListPanelHandle(ListView<Expenses> expensesListPanelNode) {
        super(expensesListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ExpensesCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ExpensesCardHandle getHandleToSelectedCard() {
        List<Expenses> selectedExpensesList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedExpensesList.size() != 1) {
            throw new AssertionError("Expenses list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ExpensesCardHandle::new)
                .filter(handle -> handle.equals(selectedExpensesList.get(0)))
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
        List<Expenses> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code Expenses}.
     */
    public void navigateToCard(Expenses expenses) {
        if (!getRootNode().getItems().contains(expenses)) {
            throw new IllegalArgumentException("Expenses does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(expenses);
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
     * Selects the {@code ExpensesCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the expenses card handle of a expenses associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ExpensesCardHandle getExpensesCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ExpensesCardHandle::new)
                .filter(handle -> handle.equals(getExpenses(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Expenses getExpenses(int index) {
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
     * Remembers the selected {@code ExpensesCard} in the list.
     */
    public void rememberSelectedExpensesCard() {
        List<Expenses> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedExpensesCard = Optional.empty();
        } else {
            lastRememberedSelectedExpensesCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedExpensesCard()} call.
     */
    public boolean isSelectedExpensesCardChanged() {
        List<Expenses> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedExpensesCard.isPresent();
        } else {
            return !lastRememberedSelectedExpensesCard.isPresent()
                    || !lastRememberedSelectedExpensesCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }


}
