package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditItemDescriptor;
import seedu.address.testutil.EditItemDescriptorBuilder;

public class EditItemDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditItemDescriptor descriptorWithSameValues = new EditItemDescriptor(DESC_ARDUINO);
        assertTrue(DESC_ARDUINO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ARDUINO.equals(DESC_ARDUINO));

        // null -> returns false
        assertFalse(DESC_ARDUINO.equals(null));

        // different types -> returns false
        assertFalse(DESC_ARDUINO.equals(5));

        // different values -> returns false
        assertFalse(DESC_ARDUINO.equals(DESC_RPLIDAR));

        // different name -> returns false
        EditItemDescriptor editedArduino =
                new EditItemDescriptorBuilder(DESC_ARDUINO).withName(VALID_NAME_RPLIDAR).build();
        assertFalse(DESC_ARDUINO.equals(editedArduino));

        // different quantity -> returns false
        editedArduino = new EditItemDescriptorBuilder(DESC_ARDUINO).withQuantity(VALID_QUANTITY_RPLIDAR).build();
        assertFalse(DESC_ARDUINO.equals(editedArduino));

        // different min_quantity -> returns false
        editedArduino = new EditItemDescriptorBuilder(DESC_ARDUINO).withMinQuantity(VALID_MIN_QUANTITY_RPLIDAR).build();
        assertFalse(DESC_ARDUINO.equals(editedArduino));


        // different tags -> returns false
        editedArduino = new EditItemDescriptorBuilder(DESC_ARDUINO).withTags(VALID_TAG_LAB1).build();
        assertTrue(DESC_ARDUINO.equals(editedArduino));
    }

}
