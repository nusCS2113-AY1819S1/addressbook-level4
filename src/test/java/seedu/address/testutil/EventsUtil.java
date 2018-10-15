package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import guitests.GuiRobot;
import javafx.application.Platform;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;

/**
 * Helper methods related to events.
 */
public class EventsUtil {
    /**
     * Posts {@code event} to all registered subscribers. This method will return successfully after the {@code event}
     * has been posted to all subscribers.
     */
    public static void postNow(BaseEvent event) {
        new GuiRobot().interact(() -> EventsCenter.getInstance().post(event));
    }

    /**
     * Posts {@code event} to all registered subscribers at some unspecified time in the future.
     */
    public static void postLater(BaseEvent event) {
        Platform.runLater(() -> EventsCenter.getInstance().post(event));
    }

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddEventCommand(Event event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */

    public static String getEventDetails(Event event)
    {

        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getEventName().fullName + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().value + " ");
        sb.append(PREFIX_DATE + event.getDate().toString() + " ");
        sb.append(PREFIX_START_DATE + event.getStartTime().startTime + " ");
        sb.append(PREFIX_END_DATE + event.getEndTime().endTime);
        return sb.toString();
    }

    /**
     * Returns formatted string based on LocalDate.
     */
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);
        String trimmedFormattedString = formattedString.trim();
        return trimmedFormattedString;
    }
}
