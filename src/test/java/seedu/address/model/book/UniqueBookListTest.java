package seedu.address.model.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalBooks.ALICE;
import static seedu.address.testutil.TypicalBooks.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.BookBuilder;

public class UniqueBookListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBookList uniqueBookList = new UniqueBookList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueBookList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueBookList.add(ALICE);
        assertTrue(uniqueBookList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBookList.add(ALICE);
        Book editedAlice = new BookBuilder(ALICE).withQuantity(VALID_QUANTITY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueBookList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueBookList.add(ALICE);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setPerson(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setPerson(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(BookNotFoundException.class);
        uniqueBookList.setPerson(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueBookList.add(ALICE);
        uniqueBookList.setPerson(ALICE, ALICE);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(ALICE);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueBookList.add(ALICE);
        Book editedAlice = new BookBuilder(ALICE).withQuantity(VALID_QUANTITY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueBookList.setPerson(ALICE, editedAlice);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(editedAlice);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueBookList.add(ALICE);
        uniqueBookList.setPerson(ALICE, BOB);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueBookList.add(ALICE);
        uniqueBookList.add(BOB);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.setPerson(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(BookNotFoundException.class);
        uniqueBookList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueBookList.add(ALICE);
        uniqueBookList.remove(ALICE);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setPersons((UniqueBookList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueBookList.add(ALICE);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        uniqueBookList.setPersons(expectedUniqueBookList);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setPersons((List<Book>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueBookList.add(ALICE);
        List<Book> bookList = Collections.singletonList(BOB);
        uniqueBookList.setPersons(bookList);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Book> listWithDuplicateBooks = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.setPersons(listWithDuplicateBooks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueBookList.asUnmodifiableObservableList().remove(0);
    }
}
