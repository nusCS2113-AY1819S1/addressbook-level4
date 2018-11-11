package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author junweiljw
public class AgendaTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Agenda(null));
    }

    @Test
    public void constructor_invalidAgenda_throwsIllegalArgumentException() {
        String invalidAgenda = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Agenda(invalidAgenda));
    }

    @Test
    public void isValidAgenda() {
        // null agenda
        Assert.assertThrows(NullPointerException.class, () -> Agenda.isValidAgenda(null));

        // invalid agenda
        assertFalse(Agenda.isValidAgenda("")); // empty string
        assertFalse(Agenda.isValidAgenda(" ")); // spaces only
        assertFalse(Agenda.isValidAgenda("2321323241")); // contains only numbers
        assertFalse(Agenda.isValidAgenda("#$@!#@!")); // contains only characters

        // valid date
        assertTrue(Agenda.isValidAgenda("To discuss milestone 1.4")); // correct
        assertTrue(Agenda.isValidAgenda("-")); // one character
        assertTrue(Agenda.isValidAgenda("To discuss the importance of " +
                "having the meeting minutes feature")); // long input
    }
}
