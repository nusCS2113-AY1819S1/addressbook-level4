package seedu.divelog.logic.pressuregroup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.Location;
import seedu.divelog.model.dive.OurDate;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.dive.Time;
import seedu.divelog.model.dive.TimeZone;


//@@author shuanang
public class PressureGroupLogicTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void computePressureGroupFirstDive_test() throws LimitExceededException {
        PressureGroup pressureGroup = PressureGroupLogic.computePressureGroupFirstDive(new DepthProfile(14),
                50);
        assertEquals(pressureGroup.getPressureGroup(), "N");
        pressureGroup = PressureGroupLogic.computePressureGroupFirstDive(new DepthProfile(1),
                219);
        assertEquals(pressureGroup.getPressureGroup(), "Z");
        pressureGroup = PressureGroupLogic.computePressureGroupFirstDive(new DepthProfile(21),
                10);
        assertEquals(pressureGroup.getPressureGroup(), "C");
    }

    @Test
    public void computePressureGroup_test() throws JSONException, LimitExceededException {

        PressureGroup pressureGroup = PressureGroupLogic.computePressureGroup(new DepthProfile(14),
                50, new PressureGroup("d"));
        assertEquals(pressureGroup.getPressureGroup(), "T");
        pressureGroup = PressureGroupLogic.computePressureGroup(new DepthProfile(1),
                209, new PressureGroup("A"));
        assertEquals(pressureGroup.getPressureGroup(), "Z");
        pressureGroup = PressureGroupLogic.computePressureGroup(new DepthProfile(21),
                10, new PressureGroup("j"));
        assertEquals(pressureGroup.getPressureGroup(), "P");
    }

    @Test
    public void computePressureGroup_actualBottomTimeExceedsNoDecompressionLimitsThrowsException()
            throws LimitExceededException, JSONException {
        thrown.expect(LimitExceededException.class);
        PressureGroup pressureGroup = PressureGroupLogic.computePressureGroup(new DepthProfile(10),
                210, new PressureGroup("a"));
        assertEquals(pressureGroup.getPressureGroup(), null);
    }

    @Test
    public void computePressureGroupAfterSurfaceInterval_test() {
        PressureGroup pressureGroup =
                PressureGroupLogic.computePressureGroupAfterSurfaceInterval(new PressureGroup("H"), 27);
        assertEquals(pressureGroup.getPressureGroup(), "E");
    }

    @Test
    public void computeTimeToNextPg_stubTest() throws Exception {
        PressureGroupLogicStub pressureGroupLogicStub = new PressureGroupLogicStub();
        float timeToNextPg = pressureGroupLogicStub.computeTimeToNextPg(new DiveSession(new OurDate("20102018"),
                new Time("0916"),
                new Time("1045"),
                new OurDate("20102018"),
                new Time("1100"),
                new PressureGroup("A"),
                new PressureGroup("R"), //104 minutes at 10m from Pressure Group A to R
                new Location("Bali"),
                new DepthProfile(10.0f),
                new TimeZone("+8")));
        assertEquals(timeToNextPg, 8);
        float timeToNextPg1 = pressureGroupLogicStub.computeTimeToNextPg(new DiveSession(new OurDate("19102018"),
                new Time("0916"),
                new Time("1045"),
                new OurDate("19102018"), //dive ended 1 day before and diver checks
                new Time("1100"),
                new PressureGroup("A"),
                new PressureGroup("R"), //104 minutes at 10m from Pressure Group A to R
                new Location("Bali"),
                new DepthProfile(10.0f),
                new TimeZone("+8")));
        assertEquals(timeToNextPg1, 0);
    }
    @Test
    public void computeTimeToMinPressureGroup_stubTest() throws Exception {
        PressureGroupLogicStub pressureGroupLogicStub = new PressureGroupLogicStub();
        float timeToMinPg = pressureGroupLogicStub.computeTimeToMinPressureGroup(new DiveSession(
                new OurDate("20102018"),
                new Time("0916"),
                new Time("1045"),
                new OurDate("20102018"),
                new Time("1100"),
                new PressureGroup("A"),
                new PressureGroup("R"), //104 minutes at 10m from Pressure Group A to R
                new Location("Bali"),
                new DepthProfile(10.0f),
                new TimeZone("+8")));
        assertEquals(timeToMinPg, 95);
        float timeToMinPg1 = pressureGroupLogicStub.computeTimeToNextPg(new DiveSession(new OurDate("19102018"),
                new Time("0916"),
                new Time("1045"),
                new OurDate("19102018"), //dive ended 1 day before and diver checks
                new Time("1100"),
                new PressureGroup("A"),
                new PressureGroup("R"), //104 minutes at 10m from Pressure Group A to R
                new Location("Bali"),
                new DepthProfile(10.0f),
                new TimeZone("+8")));
        assertEquals(timeToMinPg1, 0);
    }
}
