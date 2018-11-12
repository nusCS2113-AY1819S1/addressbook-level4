package seedu.divelog.model.divetables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.util.DiveTableUtil;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;
import seedu.divelog.model.divetables.exceptions.MaxDepthExceededException;

//@@author arjo129
/**
 * This class loads dive tables
 */
public class PadiDiveTable {
    private static final int HOUR_IN_MINUTES = 60;
    private static final Logger logger = LogsCenter.getLogger(PadiDiveTable.class);
    private static final String TIME_VALIDATION_REGEX = "[0-9][0-9][:][0-9][0-9]";
    private static PadiDiveTable diveTable = new PadiDiveTable();
    private final DiveTableUtil surfaceTable;
    private final DiveTableUtil depthToPressureGroup;
    private final DiveTableUtil diveTableUtil;
    private final DiveTableUtil maxBottomTimes;

    //@@author shuanang
    private PadiDiveTable() {
        this.surfaceTable = new DiveTableUtil("/divetables/surface_table.json");
        this.depthToPressureGroup = new DiveTableUtil("/divetables/Dive_table_1.json");
        this.diveTableUtil = new DiveTableUtil("/divetables/Dive_table_2.json");
        this.maxBottomTimes = new DiveTableUtil("/divetables/max_bottom_times.json");
        logger.info("Successfully loaded dive tables");
    }

    //@@author arjo129
    public static PadiDiveTable getInstance() {
        return diveTable;
    }


    /**
     * Looks up surface interval table
     * @param pressureGroup1 - Pressure group along x axis
     * @param pressureGroup2 - Pressure group along y axis
     * @return returns a JSON Array with the surface intervals
     */
    public JSONArray getSurfaceTable(PressureGroup pressureGroup1, PressureGroup pressureGroup2) {
        try {
            logger.info("Attempting to read json");
            JSONObject table = surfaceTable.readJsonFileFromResources();
            logger.info("attempting to lookup data");
            JSONObject column = table.getJSONObject(pressureGroup1.getPressureGroup());
            return column.getJSONArray(pressureGroup2.getPressureGroup());
        } catch (JSONException json) {
            logger.severe("Malformatted json");
        } catch (IOException e) {
            logger.severe("Failed to read dive tables due to an IOException\n\t" + e.getMessage());
        }
        return null;
    }

    /**
     * Get data from the surface interval table
     * @param startingPressureGroup - Initial pressure group
     * @param minutes - number of minutes
     * @return - Final pressure group
     */
    public PressureGroup fromSurfaceInterval(PressureGroup startingPressureGroup, float minutes) {
        try {
            JSONObject table = surfaceTable.readJsonFileFromResources();
            JSONObject column = table.getJSONObject(startingPressureGroup.getPressureGroup());
            Iterator<String> keys = column.keys();

            while (keys.hasNext()) {
                String pressureGroup = keys.next();
                JSONArray interval = column.getJSONArray(pressureGroup);
                try {
                    String start = interval.getString(0);
                    String end = interval.getString(1);
                    float minInterval = timeToMinutes(start);
                    float maxInterval = timeToMinutes(end);
                    if (minutes >= minInterval && minutes <= maxInterval) {
                        return new PressureGroup(pressureGroup);
                    }
                } catch (JSONException je) {
                    logger.severe("Malformatted json: " + je.getMessage());
                } catch (InvalidTimeException e) {
                    logger.warning("Could not parse time");
                }
            }
        } catch (JSONException je) {
            logger.severe("Malformatted json: " + je.getMessage());
        } catch (IOException e) {
            logger.severe("Failed to read dive tables due to an IOException\n\t" + e.getMessage());
        }
        return new PressureGroup("A");
    }

