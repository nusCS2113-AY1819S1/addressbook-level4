package seedu.address.model.request;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

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
     * Returns true if a request with the same identity as {@code request} exists in the BookInventory.
     */
    boolean hasRequest(Request request);

    /**
     * Deletes the given request.
     * The request must exist in the BookInventory.
     */
    void deleteRequest(Request target);

    /**
     * Adds the given request.
     * {@code request} must not already exist in the BookInventory.
     */
    void addRequest(Request request);

    /**
     * Replaces the given request {@code target} with {@code editedRequest}.
     * {@code target} must exist in the BookInventory.
     * The request identity of {@code editedRequest} must not be
     * the same as another existing request in the BookInventory.
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
     * Returns true if the requestModel has previous BookInventory states to restore.
     */
    boolean canUndoRequests();

    /**
     * Returns true if the requestModel has undone BookInventory states to restore.
     */
    boolean canRedoRequests();

    /**
     * Restores the requestModel's BookInventory to its previous state.
     */
    void undoRequests();

    /**
     * Restores the requestModel's BookInventory to its previously undone state.
     */
    void redoRequests();

    /**
     * Saves the current BookInventory state for undo/redo.
     */
    void commitRequests();
}
