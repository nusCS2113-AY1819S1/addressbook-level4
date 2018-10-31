package seedu.address.model.classroom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents an attendance for a classroom in the app.
 */
public class Attendance {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should following dd-MM-yyyy format,"
                    + " consisting of 2 numbers denoting day of month,"
                    + " followed by hyphen, followed by 2 numbers denoting month of year,"
                    + " followed by 4 digits denoting the year";
    private String date;
    private ArrayList<String> studentsPresent;

    public Attendance() {
        LocalDate localDate = LocalDate.now();
        date = Attendance.DATE_FORMATTER.format(localDate);
        studentsPresent = new ArrayList<>();
    }

    public Attendance(String date) {
        this.date = date;
        studentsPresent = new ArrayList<>();
    }

    public Attendance(String date, ArrayList<String> studentsPresent) {
        this.date = date;
        this.studentsPresent = studentsPresent;
    }

    /**
     * If the datetime formatter is able to parse the given date, the date will be valid
     */
    public static boolean isValidDate(String date) {
        try {
            DATE_FORMATTER.parse(date);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getStudentsPresent() {
        return studentsPresent;
    }

    public void setStudentsPresent(ArrayList<String> studentsPresent) {
        this.studentsPresent = studentsPresent;
    }
}