    /**
     * Converts a duration in HH:MM to minutes
     * @param time
     * @return
     * @throws InvalidTimeException
     */
    public float timeToMinutes(String time) throws InvalidTimeException {
        if (time.matches(TIME_VALIDATION_REGEX)) {
            String[] duration = time.split(":");
            int minutes = Integer.parseInt(duration[1]);
            int hours = Integer.parseInt(duration[0]);
            if (minutes >= HOUR_IN_MINUTES) {
                throw new InvalidTimeException("timetoMinutes failed");
            }
            return hours * HOUR_IN_MINUTES + minutes;
        } else {
            throw new InvalidTimeException("timetoMinutes failed");
        }
    }
    /**
     * Looks up depth to PG
     * @param depth - The depth
     * @param duration - minutes;
     */
    public PressureGroup depthToPg(DepthProfile depth, int duration) throws LimitExceededException {
        try {
            logger.info("Attempting to read json");
            JSONObject table = depthToPressureGroup.readJsonFileFromResources();
            logger.info("attempting to lookup data");
            String key = findClosestKey(table, depth.getDepth());
            JSONObject column = table.getJSONObject(key);
            key = findClosestKey(column, duration);
            String pressureGroup = column.getString(key);
            return new PressureGroup(pressureGroup);
        } catch (IOException io) {
            logger.severe("Failed to read dive tables due to an IOException\n\t" + io.getMessage());
        } catch (JSONException json) {
            logger.severe("Failed to parse JSON");
        }

        return null;
    }

    /**
     * Reads the depth and pressure group and returns an array containing the minimum time
     * @param depth - depth dove to
     * @param pressureGroup - pressure group at time
     * @return an array containing
     */
    public JSONArray depthToTimes(DepthProfile depth, PressureGroup pressureGroup) throws LimitExceededException {
        try {
            JSONObject table = diveTableUtil.readJsonFileFromResources();
            String key = findClosestKey(table, depth.getDepth());
            JSONObject column = table.getJSONObject(key);
            return column.getJSONArray(pressureGroup.getPressureGroup());
        } catch (IOException io) {
            logger.severe("Failed to read dive tables due to an IOException\n\t" + io.getMessage());
        } catch (JSONException json) {
            logger.severe("Failed to parse JSON");
        }
        return null;

    }
    //@@author shuanang
    /**
     * Takes in depth (1-42m) and returns a float of the maximum time allowed at that depth
     * @param depth - depth dove to
     * @return float with max bottom times at that depth
     */
    public float getMaxBottomTime(DepthProfile depth) throws ParseException {
        try {
            if (depth.getDepth() > 42) {
                throw new MaxDepthExceededException();
            }
            logger.info("Attempting to read json");
            JSONObject table = maxBottomTimes.readJsonFileFromResources();
            logger.info("attempting to lookup data");
            String key = findClosestKey(table, depth.getDepth());
            return table.getInt(key);
        } catch (IOException e) {
            logger.severe("Failed to read dive tables due to an IOException\n\t" + e.getMessage());
        } catch (JSONException e) {
            logger.severe("Malformatted json");
        } catch (MaxDepthExceededException e) {
            logger.severe("Max depth exceeded\n\t" + e.getMessage());
            throw new ParseException(Messages.MESSAGE_ERROR_LIMIT_EXCEED + "\n"
                    + "The deepest you can go is 42m - don't risk your life!");
        } catch (LimitExceededException le) {
            logger.severe("Max depth exceeded\n\t" + le.getMessage());
            throw new ParseException(Messages.MESSAGE_ERROR_LIMIT_EXCEED + "\n"
                    + Messages.MESSAGE_MAX_DEPTH_EXCEEDED);
        }
        return 0;
    }

    //@@author arjo129
    /**
     * Looks for the nearest key
     * @param object - JSON Object
     * @param key - The key that is nearest to that value.
     */
    public static String findClosestKey(JSONObject object, float key) throws LimitExceededException {
        Iterator<String> keys = object.keys();
        ArrayList<Integer> integerKeys = new ArrayList<Integer>();

        while (keys.hasNext()) {
            String curr = keys.next();
            integerKeys.add(Integer.parseInt(curr));
        }

        Collections.sort(integerKeys);

        int i = 0;
        while (key > integerKeys.get(i)) {
            i++;
            if (i == integerKeys.size()) {
                throw new LimitExceededException();
            }
        }

        return integerKeys.get(i).toString();
    }
}
