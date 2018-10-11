package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.exceptions.BookNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A request is considered unique by comparing using {@code Request#isSameRequest(Request)}.
 * As such, adding and updating of persons uses Request#isSameRequest(Request) for equality
 * so as to ensure that the request being added or updated is unique in terms of identity in
 * the UniqueRequestList. However, the removal of a request uses Request#equals(Object) so
 * as to ensure that the request with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Request#isSameRequest(Request)
 */
public class UniqueRequestList implements Iterable<Request> {

    private final ObservableList<Request> internalRequestList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent request as the given argument.
     */
    public boolean contains(Request toCheck) {
        requireNonNull(toCheck);
        return internalRequestList.stream().anyMatch(toCheck::isSameRequest);
    }

    /**
     * Adds a request to the list.
     * The request must not already exist in the list.
     */
    public void add(Request toAdd) {
        requireNonNull(toAdd);
        internalRequestList.add(toAdd);
    }

    /**
     * Replaces the request {@code target} in the list with {@code editedRequest}.
     * {@code target} must exist in the list.
     * The request identity of {@code editedRequest} must not be the same as another existing request in the list.
     */
    public void setRequest(Request target, Request editedRequest) {
        requireAllNonNull(target, editedRequest);

        int index = internalRequestList.indexOf(target);
        if (index == -1) {
            throw new BookNotFoundException();
        }

        if (!target.isSameRequest(editedRequest) && contains(editedRequest)) {
            throw new BookNotFoundException();
        }

        internalRequestList.set(index, editedRequest);
    }

    public void setRequest(UniqueRequestList replacement) {
        requireNonNull(replacement);
        internalRequestList.setAll(replacement.internalRequestList);
    }

    /**
     * Replaces the contents of this list with {@code requests}.
     * {@code requests} must not contain duplicate requests.
     */
    public void setRequests(List<Request> requests) {
        requireAllNonNull(requests);
        if (!requestsAreUnique(requests)) {
            //    throw new BookNotFoundException();
        }

        internalRequestList.setAll(requests);
    }
    /**
     * Removes the equivalent request from the list.
     * The request must exist in the list.
     */
    public void remove(Request toRemove) {
        requireNonNull(toRemove);
        if (!internalRequestList.remove(toRemove)) {
            throw new BookNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Request> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalRequestList);
    }

    @Override
    public Iterator<Request> iterator() {
        return internalRequestList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRequestList // instanceof handles nulls
                        && internalRequestList.equals(((UniqueRequestList) other).internalRequestList));
    }

    @Override
    public int hashCode() {
        return internalRequestList.hashCode();
    }

    /**
     * Returns true if {@code requests} contains only unique requests.
     */
    private boolean requestsAreUnique(List<Request> requests) {
        for (int i = 0; i < requests.size() - 1; i++) {
            for (int j = i + 1; j < requests.size(); j++) {
                if (requests.get(i).isSameRequest(requests.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
