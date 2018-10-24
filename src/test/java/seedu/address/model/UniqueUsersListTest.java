package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.login.UniqueUsersList;

public class UniqueUsersListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueUsersList uniqueUsersList = new UniqueUsersList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueUsersList.asObservableList().remove(0);
    }
}
