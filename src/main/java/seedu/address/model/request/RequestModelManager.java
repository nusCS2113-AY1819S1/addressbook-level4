package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.UserPrefs;

/**
 * Represents the in-memory requestModel of the address book data.
 */
public class RequestModelManager extends ComponentManager implements RequestModel {
    private static final Logger logger = LogsCenter.getLogger(RequestModelManager.class);

    private final VersionedRequestList versionedRequestList;
    private final FilteredList<Request> filteredRequests;

    /**
     * Initializes a RequestModelManager with the given requestList and userPrefs.
     */
    public RequestModelManager(ReadOnlyRequests requestList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(requestList, userPrefs);

        logger.fine("Initializing with address book: " + requestList + " and user prefs " + userPrefs);

        versionedRequestList = new VersionedRequestList(requestList);
        filteredRequests = new FilteredList<>(versionedRequestList.getRequestList());
    }

    public RequestModelManager() {
        this(new RequestList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyRequests newData) {
        versionedRequestList.resetData(newData);
        indicateRequestListChanged();
    }

    @Override
    public ReadOnlyRequests getRequestList() {
        return versionedRequestList;
    }

    /** Raises an event to indicate the requestModel has changed */
    private void indicateRequestListChanged() {
        raise(new RequestListChangedEvent(versionedRequestList));
    }

    @Override
    public boolean hasRequest(Request request) {
        requireNonNull(request);
        return versionedRequestList.hasRequest(request);
    }

    @Override
    public void deleteRequest(Request target) {
        versionedRequestList.removeRequest(target);
        indicateRequestListChanged();
    }

    @Override
    public void addRequest(Request request) {
        versionedRequestList.addRequest(request);
        updateFilteredRequestList(PREDICATE_SHOW_ALL_REQUESTS);
        indicateRequestListChanged();
    }

    @Override
    public void updateRequest(Request target, Request editedRequest) {
        requireAllNonNull(target, editedRequest);

        versionedRequestList.updateRequest(target, editedRequest);
        indicateRequestListChanged();
    }

    //=========== Filtered Request List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Request} backed by the internal list of
     * {@code versionedRequestList}
     */
    @Override
    public ObservableList<Request> getFilteredRequestList() {
        return FXCollections.unmodifiableObservableList(filteredRequests);
    }

    @Override
    public void updateFilteredRequestList(Predicate<Request> predicate) {
        requireNonNull(predicate);
        filteredRequests.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoRequests() {
        return versionedRequestList.canUndo();
    }

    @Override
    public boolean canRedoRequests() {
        return versionedRequestList.canRedo();
    }

    @Override
    public void undoRequests() {
        versionedRequestList.undo();
        indicateRequestListChanged();
    }

    @Override
    public void redoRequests() {
        versionedRequestList.redo();
        indicateRequestListChanged();
    }

    @Override
    public void commitRequests() {
        versionedRequestList.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof RequestModelManager)) {
            return false;
        }

        // state check
        RequestModelManager other = (RequestModelManager) obj;
        return versionedRequestList.equals(other.versionedRequestList)
                && filteredRequests.equals(other.filteredRequests);
    }

}
