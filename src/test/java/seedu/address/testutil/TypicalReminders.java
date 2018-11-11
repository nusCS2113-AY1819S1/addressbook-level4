package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER1_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER1_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER1_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER1_AGENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER2_AGENDA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.reminder.Reminder;

//@@author junweiljw
/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {
    public static final Reminder REMINDER_A = new ReminderBuilder().withTitle("Typical mundane meeting")
            .withDate("08022018")
            .withTime("2359HRS")
            .withAgenda("Just to fulfill mandatory project requirements").build();
    public static final Reminder REMINDER_B = new ReminderBuilder().withTitle("Final mundane meeting")
            .withDate("05122018")
            .withTime("2300HRS")
            .withAgenda("To say goodbye and thank you to members").build();

    // Manually added - reminder's details found in {@code CommandTestUtil}
    public static final Reminder REMINDER1 = new ReminderBuilder().withTitle(VALID_REMINDER1_TITLE)
            .withDate(VALID_REMINDER1_DATE)
            .withTime(VALID_REMINDER1_TIME)
            .withAgenda(VALID_REMINDER1_AGENDA).build();
    public static final Reminder REMINDER2 = new ReminderBuilder().withTitle(VALID_REMINDER2_TITLE)
            .withDate(VALID_REMINDER2_DATE)
            .withTime(VALID_REMINDER2_TIME)
            .withAgenda(VALID_REMINDER2_AGENDA).build();

    public static final String KEYWORD_MATCHING_REMINDER = "mundane"; // A keyword that matches REMINDER_A

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical reminders.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Reminder reminder : getTypicalReminders()) {
            ab.addReminder(reminder);
        }
        return ab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(REMINDER_A, REMINDER_B));
    }
}
