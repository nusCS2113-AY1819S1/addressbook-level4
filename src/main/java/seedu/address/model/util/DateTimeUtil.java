package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.commands.EditHistCommand;
import seedu.address.model.medhistory.MedHistory;

/**
 * Helper functions for LocalDate and LocalDateTime conversion to and from String
 */
public class DateTimeUtil {
    public static final String DATE_CONSTRAINTS = "Dates should be of the format DD-MM-YYYY";
    public static final String DATE_VALUE_EXCEEDED = "Only dates falling on the year 1900 or later are allowed.";
    public static final String DATE_VALIDATION_REGEX = "[0-9]{2}" + "[-]" + "[0-9]{2}" + "[-]" + "[0-9]{4}";

    public static final String DATE_TIME_CONSTRAINTS =
            "Date and Time should be of the format: " + "DD-MM-YYYY HH:MM";
    public static final String DATE_TIME_VALIDATION_REGEX =
            "[0-9]{2}" + "[-]" + "[0-9]{2}" + "[-]" + "[0-9]{4}" + "[ ]" + "[0-9]{2}" + "[:]" + "[0-9]{2}";

    private static final String DATE_PATTERN = "dd-MM-uuuu";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);
    private static final String DATETIME_PATTERN = "dd-MM-uuuu HH:mm";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);

    private static LocalDate earliestDateAllowed = parseDate("01-01-1900");

    /**
     * convert LocalDate to String
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * convert LocalDateTime to String
     */
    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * convert String to LocalDate
     *
     * @param dateString String to be converted
     * @return parsed local-date, not null
     * @throws DateTimeParseException if String cannot be parsed
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    /**
     * convert String to LocalDateTime
     *
     * @param dateTimeString String to be converted
     * @return parsed local-date-time, not null
     * @throws DateTimeParseException if String cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
    }

    public static LocalDate getEarliestDateAllowed() {
        return earliestDateAllowed;
    }

    /**
     * Check if the date input has valid values
     * @param date String to be checked
     * @throws DateTimeParseException if the values are not valid
     */
    public static void isCorrectDate(String date) throws DateTimeParseException {
        try {
            LocalDate localDate = DateTimeUtil.parseDate(date);
            if (localDate.isBefore(DateTimeUtil.getEarliestDateAllowed())) {
                int index = date.lastIndexOf(date);
                throw new DateTimeParseException(DATE_VALUE_EXCEEDED, date, index);
            }
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException(e.getMessage(), e.getParsedString(), e.getErrorIndex());
        }
    }

    /**
     * Check if the datetime input has valid values
     * @param datetime String to be checked
     * @throws DateTimeParseException if the values are not valid
     */
    public static void isCorrectDateTime(String datetime) throws DateTimeParseException {
        try {
            LocalDateTime localDateTime = DateTimeUtil.parseDateTime(datetime);
            if (localDateTime.toLocalDate().isBefore(DateTimeUtil.getEarliestDateAllowed())) {
                int index = datetime.lastIndexOf(datetime);
                throw new DateTimeParseException(DATE_VALUE_EXCEEDED, datetime, index);
            }
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException(e.getMessage(), e.getParsedString(), e.getErrorIndex());
        }
    }

    /**
     * This method checks if input has a duplicate medical history date for addhist command.
     */
    public static boolean isInvalidMedHistDate(MedHistory medHistory) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate inputDate = LocalDate.parse(medHistory.getMedHistDate().toString(), formatter);

        return (inputDate.isAfter(localDate));
    }

    /**
     * This method checks if input has a duplicate medical history date for edithist command.
     */
    public static boolean isInvalidEditMedHistDate(EditHistCommand.EditHistDescriptor editHistDescriptor) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate inputDate = LocalDate.parse(editHistDescriptor.getMedHistDate().toString(), formatter);

        return (inputDate.isAfter(localDate));
    }
}
