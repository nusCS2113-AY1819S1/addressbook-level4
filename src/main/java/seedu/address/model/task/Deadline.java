package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

//@@author emobeany

/**
 * Represents a deadline in the task book.
 * Guarantees: field values are validated, immutable, details are present and not null.
 */

public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Deadline can only have dd/mm/yyyy format";
    public static final String MESSAGE_CONTAINS_ILLEGAL_CHARACTERS =
            "Deadline can only have dd/mm/yyyy format";

    private final String day;
    private final String month;
    private String year;

    public Deadline(String day, String month, String year) {
        this.day = day.replaceFirst("^0+(?!$)", "");
        this.month = month.replaceFirst("^0+(?!$)", "");
        this.year = year.replaceFirst("^0+(?!$)", "");
    }

    public Deadline(String day, String month) {
        this.day = day.replaceFirst("^0+(?!$)", "");
        this.month = month.replaceFirst("^0+(?!$)", "");
    }

    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidFormat(deadline), MESSAGE_DEADLINE_CONSTRAINTS);
        String[] entries = deadline.split("/");
        this.day = entries[0].replaceFirst("^0+(?!$)", "");
        this.month = entries[1].replaceFirst("^0+(?!$)", "");
        if (entries.length == 3) {
            this.year = entries[2].replaceFirst("^0+(?!$)", "");
        }
    }

    public static boolean isValidFormat(String deadline) {
        return deadline.split("/").length >= 2;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    //@@author ChanChunCheong
    public Date getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateAsString = toString();
        Date date = null;
        try {
            date = dateFormat.parse(dateAsString);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return date;
    }
    //@@author

    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Returns false if any fields contains illegal characters.
     */
    public static boolean containsIllegalCharacters(String deadline) {
        String[] entries = deadline.split("/");

        String day = entries[0];
        String month = entries[1];
        String year = entries[2];

        return (!isNumeric(day) || !isNumeric(month) || !isNumeric(year));
    }

    /**
     * Returns false if any fields are not within the limits (not a valid date).
     */
    public static boolean isValidDeadline(String deadline) {
        String[] entries = deadline.split("/");
        if (entries.length != 3) {
            return false;
        }
        int day = Integer.parseInt(entries[0]);
        int month = Integer.parseInt(entries[1]);
        int year = Integer.parseInt(entries[2]);


        if (month < 1 || month > 12) {
            return false;
        } else if (year < 2018 || year > 9999) {
            return false;
        } else if (isMonthWith30Days(month)) {
            return (day > 0 && day < 31);
        } else if (isMonthWith31Days(month)) {
            return (day > 0 && day < 32);
        } else {
            if (isLeapYear(year)) {
                return (day > 0 && day < 30);
            } else {
                return (day > 0 && day < 29);
            }
        }

        /*
        if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
            return false;
        } else if (Integer.parseInt(year) < 2018 || Integer.parseInt(year) > 9999) {
            return false;
        } else if (monthsWith30Days.contains(Integer.parseInt(month))) {
            return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 31);
        } else if (monthsWith31Days.contains(Integer.parseInt(month))) {
            return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 32);
        } else {
            if (isLeapYear(Integer.parseInt(year))) {
                return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 30);
            } else {
                return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 29);
            }
        }
        */

    }

    //@@author ChanChunCheong
    /**
     * Defers the deadline of the task.
     */
    public Deadline deferDeadline(int deferredDays) {
        int year = Integer.parseInt(getYear());
        int month = Integer.parseInt(getMonth());
        int day = Integer.parseInt(getDay());
        int baseDays;
        int numofMonths = 0;
        //int numofYears = 0;
        int newDay;
        int updatedDay;

        if (isMonthWith30Days(month)) {
            baseDays = 30;
        } else if (isMonthWith31Days(month)) {
            baseDays = 31;
        } else {
            if (isLeapYear(year)) {
                baseDays = 29;
            } else {
                baseDays = 28;
            }
        }

        newDay = day + deferredDays;
        updatedDay = newDay % baseDays;
        // if daystoAdd == 0 then day = end of the month.
        if (updatedDay == 0) {
            day = baseDays;
        } else {
            day = updatedDay;
        }

        //Count the number of months added
        while (newDay > baseDays) {
            newDay = day - baseDays;
            numofMonths++;
        }

        if (numofMonths > 0) {
            if (month + numofMonths > 12) {
                year = year + 1;
            }
        }

        month = (month + numofMonths) % 12;
        if (month == 0) {
            //month is December
            month = 12;
        }

        String deferredDay = Integer.toString(day);
        String deferredMonth = Integer.toString(month);
        String deferredYear = Integer.toString(year);
        Deadline deferredDeadline = new Deadline(deferredDay, deferredMonth, deferredYear);
        return deferredDeadline;
    }

    /**
     * Returns false if any fields are not within the limits (not a valid date).
     */
    public static boolean isMonthWith30Days(int month) {
        ArrayList<Integer> monthsWith30Days = new ArrayList<>(Arrays.asList(4, 6, 9, 11));
        return monthsWith30Days.contains(month);
    }

    /**
     * Returns false if any fields are not within the limits (not a valid date).
     */
    public static boolean isMonthWith31Days(int month) {
        ArrayList<Integer> monthsWith31Days = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
        return monthsWith31Days.contains(month);
    }

    //@@author emobeany
    @Override
    public int hashCode() {
        // custom fields hashing
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear());
        return builder.toString();
    }

    /**
     * Referenced online: Checking if String is numeric
     * @param s
     * @return true if String is completely numeric
     */
    public static boolean isNumeric(String s) {
        //s.matches("[-+]?\\d*\\.?\\d+");
        return s != null && s.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Referenced online: Checking if year is a leap year
     * @param year selected
     * @return true if year is a leap year
     */
    public static boolean isLeapYear(Integer year) {
        return ((year % 400 == 0 || year % 100 != 0) && year % 4 == 0);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (object instanceof Deadline) {
            Deadline otherDeadline = (Deadline) object;
            return otherDeadline.day.equals(this.day) && otherDeadline.month.equals(this.month)
                    && otherDeadline.year.equals(this.year);
        }
        return false;
    }
}
