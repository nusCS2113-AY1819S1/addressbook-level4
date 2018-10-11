package seedu.address.model.request;

import javafx.collections.ObservableList;

import java.util.function.Predicate;

/**
 * The API of the RequestModel component.
 */
public interface RequestModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Request> PREDICATE_SHOW_ALL_REQUESTS = unused -> true;

    /** Clears existing backing requestModel and replaces with the provided new data. */
    void resetData(ReadOnlyRequests newData);

    /** Returns the RequestList */
    ReadOnlyRequests getRequestList();

    /**
     * Returns true if a request with the same identity as {@code request} exists in the address book.
     */
    boolean hasRequest(Request request);

    /**
     * Deletes the given request.
     * The request must exist in the address book.
     */
    void deleteRequest(Request target);

    /**
     * Adds the given request.
     * {@code request} must not already exist in the address book.
     */
    void addRequest(Request request);

    /**
     * Replaces the given request {@code target} with {@code editedRequest}.
     * {@code target} must exist in the address book.
     * The request identity of {@code editedRequest} must not be the same as another existing request in the address book.
     */
    void updateRequest(Request target, Request editedRequest);

    /** Returns an unmodifiable view of the filtered request list */
    ObservableList<Request> getFilteredRequestList();

    /**
     * Updates the filter of the filtered request list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRequestList(Predicate<Request> predicate);

    /**
     * Returns true if the requestModel has previous address book states to restore.
     */
    boolean canUndoRequests();

    /**
     * Returns true if the requestModel has undone address book states to restore.
     */
    boolean canRedoRequests();

    /**
     * Restores the requestModel's address book to its previous state.
     */
    void undoRequests();

    /**
     * Restores the requestModel's address book to its previously undone state.
     */
    void redoRequests();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitRequests();
}
