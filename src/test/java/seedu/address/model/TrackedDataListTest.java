package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.ParameterBuilder.DEFAULT_NAME;
import static seedu.address.testutil.ParameterBuilder.DEFAULT_PREFIX;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.exceptions.DuplicateParameterException;
import seedu.address.testutil.ParameterBuilder;

public class TrackedDataListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TrackedDataList trackedDataList = new TrackedDataList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), trackedDataList.getTrackedDataList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        trackedDataList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTrackedDataList_replacesData() {
        TrackedDataList newData = getTypicalTrackedDataList();
        trackedDataList.resetData(newData);
        assertEquals(newData, trackedDataList);
    }

    @Test
    public void resetData_withDuplicateParameters_throwsDuplicateParameterException() {
        // Two parameters with the same fields
        Parameter editedNameParameter = new ParameterBuilder().withPrefix(DEFAULT_PREFIX)
                .withValue(DEFAULT_NAME).build();
        List<Parameter> newParameters = Arrays.asList(NAME_PARAMETER, editedNameParameter);
        TrackedDataListStub newData = new TrackedDataListStub(newParameters);

        thrown.expect(DuplicateParameterException.class);
        trackedDataList.resetData(newData);
    }

    @Test
    public void hasParameter_nullParameter_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        trackedDataList.hasParameter(null);
    }

    @Test
    public void hasParameter_parameterNotInTrackedDataList_returnsFalse() {
        assertFalse(trackedDataList.hasParameter(NAME_PARAMETER));
    }

    @Test
    public void hasParameter_parameterInTrackedDataList_returnsTrue() {
        trackedDataList.addParameter(NAME_PARAMETER);
        assertTrue(trackedDataList.hasParameter(NAME_PARAMETER));
    }

    @Test
    public void hasParameter_parameterWithSameFieldsInTrackedDataList_returnsTrue() {
        trackedDataList.addParameter(NAME_PARAMETER);
        Parameter editedNameParameter = new ParameterBuilder().withPrefix(DEFAULT_PREFIX)
                .withValue(DEFAULT_NAME).build();
        assertTrue(trackedDataList.hasParameter(editedNameParameter));
    }

    @Test
    public void getTrackedDataList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        trackedDataList.getTrackedDataList().remove(0);
    }

    /**
     * A stub ReadOnlyTrackedDataList whose parameters list can violate interface constraints.
     */
    private static class TrackedDataListStub implements ReadOnlyTrackedDataList {
        private final ObservableList<Parameter> parameters = FXCollections.observableArrayList();

        TrackedDataListStub(Collection<Parameter> parameters) {
            this.parameters.setAll(parameters);
        }

        @Override
        public ObservableList<Parameter> getTrackedDataList() {
            return parameters;
        }
    }

}
