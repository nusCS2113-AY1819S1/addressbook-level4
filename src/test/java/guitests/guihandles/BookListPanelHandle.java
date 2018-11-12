package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.book.Book;

/**
 * Provides a handle for {@code BookListPanel} containing the list of {@code BookCard}.
 */
public class BookListPanelHandle extends NodeHandle<ListView<Book>> {
    public static final String BOOK_LIST_VIEW_ID = "#bookListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Book> lastRememberedSelectedBookCard;

    public BookListPanelHandle(ListView<Book> bookListPanelNode) {
        super(bookListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code BookCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BookCardHandle getHandleToSelectedCard() {
        List<Book> selectedBookList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedBookList.size() != 1) {
            throw new AssertionError("Book list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(BookCardHandle::new)
                .filter(handle -> handle.equals(selectedBookList.get(0)))
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
        List<Book> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code book}.
     */
    public void navigateToCard(Book book) {
        if (!getRootNode().getItems().contains(book)) {
            throw new IllegalArgumentException("Book does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(book);
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
     * Selects the {@code BookCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the book card handle of a book associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BookCardHandle getBookCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(BookCardHandle::new)
                .filter(handle -> handle.equals(getBook(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Book getBook(int index) {
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
     * Remembers the selected {@code BookCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<Book> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedBookCard = Optional.empty();
        } else {
            lastRememberedSelectedBookCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code BookCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<Book> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedBookCard.isPresent();
        } else {
            return !lastRememberedSelectedBookCard.isPresent()
                    || !lastRememberedSelectedBookCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
