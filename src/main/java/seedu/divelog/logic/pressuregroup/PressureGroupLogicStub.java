package seedu.divelog.logic.pressuregroup;
//@@author shuanang

import static seedu.divelog.commons.util.CompareUtil.readDateFromLong;
import static seedu.divelog.commons.util.CompareUtil.readTimeFromLong;

import org.json.JSONArray;

import seedu.divelog.commons.util.CompareUtil;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.divetables.PadiDiveTable;

/**
 * Stub class to test the PressureGroupLogic without current time dependency in CompareUtil.java
 * specifically two methods: computeTimeToNextPg and computeTimeToMinPressureGroup
 */
public class PressureGroupLogicStub extends PressureGroupLogic {

    @Override
    public float computeTimeToNextPg(DiveSession lastDive) throws Exception {
        long currentDateTime = 201020181200L; //ddMMyyyyHHmm
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
            timeToNextPg = 0;
            return timeToNextPg;
        }
        String currentPg = computePressureGroupAfterSurfaceInterval(new PressureGroup(endOfLastDivePg), surfaceDuration)
                .getPressureGroup();
        String nextPg = String.valueOf(((char) (currentPg.charAt(0) - 1)));
        JSONArray nextPgArr = padiDiveTable.getSurfaceTable(new PressureGroup(endOfLastDivePg),
                new PressureGroup(nextPg));
        String toNextPgMinimumTime = nextPgArr.get(0).toString();
        timeToNextPg = padiDiveTable.timeToMinutes(toNextPgMinimumTime) - surfaceDuration;
        return timeToNextPg;
    }

    @Override
    public float computeTimeToMinPressureGroup(DiveSession lastDive) throws Exception {
        long currentDateTime = 201020181200L; //ddMMyyyyHHmm
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
            timeToMinPg = 0;
            return timeToMinPg;
        }
        JSONArray toMinPgArr = padiDiveTable.getSurfaceTable(new PressureGroup(endOfLastDivePg),
                new PressureGroup("A"));
        String toMinPgTime = toMinPgArr.get(0).toString();
        timeToMinPg = padiDiveTable.timeToMinutes(toMinPgTime) - surfaceDuration;
        return timeToMinPg;
    }
}
