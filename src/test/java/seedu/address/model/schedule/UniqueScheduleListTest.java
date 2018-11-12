package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CARL;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.CARL_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.DANIEL_LEAVE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.schedule.ScheduleBuilder;


public class UniqueScheduleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();

    @Test
    public void contains_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueScheduleList.contains(null);
    }

    @Test
    public void contains_scheduleNotInList_returnsFalse() {
        assertFalse(uniqueScheduleList.contains(ALICE_WORK));
    }

    @Test
    public void contains_scheduleInList_returnsTrue() {
        uniqueScheduleList.add(ALICE_WORK);
        assertTrue(uniqueScheduleList.contains(ALICE_WORK));
    }

    @Test
    public void contains_personWithDifferentIdentityFieldsInList_returnsFalse() {
        uniqueScheduleList.add(ALICE_WORK);
        Schedule editedAlice = new ScheduleBuilder(ALICE_WORK).withDate(VALID_DATE_AMY).withType(VALID_TYPE_CARL)
                .build();
        assertFalse(uniqueScheduleList.contains(editedAlice));
    }

    @Test
    public void add_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueScheduleList.add(null);
    }

    @Test
    public void add_duplicateSchedule_throwsDuplicateScheduleException() {
        uniqueScheduleList.add(BENSON_WORK);
        thrown.expect(DuplicateScheduleException.class);
        uniqueScheduleList.add(BENSON_WORK);
    }

    @Test
    public void setSchedule_nullTargetSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueScheduleList.setSchedule(null, CARL_WORK);
    }

    @Test
    public void setSchedule_nullEditedSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueScheduleList.setSchedule(ALICE_WORK, null);
    }

    @Test
    public void setSchedule_targetScheduleNotInList_throwsScheduleNotFoundException() {
        thrown.expect(ScheduleNotFoundException.class);
        uniqueScheduleList.setSchedule(ALICE_WORK, ALICE_WORK);
    }

    @Test
    public void setSchedule_editedScheduleIsSameSchedule_success() {
        uniqueScheduleList.add(DANIEL_LEAVE);
        uniqueScheduleList.setSchedule(DANIEL_LEAVE, DANIEL_LEAVE);
        UniqueScheduleList expectedUniqueScheduleList = new UniqueScheduleList();
        expectedUniqueScheduleList.add(DANIEL_LEAVE);
        assertEquals(expectedUniqueScheduleList, uniqueScheduleList);
    }

    @Test
    public void setSchedule_editedScheduleHasSameSchedule_success() {
        uniqueScheduleList.add(ALICE_WORK);
        Schedule editedAlice = new ScheduleBuilder(ALICE_WORK).withType(VALID_TYPE_AMY).withDate(VALID_DATE_AMY)
                .build();
        uniqueScheduleList.setSchedule(ALICE_WORK, editedAlice);
        UniqueScheduleList expectedUniqueScheduleList = new UniqueScheduleList();
        expectedUniqueScheduleList.add(editedAlice);
        assertEquals(expectedUniqueScheduleList, uniqueScheduleList);
    }

    @Test
    public void setSchedule_editedScheduleHasDifferentSchedule_success() {
        uniqueScheduleList.add(ALICE_WORK);
        uniqueScheduleList.setSchedule(ALICE_WORK, BENSON_WORK);
        UniqueScheduleList expectedUniqueScheduleList = new UniqueScheduleList();
        expectedUniqueScheduleList.add(BENSON_WORK);
        assertEquals(expectedUniqueScheduleList, uniqueScheduleList);
    }

    @Test
    public void setSchedule_editedScheduleHasNonUniqueIdentity_throwsDuplicateScheduleException() {
        uniqueScheduleList.add(ALICE_WORK);
        uniqueScheduleList.add(BENSON_WORK);
        thrown.expect(DuplicateScheduleException.class);
        uniqueScheduleList.setSchedule(ALICE_WORK, BENSON_WORK);
    }

    @Test
    public void remove_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueScheduleList.remove(null);
    }

    @Test
    public void remove_scheduleDoesNotExist_throwsScheduleNotFoundException() {
        thrown.expect(ScheduleNotFoundException.class);
        uniqueScheduleList.remove(ALICE_WORK);
    }

    @Test
    public void remove_existingSchedule_removesSchedule() {
        uniqueScheduleList.add(ALICE_WORK);
        uniqueScheduleList.remove(ALICE_WORK);
        UniqueScheduleList expectedUniqueScheduleList = new UniqueScheduleList();
        assertEquals(expectedUniqueScheduleList, uniqueScheduleList);
    }

    @Test
    public void setSchedules_nullUniqueScheduleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueScheduleList.setSchedules((UniqueScheduleList) null);
    }

    @Test
    public void setSchedules_uniqueScheduleList_replacesOwnListWithProvidedUniqueScheduleList() {
        uniqueScheduleList.add(ALICE_WORK);
        UniqueScheduleList expectedUniqueScheduleList = new UniqueScheduleList();
        expectedUniqueScheduleList.add(BENSON_WORK);
        uniqueScheduleList.setSchedules(expectedUniqueScheduleList);
        assertEquals(expectedUniqueScheduleList, uniqueScheduleList);
    }

    @Test
    public void setSchedules_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueScheduleList.setSchedules((List<Schedule>) null);
    }

    @Test
    public void setSchedules_list_replacesOwnListWithProvidedList() {
        uniqueScheduleList.add(ALICE_WORK);
        List<Schedule> scheduleList = Collections.singletonList(BENSON_WORK);
        uniqueScheduleList.setSchedules(scheduleList);
        UniqueScheduleList expectedUniqueScheduleList = new UniqueScheduleList();
        expectedUniqueScheduleList.add(BENSON_WORK);
        assertEquals(expectedUniqueScheduleList, uniqueScheduleList);
    }

    @Test
    public void setSchedules_listWithDuplicateSchedules_throwsDuplicateScheduleException() {
        List<Schedule> listWithDuplicateSchedules = Arrays.asList(ALICE_WORK, ALICE_WORK);
        thrown.expect(DuplicateScheduleException.class);
        uniqueScheduleList.setSchedules(listWithDuplicateSchedules);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueScheduleList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void hashCode_validSchedule_correctHashCodeRepresentation() {
        uniqueScheduleList.add(ALICE_WORK);
        Schedule sameAlice = new ScheduleBuilder(ALICE_WORK).build();
        List<Schedule> expected = Arrays.asList(sameAlice);
        assertEquals(uniqueScheduleList.hashCode(), expected.hashCode());
    }


    //Iterator has been tested by java, we will just run a few test to check if iterator is returned from the method
    @Test
    public void iterator_emptyUniqueScheduleList_hasNoNextSchedule() {
        assertFalse(uniqueScheduleList.iterator().hasNext());
    }

    @Test
    public void iterator_nextScheduleEmptyUniqueScheduleList_throwsNoSuchElementException() {
        Assert.assertThrows(NoSuchElementException.class, () -> uniqueScheduleList.iterator().next());
    }
}
