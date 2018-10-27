package seedu.address.model.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIENCE;
import static seedu.address.testutil.TypicalBooks.ART;
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
        assertFalse(uniqueBookList.contains(ART));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueBookList.add(ART);
        assertTrue(uniqueBookList.contains(ART));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBookList.add(ART);
        Book editedAlice = new BookBuilder(ART).withQuantity(VALID_QUANTITY_BIOLOGY).withTags(VALID_TAG_SCIENCE)
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
        uniqueBookList.add(ART);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.add(ART);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBook(null, ART);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBook(ART, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(BookNotFoundException.class);
        uniqueBookList.setBook(ART, ART);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueBookList.add(ART);
        uniqueBookList.setBook(ART, ART);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(ART);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueBookList.add(ART);
        Book editedAlice = new BookBuilder(ART).withQuantity(VALID_QUANTITY_BIOLOGY).withTags(VALID_TAG_SCIENCE)
                .build();
        uniqueBookList.setBook(ART, editedAlice);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(editedAlice);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueBookList.add(ART);
        uniqueBookList.setBook(ART, BOB);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueBookList.add(ART);
        uniqueBookList.add(BOB);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.setBook(ART, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(BookNotFoundException.class);
        uniqueBookList.remove(ART);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueBookList.add(ART);
        uniqueBookList.remove(ART);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBooks((UniqueBookList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueBookList.add(ART);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        uniqueBookList.setBooks(expectedUniqueBookList);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBookList.setBooks((List<Book>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueBookList.add(ART);
        List<Book> bookList = Collections.singletonList(BOB);
        uniqueBookList.setBooks(bookList);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOB);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Book> listWithDuplicateBooks = Arrays.asList(ART, ART);
        thrown.expect(DuplicateBookException.class);
        uniqueBookList.setBooks(listWithDuplicateBooks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueBookList.asUnmodifiableObservableList().remove(0);
    }
}
