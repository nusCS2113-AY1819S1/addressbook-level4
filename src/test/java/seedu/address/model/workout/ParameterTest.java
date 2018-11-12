package seedu.address.model.workout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;
import static seedu.address.testutil.TypicalParameters.TYPE_PARAMETER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ParameterBuilder;

public class ParameterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        // same values -> returns true
        Parameter nameCopy = new ParameterBuilder(NAME_PARAMETER).build();
        assertTrue(NAME_PARAMETER.equals(nameCopy));

        // same object -> returns true
        assertTrue(NAME_PARAMETER.equals(NAME_PARAMETER));

        // null -> returns false
        assertFalse(NAME_PARAMETER.equals(null));

        // different type of object -> returns false
        assertFalse(NAME_PARAMETER.equals(5));

        // different parameter -> returns false
        assertFalse(NAME_PARAMETER.equals(TYPE_PARAMETER));

        // different prefix -> returns false
        Parameter editedNameParameter = new ParameterBuilder(NAME_PARAMETER).withPrefix("type/").build();
        assertFalse(NAME_PARAMETER.equals(editedNameParameter));

        // different value -> returns false
        editedNameParameter = new ParameterBuilder(NAME_PARAMETER).withValue(VALID_NAME_BOB_WORKOUT).build();
        assertFalse(NAME_PARAMETER.equals(editedNameParameter));

    }
}
