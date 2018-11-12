package seedu.divelog.logic.pressuregroup;
//@@author shuanang
import static seedu.divelog.commons.util.CompareUtil.readDateFromLong;
import static seedu.divelog.commons.util.CompareUtil.readTimeFromLong;

import org.json.JSONArray;
import org.json.JSONException;

import seedu.divelog.commons.util.CompareUtil;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.PressureGroup;

import seedu.divelog.model.divetables.PadiDiveTable;

/**
 * Calculates the pressure group given depth and minutes spent at depth for single and/or repeat dives.
 */
public class PressureGroupLogic {
    public PressureGroupLogic(){}

    /**
     * For first dive of the day, calculates pressure group given depth and minutes spent at depth
     * @param depth {@code DepthProfile} in metres, the deepest point which the diver descends for the dive
     * @param actualBottomTime in minutes which the diver spent underwater
     * @return new PressureGroup object containing the new pressure group after repeat dive
     * @throws LimitExceededException if the dive is too long.
     */
    public static PressureGroup computePressureGroupFirstDive(DepthProfile depth, float actualBottomTime)
            throws LimitExceededException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        PressureGroup newPg;
        newPg = padiDiveTable.depthToPg(depth, (int) actualBottomTime);
        return newPg;
    }
    /**
     * Calculates the pressure group given depth and minutes spent at depth for repetitive dives.
     *
     * @param depth {@code DepthProfile} in metres, the deepest point which the diver descends for the dive
     * @param actualBottomTime in minutes which the diver spent underwater
     * @param pg {@code PressureGroup} current PressureGroup object, in consideration for repeat dives.
     * @return new PressureGroup object containing the new pressure group after repeat dive
     * @throws JSONException If an error occurs during reading of the PADI dive table.
     * @throws LimitExceededException For repeat dives, if actual bottom time exceeds no decompression limits
     */
    public static PressureGroup computePressureGroup(DepthProfile depth, float actualBottomTime, PressureGroup pg)
            throws JSONException, LimitExceededException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        JSONArray arr = padiDiveTable.depthToTimes(depth, pg);
        int adjustedNoDecompressionLimits = arr.getInt(1);
        if ((int) actualBottomTime > adjustedNoDecompressionLimits) {
            throw new LimitExceededException();
        }
        int totalBottomTime = arr.getInt(0) + (int) actualBottomTime;
        PressureGroup newPg;
        newPg = padiDiveTable.depthToPg(depth, totalBottomTime);
        return newPg;
    }

    /**
     * Calculates the pressure group after a given time the diver has emerged from the water.
     *
     * @param pg {@code PressureGroup} current PressureGroup object containing the current pressure group
     * @param duration in minutes: time elapsed after diver has surfaced
     * @return new PressureGroup object containing the new pressure group after surface interval
     */
    public static PressureGroup computePressureGroupAfterSurfaceInterval(PressureGroup pg, float duration) {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        final int minSurfaceTimeMinutesToMinPressureGroup = 3 * 60;
        PressureGroup newPg;
        if (duration >= minSurfaceTimeMinutesToMinPressureGroup) {
            return new PressureGroup("A");
        } else {
            newPg = padiDiveTable.fromSurfaceInterval(pg, duration);
        }
        return newPg;
    }

    /**
     * Calculates the time to next pressure group, returns a float with minutes to next pressure group
     *
     * @param lastDive {@code DiveSession} current DiveSession object containing the last dive details
     * @return number of minutes needed to the next immediate Pressure group e.g. "D" to "C"
     */
    public float computeTimeToNextPg(DiveSession lastDive) throws Exception {
        //from surface interval - ending pressure group + difference in minutes -> current pg
        long currentDateTime = CompareUtil.getCurrentDateTimeLong(); //ddMMyyyyHHmm
        String timeNow = readTimeFromLong(currentDateTime);
        String dateNow = readDateFromLong(currentDateTime);
        String endOfLastDiveTime = lastDive.getEnd().getTimeString();
        String endOfLastDiveDate = lastDive.getDateEnd().getOurDateString();
        long surfaceDuration = CompareUtil.checkTimeDifference(endOfLastDiveTime, timeNow, endOfLastDiveDate, dateNow);
        String endOfLastDivePg = lastDive.getPressureGroupAtEnd().getPressureGroup();
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        final int minSurfaceTimeMinutesToMinPressureGroup = 3 * 60;
        float timeToNextPg;
        if (surfaceDuration > minSurfaceTimeMinutesToMinPressureGroup) {
            //"Pressure Group already A"
            timeToNextPg = 0;
            return timeToNextPg;
        }
        String currentPg = computePressureGroupAfterSurfaceInterval(new PressureGroup(endOfLastDivePg), surfaceDuration)
                .getPressureGroup();
        //from current pg to next pg time : next pg arr[0] - difference in minutes (surfaceDuration)
        String nextPg = String.valueOf(((char) (currentPg.charAt(0) - 1)));
        JSONArray nextPgArr = padiDiveTable.getSurfaceTable(new PressureGroup(endOfLastDivePg),
                new PressureGroup(nextPg));
        String toNextPgMinimumTime = nextPgArr.get(0).toString();
        timeToNextPg = padiDiveTable.timeToMinutes(toNextPgMinimumTime) - surfaceDuration;
        //"Time to next Pg group, " + nextPg + " : " + timeToNextPg + " minutes"
        return timeToNextPg;
    }

    /**
     * Computes time needed to the min Pressure Group "A" from the last dive, with reference to the current time
     *
     * @param lastDive {@code DiveSession} current DiveSession object containing the last dive details
     * @return number of minutes needed to the minimum pressure group "A"
     */
    public float computeTimeToMinPressureGroup(DiveSession lastDive) throws Exception {
        //fromsurfaceinterval - ending pressure group + difference in minutes -> current pg
        long currentDateTime = CompareUtil.getCurrentDateTimeLong(); //ddMMyyyyHHmm
        String timeNow = readTimeFromLong(currentDateTime);
        String dateNow = readDateFromLong(currentDateTime);
        String endOfLastDiveTime = lastDive.getEnd().getTimeString();
        String endOfLastDiveDate = lastDive.getDateEnd().getOurDateString();
        long surfaceDuration = CompareUtil.checkTimeDifference(endOfLastDiveTime, timeNow, endOfLastDiveDate, dateNow);
        String endOfLastDivePg = lastDive.getPressureGroupAtEnd().getPressureGroup();
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        final int minSurfaceTimeMinutesToMinPressureGroup = 3 * 60;
        float timeToMinPg;
        if (surfaceDuration > minSurfaceTimeMinutesToMinPressureGroup) {
            //"Pressure Group already A"
            timeToMinPg = 0;
            return timeToMinPg;
        }
        //from current pg to A pressure group min time : getSurfaceTable(last dive pg, "A") arr[0] - surfaceDuration
        JSONArray toMinPgArr = padiDiveTable.getSurfaceTable(new PressureGroup(endOfLastDivePg),
                new PressureGroup("A"));
        String toMinPgTime = toMinPgArr.get(0).toString();
        timeToMinPg = padiDiveTable.timeToMinutes(toMinPgTime) - surfaceDuration;
        return timeToMinPg;
    }
}
