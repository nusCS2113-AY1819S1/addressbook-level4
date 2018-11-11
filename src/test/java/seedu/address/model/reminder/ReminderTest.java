package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_AGENDA;
import static seedu.address.testutil.TypicalReminders.REMINDER1;
import static seedu.address.testutil.TypicalReminders.REMINDER2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ReminderBuilder;

//@@author junweiljw
public class ReminderTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameReminder() {
        // same object -> returns true
        assertTrue(REMINDER1.isSameReminder(REMINDER1));

        // null -> returns false
        assertFalse(REMINDER1.isSameReminder(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder reminder1Copy = new ReminderBuilder(REMINDER1).build();
        assertTrue(REMINDER1.equals(reminder1Copy));

        // same object -> returns true
        assertTrue(REMINDER1.equals(REMINDER1));

        // null -> returns false
        assertFalse(REMINDER1.equals(null));

        // different type -> returns false
        assertFalse(REMINDER1.equals(5));

        // different reminder -> returns false
        assertFalse(REMINDER1.equals(REMINDER2));

        // different title -> returns false
        Reminder editedREMINDER1 = new ReminderBuilder(REMINDER1).withTitle(VALID_REMINDER2_TITLE).build();
        assertFalse(REMINDER1.equals(editedREMINDER1));

        // different date -> returns false
        editedREMINDER1 = new ReminderBuilder(REMINDER1).withDate(VALID_REMINDER2_DATE).build();
        assertFalse(REMINDER1.equals(editedREMINDER1));

        // different time -> returns false
        editedREMINDER1 = new ReminderBuilder(REMINDER1).withTime(VALID_REMINDER2_TIME).build();
        assertFalse(REMINDER1.equals(editedREMINDER1));

        // different agenda -> returns false
        editedREMINDER1 = new ReminderBuilder(REMINDER1).withAgenda(VALID_REMINDER2_AGENDA).build();
        assertFalse(REMINDER1.equals(editedREMINDER1));
    }
}
