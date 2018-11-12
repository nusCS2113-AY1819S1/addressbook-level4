package seedu.address.model.workout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;
import static seedu.address.testutil.TypicalParameters.TYPE_PARAMETER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.workout.exceptions.DuplicateParameterException;
import seedu.address.model.workout.exceptions.ParameterNotFoundException;

public class UniqueParameterListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueParameterList uniqueParameterList = new UniqueParameterList();

    @Test
    public void contains_nullParameter_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueParameterList.contains(null);
    }

    @Test
    public void contains_parameterNotInList_returnsFalse() {
        assertFalse(uniqueParameterList.contains(NAME_PARAMETER));
    }

    @Test
    public void contains_parameterInList_returnsTrue() {
        uniqueParameterList.add(NAME_PARAMETER);
        assertTrue(uniqueParameterList.contains(NAME_PARAMETER));
    }
    /*
    @Test
    public void contains_workoutWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWorkoutList.add(ALICE_WORKOUT);
        Workout editedAlice = new WorkoutBuilder(ALICE_WORKOUT).withAddress(VALID_ADDRESS_BOB)
        .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueWorkoutList.contains(editedAlice));
    }
    */
    @Test
    public void add_nullParameter_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueParameterList.add(null);
    }

    @Test
    public void add_duplicateParameter_throwsDuplicateParameterException() {
        uniqueParameterList.add(NAME_PARAMETER);
        thrown.expect(DuplicateParameterException.class);
        uniqueParameterList.add(NAME_PARAMETER);
    }

    @Test
    public void remove_nullParameter_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueParameterList.remove(null);
    }

    @Test
    public void remove_parameterDoesNotExist_throwsParameterNotFoundException() {
        thrown.expect(ParameterNotFoundException.class);
        uniqueParameterList.remove(NAME_PARAMETER);
    }

    @Test
    public void remove_existingParameter_removesParameter() {
        uniqueParameterList.add(NAME_PARAMETER);
        uniqueParameterList.remove(NAME_PARAMETER);
        UniqueParameterList expectedUniqueParameterList = new UniqueParameterList();
        assertEquals(expectedUniqueParameterList, uniqueParameterList);
    }

    @Test
    public void setParameters_nullUniqueParameterList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueParameterList.setParameters((UniqueParameterList) null);
    }

    @Test
    public void setParameters_uniqueParameterList_replacesOwnListWithProvidedUniqueParameterList() {
        uniqueParameterList.add(NAME_PARAMETER);
        UniqueParameterList expectedUniqueParameterList = new UniqueParameterList();
        expectedUniqueParameterList.add(TYPE_PARAMETER);
        uniqueParameterList.setParameters(expectedUniqueParameterList);
        assertEquals(expectedUniqueParameterList, uniqueParameterList);
    }

    @Test
    public void setParameters_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueParameterList.setParameters((List<Parameter>) null);
    }

    @Test
    public void setParameters_list_replacesOwnListWithProvidedList() {
        uniqueParameterList.add(NAME_PARAMETER);
        List<Parameter> parameterList = Collections.singletonList(TYPE_PARAMETER);
        uniqueParameterList.setParameters(parameterList);
        UniqueParameterList expectedUniqueParameterList = new UniqueParameterList();
        expectedUniqueParameterList.add(TYPE_PARAMETER);
        assertEquals(expectedUniqueParameterList, uniqueParameterList);
    }

    @Test
    public void setParameters_listWithDuplicateParameters_throwsDuplicateParameterException() {
        List<Parameter> listWithDuplicateParameters = Arrays.asList(NAME_PARAMETER, NAME_PARAMETER);
        thrown.expect(DuplicateParameterException.class);
        uniqueParameterList.setParameters(listWithDuplicateParameters);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueParameterList.asUnmodifiableObservableList().remove(0);
    }
}
