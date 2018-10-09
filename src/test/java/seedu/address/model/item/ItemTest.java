package seedu.address.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RPLIDAR;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.RPLIDAR;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ItemBuilder;

public class ItemTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Item item = new ItemBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        item.getTags().remove(0);
    }

    @Test
    public void isSameItem() {
        // same object -> returns true
        assertTrue(ARDUINO.isSameItem(ARDUINO));

        // null -> returns false
        assertFalse(ARDUINO.isSameItem(null));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Item arduinoCopy = new ItemBuilder(ARDUINO).build();
        assertTrue(ARDUINO.equals(arduinoCopy));

        // same object -> returns true
        assertTrue(ARDUINO.equals(ARDUINO));

        // null -> returns false
        assertFalse(ARDUINO.equals(null));

        // different type -> returns false
        assertFalse(ARDUINO.equals(5));

        // different item -> returns false
        assertFalse(ARDUINO.equals(RPLIDAR));

        // different name -> returns false
        Item editedArduino = new ItemBuilder(ARDUINO).withName(VALID_NAME_RPLIDAR).build();
        assertFalse(ARDUINO.equals(editedArduino));

    }
}
