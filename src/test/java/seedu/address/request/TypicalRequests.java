package seedu.address.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalRequests {

    public static final Request REQUEST_STUDENT_A = new RequestBuilder()
            .withIsbn("9780748137992")
            .withQuantity("5")
            .withEmail("testing3@gmail.com").build();
    public static final Request REQUEST_STUDENT_B = new RequestBuilder()
            .withIsbn("9781401309572")
            .withQuantity("10")
            .withEmail("testing1@gmail.com").build();

    private TypicalRequests() {} // prevents instantiation

    /**
     * Returns an {@code RequestList} with all the typical requests.
     */
    public static RequestList getTypicalRequestList() {
        RequestList rl = new RequestList();
        for (Request request : getTypicalRequests()) {
            rl.addRequest(request);
        }
        return rl;
    }

    public static List<Request> getTypicalRequests() {
        return new ArrayList<>(Arrays.asList(REQUEST_STUDENT_A, REQUEST_STUDENT_B));
    }
}
