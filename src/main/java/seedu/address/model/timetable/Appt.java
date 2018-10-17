package seedu.address.model.timetable;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Appt {
    private final ApptDateTime start;
    private final ApptDateTime end;
    private final ApptVenue venue;
    private final ApptInfo info;
    private final ApptDrName drName;

    public Appt(ApptDateTime start, ApptDateTime end, ApptVenue venue, ApptInfo info, ApptDrName drName) {
        requireAllNonNull(start, end, venue, info, drName);
        this.start = start;
        this.end = end;
        this.venue = venue;
        this.info = info;
        this.drName = drName;
    }

    public ApptDateTime getStart() {
        return start;
    }

    public ApptDateTime getEnd() {
        return end;
    }

    public ApptVenue getVenue() {
        return venue;
    }

    public ApptInfo getInfo() {
        return info;
    }

    public ApptDrName getDrName() {
        return drName;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(start, end, venue, info, drName);
    }

    // temporary
    @Override
    public String toString() {
        return start.toString() + " " + end.toString() + " " + venue.toString() + " " + info.toString() + " "
                + drName.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appt)) {
            return false;
        }

        Appt otherAppt = (Appt) other;
        return otherAppt.getStart().equals(getStart())
                && otherAppt.getEnd().equals(getEnd())
                && otherAppt.getVenue().equals(getVenue())
                && otherAppt.getInfo().equals(getInfo())
                && otherAppt.getDrName().equals(getDrName());
        // will probably also need to add in index/some way to identify patient into equals check
        // multiple patients can have the same appts with all the same details
    }
}
