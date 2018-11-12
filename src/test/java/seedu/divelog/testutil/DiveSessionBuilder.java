package seedu.divelog.testutil;

import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.Location;
import seedu.divelog.model.dive.OurDate;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.dive.Time;
import seedu.divelog.model.dive.TimeZone;

//@@author arjo129
/**
 * A utility class to help with building DiveSession objects.
 */
public class DiveSessionBuilder {

    private static final String DEFAULT_START = "0700";
    private static final String DEFAULT_END = "0800";
    private static final String DEFAULT_SAFETY_STOP = "0745";
    private static final String DEFAULT_PG_START = "A";
    private static final String DEFAULT_PG_END = "L";
    private static final String DEFAULT_LOCATION = "Bali";
    private static final String DEFAULT_DATE_START = "01012019";
    private static final String DEFAULT_DATE_END = "01012019";
    private static final String DEFAULT_TIMEZONE = "+8";
    private static final float DEFAULT_DEPTH = 5;

    private Time start;
    private Time safetyStop;
    private Time end;
    private PressureGroup pressureGroupAtBeginning;
    private PressureGroup pressureGroupAtEnd;
    private Location location;
    private DepthProfile depthProfile;
    private OurDate dateEnd;
    private OurDate dateStart;
    private TimeZone timezone;

    public DiveSessionBuilder() {
        start = new Time(DEFAULT_START);
        end = new Time(DEFAULT_END);
        safetyStop = new Time(DEFAULT_SAFETY_STOP);
        pressureGroupAtBeginning = new PressureGroup(DEFAULT_PG_START);
        pressureGroupAtEnd = new PressureGroup(DEFAULT_PG_END);
        location = new Location(DEFAULT_LOCATION);
        depthProfile = new DepthProfile(DEFAULT_DEPTH);
        dateEnd = new OurDate(DEFAULT_DATE_START);
        dateStart = new OurDate(DEFAULT_DATE_END);
        timezone = new TimeZone(DEFAULT_TIMEZONE);
    }

    /**
     * Initializes the DiveSessionBuilder with the data of {@code diveToCopy}.
     */
    public DiveSessionBuilder(DiveSession diveToCopy) {
        start = diveToCopy.getStart();
        end = diveToCopy.getEnd();
        safetyStop = diveToCopy.getSafetyStop();
        pressureGroupAtEnd = diveToCopy.getPressureGroupAtEnd();
        pressureGroupAtBeginning = diveToCopy.getPressureGroupAtBeginning();
        location = diveToCopy.getLocation();
        depthProfile = diveToCopy.getDepthProfile();
        dateEnd = diveToCopy.getDateEnd();
        dateStart = diveToCopy.getDateStart();
        timezone = diveToCopy.getTimeZone();
    }
    /**
     * Sets the start time of the {@code DiveSession} that we are building.
     * @param time -  the time the dive started (24hr format)
     */
    public DiveSessionBuilder withStart(String time) {
        this.start = new Time(time);
        return this;
    }

    /**
     * Sets the end time of the {@code DiveSession} that we are building.
     * @param time -  the time the dive started (24hr format)
     */
    public DiveSessionBuilder withEnd(String time) {
        this.end = new Time(time);
        return this;
    }

    /**
     * Sets the safety stop time of the {@code DiveSession} that we are building
     * @param time -  the time the dive started. (24hr format)
     */
    public DiveSessionBuilder withSafetyStop(String time) {
        this.safetyStop = new Time(time);
        return this;
    }

    /**
     * Sets the starting pressure group of the {@code DiveSession} that we are building.
     * @param pressureGroup - The pressure group. Must be a letter between A-Z. (case-insensitive)
     */
    public DiveSessionBuilder withPressureGroupAtBeginning(String pressureGroup) {
        this.pressureGroupAtBeginning = new PressureGroup(pressureGroup);
        return this;
    }

    /**
     * Sets the ending pressure group of the {@code DiveSession} that we are building.
     * @param pressureGroup - The pressure group. Must be a letter between A-Z. (case-insensitive)
     */
    public DiveSessionBuilder withPressureGroupAtEnd(String pressureGroup) {
        this.pressureGroupAtEnd = new PressureGroup(pressureGroup);
        return this;
    }

    /**
     * Sets the location of the {@code DiveSession} that we are building.
     * @param location - The location of the dive
     */
    public DiveSessionBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the depth of the {@code DiveSession} that we are building.
     * @param depth - The depth of the dive in meters. Must be greater than 0;
     */
    public DiveSessionBuilder withDepth(float depth) {
        assert depth > 0;
        this.depthProfile = new DepthProfile(depth);
        return this;
    }

    /**
     * Sets the dive session builder start date
     */
    public DiveSessionBuilder withStartDate(String date) {
        this.dateStart = new OurDate(date);
        return this;
    }

    /**
     * Sets the dive session builder end date
     * @param date A string containing the date of the object
     */
    public DiveSessionBuilder withEndDate(String date) {
        this.dateEnd = new OurDate(date);
        return this;
    }

    /**
     * Set the time zone
     * @param tz - timezone in relation to UTC
     */
    public DiveSessionBuilder withTimeZone(String tz) {
        this.timezone = new TimeZone(tz);
        return this;
    }

    /**
     * Builds the DiveSession object
     * @return DiveSession with the desired propeties
     */
    public DiveSession build() {
        return new DiveSession(dateStart, start, safetyStop, dateEnd, end,
                pressureGroupAtBeginning, pressureGroupAtEnd, location, depthProfile,
                timezone);
    }

}
