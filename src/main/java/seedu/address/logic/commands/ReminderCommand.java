//@@author cqinkai
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.DateTimeUtil.daysDiff;
import static seedu.address.model.event.EventContainsKeywordsPredicate.checkAttendeeKeywordsMatchEventAttendee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.SendEventReminder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Sends reminders to logged-in user based on upcoming events that the user has registered for
 * using the {@code RegisterCommand}.
 * Upcoming events defined as events that will be occurring within a 24hours time frame.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_REMINDER = "Reminder(s) sent.";
    public static final String MESSAGE_NO_UPCOMING_EVENTS = "You do not have any upcoming events.";
    public static final String MESSAGE_NOT_LOGGED_IN = "You are not logged in!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        int index = 0;

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        List<Event> lastShownList = model.getFilteredEventList();
        List<String> userStringAsList = new ArrayList<>();
        List<Event> upcomingEvents = new ArrayList<>();
        Date currentDate = new Date();

        String user = model.getUsername().toString();
        userStringAsList.add(user.trim());

        for (Event event : lastShownList) {
            Index targetIndex = Index.fromZeroBased(index++);

            if ((checkAttendeeKeywordsMatchEventAttendee(userStringAsList, event)) && checkEventIsUpcoming(event)) {
                upcomingEvents.add(event);
                EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
                EventsCenter.getInstance().post(new SendEventReminder(event.getName().toString(),
                        event.getDateTime().toString(),
                        String.valueOf(daysDiff(event.getDateTime().dateTime, currentDate, TimeUnit.MILLISECONDS))));
            }
        }

        if (upcomingEvents.isEmpty()) {
            return new CommandResult(MESSAGE_NO_UPCOMING_EVENTS);
        }

        return new CommandResult(MESSAGE_REMINDER);
    }

    /**
     * Checks if the {@code Event} is upcoming
     * based on the criteria that the {@code Event} will be occurring in the next 24 hours.
     * @param event
     */
    private boolean checkEventIsUpcoming(Event event) {
        Date currentDate = new Date();
        Date eventDate = event.getDateTime().dateTime;

        return ((eventDate.after(currentDate)) && (daysDiff(eventDate, currentDate, TimeUnit.MILLISECONDS) <= 24));
    }
}
