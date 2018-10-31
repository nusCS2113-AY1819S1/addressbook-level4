package seedu.address.model.login;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalUsers.AMY;
import static seedu.address.testutil.TypicalUsers.BOB;

public class UniqueUserListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueUserList uniqueUserList = new UniqueUserList();
    @Test
    public void contains_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.contains(null);
    }

    @Test
    public void contains_userNotInList_returnsFalse() {
        assertFalse(uniqueUserList.contains(AMY));
    }

    @Test
    public void contains_userInList_returnsTrue() {
        uniqueUserList.add(AMY);
        assertTrue(uniqueUserList.contains(AMY));
    }

    @Test
    public void contains_userWithSameIdentityFieldsInList_returnsTrue() {
        uniqueUserList.add(AMY);
        User editedAmy = new User(new Username("amy"), new Password("pass"));
        assertTrue(uniqueUserList.contains(editedAmy));
    }

    @Test
    public void add_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.add(null);
    }

    @Test
    public void add_duplicateUser_throwsDuplicateUserException() {
        uniqueUserList.add(AMY);
        thrown.expect(DuplicateUserException.class);
        uniqueUserList.add(AMY);
    }

    @Test
    public void setUser_nullTargetUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUser(null, AMY);
    }

    @Test
    public void setUser_nullEditedUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUser(AMY, null);
    }

    @Test
    public void setUser_targetUserNotInList_throwsUserNotFoundException() {
        thrown.expect(UserNotFoundException.class);
        uniqueUserList.setUser(AMY, AMY);
    }

    @Test
    public void setUser_editedUserIsSameUser_success() {
        uniqueUserList.add(AMY);
        uniqueUserList.setUser(AMY, AMY);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(AMY);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUser_editedUserHasSameIdentity_success() {
        uniqueUserList.add(AMY);
        User editedAmy = new User(new Username("amy"), new Password("pass"));
        uniqueUserList.setUser(AMY, editedAmy);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(editedAmy);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUser_editedUserHasDifferentIdentity_success() {
        uniqueUserList.add(AMY);
        uniqueUserList.setUser(AMY, BOB);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(BOB);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUser_editedUserHasNonUniqueIdentity_throwsDuplicateUserException() {
        uniqueUserList.add(AMY);
        uniqueUserList.add(BOB);
        thrown.expect(DuplicateUserException.class);
        uniqueUserList.setUser(AMY, BOB);
    }

    @Test
    public void remove_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.remove(null);
    }

    @Test
    public void remove_userDoesNotExist_throwsUserNotFoundException() {
        thrown.expect(UserNotFoundException.class);
        uniqueUserList.remove(AMY);
    }

    @Test
    public void remove_existingUser_removesUser() {
        uniqueUserList.add(AMY);
        uniqueUserList.remove(AMY);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUsers_nullUniqueUserList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUsers((UniqueUserList) null);
    }

    @Test
    public void setUsers_uniqueUserList_replacesOwnListWithProvidedUniqueUserList() {
        uniqueUserList.add(AMY);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(BOB);
        uniqueUserList.setUsers(expectedUniqueUserList);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUsers_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUsers((List<User>) null);
    }

    @Test
    public void setUsers_list_replacesOwnListWithProvidedList() {
        uniqueUserList.add(AMY);
        List<User> userList = Collections.singletonList(BOB);
        uniqueUserList.setUsers(userList);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(BOB);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUsers_listWithDuplicateUsers_throwsDuplicateUserException() {
        List<User> listWithDuplicateUsers = Arrays.asList(AMY, AMY);
        thrown.expect(DuplicateUserException.class);
        uniqueUserList.setUsers(listWithDuplicateUsers);
    }
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueUserList.asObservableList().remove(0);
    }
}
