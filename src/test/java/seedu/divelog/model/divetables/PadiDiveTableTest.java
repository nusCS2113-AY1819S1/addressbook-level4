package seedu.divelog.model.divetables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

//@@author arjo129
public class PadiDiveTableTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getSurfaceTable_canRead() throws JSONException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        JSONArray arr = padiDiveTable.getSurfaceTable(new PressureGroup("A"), new PressureGroup("A"));
        assertEquals(arr.get(0).toString(), "00:00");
        assertEquals(arr.get(1).toString(), "03:00");
    }

    @Test
    public void getSurfaceTable_canCalculateDivePressure() {
        PressureGroup pressureGroup = new PressureGroup("c");
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        assertEquals(padiDiveTable.fromSurfaceInterval(pressureGroup, 22.0f), new PressureGroup("b"));
    }

    @Test
    public void depthToPressuregroup_canRead() throws LimitExceededException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        PressureGroup pg = padiDiveTable.depthToPg(new DepthProfile(10), 122);
        assertEquals(pg.getPressureGroup(), "T");
    }

    @Test
    public void depthToTimes_canRead() throws JSONException, LimitExceededException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        JSONArray arr = padiDiveTable.depthToTimes(new DepthProfile(10), new PressureGroup("A"));
        assertEquals(arr.getInt(0), 10);
        assertEquals(arr.getInt(1), 209);
    }

    @Test
    public void timeToMinutes_emptyStringThrowsException () throws InvalidTimeException {
        thrown.expect(InvalidTimeException.class);
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        padiDiveTable.timeToMinutes("");
    }

    @Test
    public void timeToMinutes_tooManyDigitsThrowsException () throws InvalidTimeException {
        thrown.expect(InvalidTimeException.class);
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        padiDiveTable.timeToMinutes("000:09");
    }

    @Test
    public void timeToMinutes_tooManyMinutesThrowsException () throws InvalidTimeException {
        thrown.expect(InvalidTimeException.class);
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        padiDiveTable.timeToMinutes("00:090");
    }

    @Test
    public void timeToMinutes_tooManyColonsThrowsException () throws InvalidTimeException {
        thrown.expect(InvalidTimeException.class);
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        padiDiveTable.timeToMinutes("00:00:00");
    }

    @Test
    public void timeToMinutes_lettersThrowsException () throws InvalidTimeException {
        thrown.expect(InvalidTimeException.class);
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        padiDiveTable.timeToMinutes("AA:00");
    }

    @Test
    public void timeToMinutes_correctConversion() throws InvalidTimeException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        assertEquals(padiDiveTable.timeToMinutes("12:12"), 732);
    }

    //@@author shuanang
    @Test
    public void getMaxBottomTime_canRead() throws ParseException {
        DepthProfile depthProfile = new DepthProfile(5);
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        assertEquals(padiDiveTable.getMaxBottomTime(depthProfile), 219);
        depthProfile = new DepthProfile(25);
        assertEquals(padiDiveTable.getMaxBottomTime(depthProfile), 29);
        depthProfile = new DepthProfile(41);
        assertEquals(padiDiveTable.getMaxBottomTime(depthProfile), 8);
    }
    @Test
    public void getMaxBottomTime_tooDeepThrowsException() throws ParseException {
        thrown.expect(ParseException.class);
        DepthProfile depthProfile = new DepthProfile(43);
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        padiDiveTable.getMaxBottomTime(depthProfile);
        depthProfile = new DepthProfile(430);
        padiDiveTable.getMaxBottomTime(depthProfile);
    }
}
