package seedu.divelog.model.dive;
//@@author arjo129

import seedu.divelog.commons.core.ApplicationState;
import seedu.divelog.commons.enums.Units;
import seedu.divelog.commons.util.UnitsUtil;

/**
 * Keeps track of the Depth of a dive.
 * Currently as of v1.1, only simple dives to a fixed depth are supported
 * TODO: Implement complex dives
 */
public class DepthProfile {
    private static final String DEPTH_VALIDATION_REGEX = "([0-9]*[.])?[0-9]+";

    private final float depth;

    /***
     * Creates a new DepthProfile object
     * @param depth in meters
     */
    public DepthProfile(float depth) {
        assert depth > 0;
        this.depth = depth;
    }

    /***
     * Creates a DepthProfile object using the units defined.
     */
    public DepthProfile(float depth, Units units) {
        if (units == Units.FEET) {
            this.depth = UnitsUtil.feetToMeters(depth);
        } else {
            this.depth = depth;
        }
    }
    /**
     * Verifies the validity of a depth argument
     * @param depth The depth. Must be a decimal number
     * @return if the depth string is valid, return true
     */
    public static boolean isValidDepth(String depth) {
        return depth.matches(DEPTH_VALIDATION_REGEX);
    }

    public float getDepth() {
        return depth;
    }

    /**
     * Returns the depth formatted according to the units
     * @return
     */
    public String getFormattedString() {
        ApplicationState applicationState = ApplicationState.getInstance();
        Units currentUnit = applicationState.getUnit();
        switch(currentUnit) {
        case FEET:
            return String.format("%.1fft", UnitsUtil.metersToFeet(this.depth));
        case METERS:
        default:
            return String.format("%.1fm", this.depth);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DepthProfile)) {
            return false;
        }
        DepthProfile other = (DepthProfile) obj;
        return other.getDepth() == depth;
    }
}
