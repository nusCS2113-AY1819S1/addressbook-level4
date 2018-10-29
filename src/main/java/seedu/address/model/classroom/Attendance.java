package seedu.address.model.classroom;

import java.util.ArrayList;

/**
 * Represents an attendance for a classroom in the app.
 */
public class Attendance {
    private String date;
    private ArrayList<String> studentsPresent = new ArrayList<>();

    public Attendance() {
    }

    public Attendance(String date, ArrayList<String> studentsPresent) {
        this.date = date;
        this.studentsPresent = studentsPresent;
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
