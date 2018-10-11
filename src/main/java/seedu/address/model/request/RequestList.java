package seedu.address.model.request;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRequest comparison)
 */
public class RequestList implements ReadOnlyRequests {

    private final UniqueRequestList requestList;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        requestList = new UniqueRequestList();
    }

    public RequestList() {}

    /**
     * Creates an RequestList using the Persons in the {@code toBeCopied}
     */
    public RequestList(ReadOnlyRequests toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the request list with {@code requestList}.
     * {@code requestList} must not contain duplicate requestList.
     */
    public void setRequestList(List<Request> requestList) {
        this.requestList.setRequests(requestList);
    }

    /**
     * Resets the existing data of this {@code RequestList} with {@code newData}.
     */
    public void resetData(ReadOnlyRequests newData) {
        requireNonNull(newData);

        setRequestList(newData.getRequestList());
    }

    //// request-level operations

    /**
     * Returns true if a request with the same identity as {@code request} exists in the address book.
     */
    public boolean hasRequest(Request request) {
        requireNonNull(request);
        return requestList.contains(request);
    }

    /**
     * Adds a request to the address book.
     * The request must not already exist in the address book.
     */
    public void addRequest(Request p) {
        requestList.add(p);
    }

    /**
     * Replaces the given request {@code target} in the list with {@code editedRequest}.
     * {@code target} must exist in the address book.
     * The request identity of {@code editedRequest} must not be the same as
     * another existing request in the address book.
     */
    public void updateRequest(Request target, Request editedRequest) {
        requireNonNull(editedRequest);

        requestList.setRequest(target, editedRequest);
    }

    /**
     * Removes {@code key} from this {@code RequestList}.
     * {@code key} must exist in the address book.
     */
    public void removeRequest(Request key) {
        requestList.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return requestList.asUnmodifiableObservableList().size() + " requestList";
        // TODO: refine later
    }

    @Override
    public ObservableList<Request> getRequestList() {
        return requestList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestList // instanceof handles nulls
                && requestList.equals(((RequestList) other).requestList));
    }

    @Override
    public int hashCode() {
        return requestList.hashCode();
    }
}
