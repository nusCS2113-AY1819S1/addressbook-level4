package seedu.divelog.model.dive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.json.JSONException;

import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.util.CompareUtil;
import seedu.divelog.logic.pressuregroup.PressureGroupLogic;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

/**
 * This class represents a single dive session
 */
public class DiveSession implements Comparable {
    private final OurDate dateStart;
    private final Time start;
    private final Time safetyStop;
    private final OurDate dateEnd;
    private final Time end;
    private final Location location;
    private final DepthProfile depthProfile;
    private final TimeZone timezone;
    private Date dateTime;
    //These are recomputed later
    private PressureGroup pressureGroupAtBeginning;
    private PressureGroup pressureGroupAtEnd;


    public DiveSession(
            OurDate dateStart, Time start, Time safetyStop,
            OurDate dateEnd, Time end,
            PressureGroup pressureGroupAtBeginning,
            PressureGroup pressureGroupAtEnd, Location location,
            DepthProfile depthProfile, TimeZone timezone) {
        this.dateStart = dateStart;
        this.start = start;
        this.safetyStop = safetyStop;
        this.dateEnd = dateEnd;
        this.end = end;
        this.pressureGroupAtBeginning = pressureGroupAtBeginning;
        this.pressureGroupAtEnd = pressureGroupAtEnd;
        this.location = location;
        this.depthProfile = depthProfile;
        this.timezone = timezone;
        String dateTimeString = this.getDateStart().getOurDateString().concat(this.getStart().getTimeString());
        try {
            this.dateTime = new SimpleDateFormat("ddMMyyyyHHmm").parse(dateTimeString);
        } catch (ParseException e) {
            throw new AssertionError("Invalid time and date were parsed to DiveSession!");
        }
    }

    public OurDate getDateStart() {
        return dateStart;
    }

    public OurDate getDateEnd() {
        return dateEnd;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public PressureGroup getPressureGroupAtBeginning() {
        return pressureGroupAtBeginning;
    }

    public PressureGroup getPressureGroupAtEnd() {
        return pressureGroupAtEnd;
    }

    public void setPressureGroupAtBeginning(PressureGroup pressureGroup) {
        pressureGroupAtBeginning = pressureGroup;
    }

    public Time getSafetyStop() {
        return safetyStop;
    }

    public DepthProfile getDepthProfile() {
        return depthProfile;
    }

    public Location getLocation() {
        return location;
    }

    public TimeZone getTimeZone() {
        return timezone;
    }

    public Date getStartDateTime() {
        return dateTime;
    }

    public Date getEndDateTime() {
        String dateTimeString = this.getDateEnd().getOurDateString().concat(this.getEnd().getTimeString());
        try {
            return new SimpleDateFormat("ddMMyyyyHHmm").parse(dateTimeString);
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * Gets the dive start time in UTC as a java Date object
     * @return The date as the UTC date.
     */
    public Date getDiveUtcDateStart() {
        String date = getDateStart().getOurDateString();
        String time = getStart().getTimeString();
        int timeZone = getTimeZone().getTimeZone();
        try {
            return CompareUtil.convertTimeToUtc(time, date, timeZone);
        } catch (ParseException pe) {
            throw new AssertionError("Time passed to DiveSession should be checked beforehand");
        }
    }

    /**
     * Gets the dive start time in UTC as a java Date object
     * @return The date as the UTC date.
     */
    public Date getDiveUtcDateEnd() {
        String date = getDateEnd().getOurDateString();
        String time = getEnd().getTimeString();
        int timeZone = getTimeZone().getTimeZone();
        try {
            return CompareUtil.convertTimeToUtc(time, date, timeZone);
        } catch (ParseException pe) {
            throw new AssertionError("Time passed to DiveSession should be checked beforehand");
        }
    }


    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof DiveSession)) {
            return false;
        }

        DiveSession other = (DiveSession) obj;

        return other.getLocation().equals(getLocation())
                && other.getDateStart().equals(getDateStart())
                && other.getDateEnd().equals(getDateEnd())
                && other.getStart().equals(getStart())
                && other.getSafetyStop().equals(getSafetyStop())
                && other.getPressureGroupAtBeginning().equals(getPressureGroupAtBeginning())
                && other.getPressureGroupAtEnd().equals(getPressureGroupAtEnd())
                && other.getEnd().equals(getEnd())
                && other.getDepthProfile().equals(getDepthProfile())
                && other.getTimeZone().equals(getTimeZone());
    }

