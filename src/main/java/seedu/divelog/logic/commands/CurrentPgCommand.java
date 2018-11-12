package seedu.divelog.logic.commands;

//@@author shuanang

import static seedu.divelog.commons.util.CompareUtil.readDateFromLong;
import static seedu.divelog.commons.util.CompareUtil.readTimeFromLong;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.divelog.commons.util.CompareUtil;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.pressuregroup.PressureGroupLogic;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.PressureGroup;


/**
 * Returns the current pressure group with respect to the latest dive session based on the current time
 * provides information on current pressure group, minutes to next pressure group, minutes and time to the minimum
 * pressure group "A", as well as flight recommendation times according to number of dives
 */
public class CurrentPgCommand extends Command {
    public static final String COMMAND_WORD = "currentpg";
    public static final String COMMAND_ALIAS = "cpg";
    public static final String MESSAGE_CURRENTPG = "Based on your latest dive(s), your current pressure group is: ";
    public static final String MESSAGE_TIMETONEXT = "Time to next pressure group, ";
    public static final String MESSAGE_TIMETOMIN = "Time to 'A' pressure group: ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        DiveSession lastDive = model.getMostRecent();
        PressureGroupLogic pressureGroupLogic = new PressureGroupLogic();
        long currentDateTime = CompareUtil.getCurrentDateTimeLong(); //ddMMyyyyHHmm
        String timeNow = readTimeFromLong(currentDateTime);
        String dateNow = readDateFromLong(currentDateTime);
        String endOfLastDiveTime = lastDive.getEnd().getTimeString();
        String endOfLastDiveDate = lastDive.getDateEnd().getOurDateString();
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy HH:mm Z");
        Calendar minPgCal = Calendar.getInstance();
        Calendar timeToFlightCal = Calendar.getInstance();
        try {
            long surfaceDuration = CompareUtil.checkTimeDifference(endOfLastDiveTime, timeNow, endOfLastDiveDate,
                    dateNow);
            PressureGroup currentPg = PressureGroupLogic.computePressureGroupAfterSurfaceInterval(lastDive
                    .getPressureGroupAtEnd(), surfaceDuration);
            String nextPg = String.valueOf(((char) (currentPg.getPressureGroup().charAt(0) - 1)));
            if (currentPg.getPressureGroup().equals("A")) {
                nextPg = "A";
            }
            float timeToNextPg = pressureGroupLogic.computeTimeToNextPg(lastDive);
            float timeToMinPg = pressureGroupLogic.computeTimeToMinPressureGroup(lastDive);
            minPgCal.add(Calendar.MINUTE, (int) timeToMinPg);
            int firstDive = 2; //just a flag
            PressureGroup pg = PressureGroupLogic
                    .computePressureGroupFirstDive(lastDive.getDepthProfile(), CompareUtil
                            .checkTimeDifference(lastDive.getStart().getTimeString(),
                                    lastDive.getEnd().getTimeString(),
                                    lastDive.getDateStart().getOurDateString(),
                                    lastDive.getDateEnd().getOurDateString()));
            if (lastDive.getPressureGroupAtEnd().getPressureGroup().equals(pg.getPressureGroup().intern())) {
                //lastDive is a first dive
                firstDive = 0;
            } else {
                //lastDive is a repeat dive
                firstDive = 1;
            }
            int singleDivePreFlightSurfaceIntervalMinutes = 12 * 60;
            int repeatDivePreFlightSurfaceIntervalMinutes = 18 * 60;
            if ((currentPg.getPressureGroup().equals("A")) && (firstDive == 0)
                    && (surfaceDuration >= singleDivePreFlightSurfaceIntervalMinutes)) {
                //check if there is any dive in the past 12 hours for single dives
                return new CommandResult("Based on your latest dive(s) that ended on "
                        + lastDive.getDateEnd().getOurDateString() + " "
                        + lastDive.getEnd().getTimeString()
                        + "hrs, your current pressure group is already A." + "\n"
                        + "It has been at least 12 hours since your last SINGLE dive - you can safely fly!");
            }
            if ((currentPg.getPressureGroup().equals("A")) && (firstDive == 1)
                    && (surfaceDuration >= repeatDivePreFlightSurfaceIntervalMinutes)) {
                //check if there is any dive in the past 18 hours for repeat/multi-day dives
                return new CommandResult("Based on your latest dive(s) that ended on "
                        + lastDive.getDateEnd().getOurDateString() + " "
                        + lastDive.getEnd().getTimeString()
                        + "hrs, your current pressure group is already A." + "\n"
                        + " It has been at least 18 hours since your last dive of your repetitive dive series"
                        + " - you can safely fly!");
            }
            long singleDiveToFlightTime = singleDivePreFlightSurfaceIntervalMinutes - surfaceDuration;
            long repeatDiveToFlightTime = repeatDivePreFlightSurfaceIntervalMinutes - surfaceDuration;
            if (firstDive == 0) {
                timeToFlightCal.add(Calendar.MINUTE, (int) singleDiveToFlightTime);
                return new CommandResult(MESSAGE_CURRENTPG + currentPg.getPressureGroup() + "\n"
                        + MESSAGE_TIMETONEXT + nextPg + ": " + timeToNextPg + " minutes." + "\n"
                        + MESSAGE_TIMETOMIN + timeToMinPg + " minutes, at: " + dateFormat.format(minPgCal.getTime())
                        + "\n" + "You just completed a single dive, ended on "
                        + lastDive.getDateEnd().getOurDateString() + " "
                        + lastDive.getEnd().getTimeString()
                        + "hrs." + "\n"
                        + "You are recommended to wait at least 12 hours on land "
                        + "before flying." + "\n"
                        + "You can safely fly from: " + dateFormat.format(timeToFlightCal.getTime()) + " onwards.");
            }
            if (firstDive == 1) {
                timeToFlightCal.add(Calendar.MINUTE, (int) repeatDiveToFlightTime);
                return new CommandResult(MESSAGE_CURRENTPG + currentPg.getPressureGroup() + "\n"
                        + MESSAGE_TIMETONEXT + nextPg + ": " + timeToNextPg + " minutes." + "\n"
                        + MESSAGE_TIMETOMIN + timeToMinPg + " minutes, at: " + dateFormat.format(minPgCal.getTime())
                        + "\n" + "You just completed a repetitive/multi-day dive, ended on "
                        + lastDive.getDateEnd().getOurDateString() + " "
                        + lastDive.getEnd().getTimeString()
                        + "hrs." + "\n"
                        + "You are recommended to wait at least 18 hours on land before flying." + "\n"
                        + "You can safely fly from: " + dateFormat.format(timeToFlightCal.getTime()) + " onwards.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult("Unable to retrieve current pressure group.");
    }
}
