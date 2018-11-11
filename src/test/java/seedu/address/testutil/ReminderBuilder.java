package seedu.address.testutil;

import seedu.address.model.todo.Title;
//import seedu.address.model.person.Date;
import seedu.address.model.person.Time;
//import seedu.address.model.reminder.Title;
import seedu.address.model.reminder.Date;
//import seedu.address.model.reminder.Time;
import seedu.address.model.reminder.Agenda;
import seedu.address.model.reminder.Reminder;

//@@author junweiljw
/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_TITLE = "Typical mundane meetings";
    public static final String DEFAULT_DATE = "08022018";
    public static final String DEFAULT_TIME = "2369HRS";
    public static final String DEFAULT_AGENDA = "To discuss the next EASY presentation";

    private Title title;
    private Date date;
    private Time time;
    private Agenda agenda;

    public ReminderBuilder() {
        title = new Title(DEFAULT_TITLE);
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        agenda = new Agenda(DEFAULT_AGENDA);
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        title = reminderToCopy.getTitle();
        date = reminderToCopy.getDate();
        time = reminderToCopy.getTime();
        agenda = reminderToCopy.getAgenda();
    }

    /**
     * Sets the {@code Title} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Agenda} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withAgenda(String agenda) {
        this.agenda = new Agenda(agenda);
        return this;
    }

    public Reminder build() {
        return new Reminder(title, date, time, agenda);
    }

}
