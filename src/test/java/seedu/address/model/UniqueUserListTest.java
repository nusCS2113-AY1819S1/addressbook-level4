package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.login.UniqueUserList;

public class UniqueUserListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueUserList.asObservableList().remove(0);
    }
}
