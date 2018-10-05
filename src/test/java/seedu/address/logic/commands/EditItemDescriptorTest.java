package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SENSOR;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_LIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_LIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_LIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditItemDescriptor;
import seedu.address.testutil.EditItemDescriptorBuilder;

public class EditItemDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditItemDescriptor descriptorWithSameValues = new EditItemDescriptor(DESC_SENSOR);
        assertTrue(DESC_SENSOR.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SENSOR.equals(DESC_SENSOR));

        // null -> returns false
        assertFalse(DESC_SENSOR.equals(null));

        // different types -> returns false
        assertFalse(DESC_SENSOR.equals(5));

        // different values -> returns false
        assertFalse(DESC_SENSOR.equals(DESC_LIDAR));

        // different name -> returns false
        EditItemDescriptor editedSensor = new EditItemDescriptorBuilder(DESC_SENSOR).withName(VALID_NAME_LIDAR).build();
        assertFalse(DESC_SENSOR.equals(editedSensor));

        // different quantity -> returns false
        editedSensor = new EditItemDescriptorBuilder(DESC_SENSOR).withQuantity(VALID_QUANTITY_LIDAR).build();
        assertFalse(DESC_SENSOR.equals(editedSensor));

        // different min_quantity -> returns false
        editedSensor = new EditItemDescriptorBuilder(DESC_SENSOR).withMin_quantity(VALID_MIN_QUANTITY_LIDAR).build();
        assertFalse(DESC_SENSOR.equals(editedSensor));


        // different tags -> returns false
        editedSensor = new EditItemDescriptorBuilder(DESC_SENSOR).withTags(VALID_TAG_LAB1).build();
        assertFalse(DESC_SENSOR.equals(editedSensor));
    }
}