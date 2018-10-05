package seedu.address.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalItems.RPLIDAR;
import static seedu.address.testutil.TypicalItems.ARDUINO;

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

        // different phone and email -> returns false
        Item editedAlice = new ItemBuilder(ARDUINO).withPhone(VALID_PHONE_RPLIDAR).withEmail(VALID_EMAIL_RPLIDAR).build();
        assertFalse(ARDUINO.isSameItem(editedAlice));

        // different name -> returns false
        editedAlice = new ItemBuilder(ARDUINO).withName(VALID_NAME_RPLIDAR).build();
        assertFalse(ARDUINO.isSameItem(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ItemBuilder(ARDUINO).withEmail(VALID_EMAIL_RPLIDAR).withAddress(VALID_ADDRESS_RPLIDAR)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ARDUINO.isSameItem(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ItemBuilder(ARDUINO).withPhone(VALID_PHONE_RPLIDAR).withAddress(VALID_ADDRESS_RPLIDAR)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ARDUINO.isSameItem(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ItemBuilder(ARDUINO).withAddress(VALID_ADDRESS_RPLIDAR).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ARDUINO.isSameItem(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Item aliceCopy = new ItemBuilder(ARDUINO).build();
        assertTrue(ARDUINO.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ARDUINO.equals(ARDUINO));

        // null -> returns false
        assertFalse(ARDUINO.equals(null));

        // different type -> returns false
        assertFalse(ARDUINO.equals(5));

        // different item -> returns false
        assertFalse(ARDUINO.equals(RPLIDAR));

        // different name -> returns false
        Item editedAlice = new ItemBuilder(ARDUINO).withName(VALID_NAME_RPLIDAR).build();
        assertFalse(ARDUINO.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ItemBuilder(ARDUINO).withPhone(VALID_PHONE_RPLIDAR).build();
        assertFalse(ARDUINO.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ItemBuilder(ARDUINO).withEmail(VALID_EMAIL_RPLIDAR).build();
        assertFalse(ARDUINO.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ItemBuilder(ARDUINO).withAddress(VALID_ADDRESS_RPLIDAR).build();
        assertFalse(ARDUINO.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ItemBuilder(ARDUINO).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ARDUINO.equals(editedAlice));
    }
}