    /**
     * Recomputes the end pressure group given Non-Repeated Dive
     * @throws LimitExceededException when dives exceed threshold
     * @throws InvalidTimeException when time given is in invalid format. This should never happen.
     */
    public void computePressureGroupNonRepeated() throws LimitExceededException, InvalidTimeException {
        try {
            float actualBottomTime = CompareUtil.checkTimeDifference(start.getTimeString(), end.getTimeString(),
                    dateStart.getOurDateString(), dateEnd.getOurDateString());
            pressureGroupAtEnd = PressureGroupLogic.computePressureGroupFirstDive(depthProfile, actualBottomTime);
        } catch (ParseException pe) {
            throw new InvalidTimeException("Something went wrong with the time format");
        }
    }

    /**
     * Recomputes the end pressure group given Repeated Dive
     * @throws LimitExceededException when it exceeds the thresholds
     * @throws InvalidTimeException when time given is in invalid format. This should never happen.
     */
    public void computePressureGroupComputeRepeated() throws LimitExceededException, InvalidTimeException {
        try {
            float actualBottomTime = CompareUtil.checkTimeDifference(start.getTimeString(), end.getTimeString(),
                    dateStart.getOurDateString(), dateEnd.getOurDateString());
            pressureGroupAtEnd = PressureGroupLogic.computePressureGroup(depthProfile, actualBottomTime,
                    pressureGroupAtBeginning);
        } catch (LimitExceededException l) {
            throw l;
        } catch (ParseException e) {
            //Shouldn't ever be reached
            Logger log = LogsCenter.getLogger(DiveSession.class);
            log.severe("Something went wrong with the time format");
            throw new InvalidTimeException("Something went wrong with the time format");
        } catch (JSONException je) {
            Logger log = LogsCenter.getLogger(DiveSession.class);
            log.severe("Something went wrong decoding the JSON!!");
        }
    }

    /**
     * Measures the time between different DiveSessions.
     * @return time in minutes between dive sessions
     * @throws InvalidTimeException When time format is incorrect. Should never reach this stage.
     */
    public float getTimeBetweenDiveSession(DiveSession other) {
        return (float) Math.floor((other.getDiveUtcDateStart().getTime() - getDiveUtcDateEnd().getTime()) / 60000.0f);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dive Location: " + getLocation().getLocationName() + "\n");
        stringBuilder.append("\tStart: " + getDateStart().getOurDateString() + " " + getStart().getTimeString() + "\n");
        stringBuilder.append("\tEnd: " + getDateEnd().getOurDateString() + " " + getEnd().getTimeString() + "\n");
        stringBuilder.append("\tPressureGroup: " + getPressureGroupAtBeginning().getPressureGroup() + "->"
                + getPressureGroupAtEnd().getPressureGroup() + "\n");
        stringBuilder.append("\tDepth: " + getDepthProfile().getDepth() + "\n");
        stringBuilder.append("\tSafety stop: " + getSafetyStop().getTimeString());
        stringBuilder.append("\tTime Zone:" + getTimeZone().getTimeZoneString());
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Object o) {

        if (!(o instanceof DiveSession)) {
            throw new ClassCastException();
        }

        DiveSession other = (DiveSession) o;

        Date otherDate = other.getDiveUtcDateStart();
        Date myDate = getDiveUtcDateStart();
        return myDate.compareTo(otherDate);

    }
}
