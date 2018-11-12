package seedu.divelog.commons.util;

/**
 * Performs basic unit conversion
 */
public class UnitsUtil {
    private static float conversionRatioFeetToMeters = 0.3048f;

    /**
     * Converts feet to meters
     * @param feet - depth in feet.
     * @return value in meters
     */
    public static float feetToMeters(float feet) {
        return conversionRatioFeetToMeters * feet;
    }

    /**
     * Converts meters to feet
     * @param meters - depth in meters
     * @return value in meters
     */
    public static float metersToFeet(float meters) {
        return meters / conversionRatioFeetToMeters;
    }
}
