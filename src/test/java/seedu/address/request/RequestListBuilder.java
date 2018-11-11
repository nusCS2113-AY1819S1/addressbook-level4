package seedu.address.request;

/**
 * A utility class to help with building RequestList objects.
 * Example usage: <br>
 *     {@code RequestList rl = new RequestListBuilder().withRequest(...).build();}
 */
public class RequestListBuilder {

    private RequestList requestList;

    public RequestListBuilder() {
        requestList = new RequestList();
    }

    public RequestListBuilder(RequestList requestList) {
        this.requestList = requestList;
    }

    /**
     * Adds a new {@code Request} to the {@code RequestList} that we are building.
     */
    public RequestListBuilder withRequest(Request request) {
        requestList.addRequest(request);
        return this;
    }

    public RequestList build() {
        return requestList;
    }
}
