package seedu.divelog.model.dive;

//@@author arjo129

/**
 * Represents a pressure group
 */
public class PressureGroup {

    private static final String PRESSURE_GROUP_VALIDATION_REGEX = "([A-Z||a-z])";
    private final String pressureGroup;

    /**
     * Constructs a pressure group object.
     * @param pressureGroup A String that must be 1 alphabetical character in length
     */
    public PressureGroup(String pressureGroup) {
        assert pressureGroup.length() == 1;
        assert Character.isLetter(pressureGroup.charAt(0));
        this.pressureGroup = pressureGroup.toUpperCase();
    }

    public String getPressureGroup() {
        return pressureGroup;
    }

    /**
     * Checks if a string is a valid pressure group. A valid pressure group consists only
     * of 1 alphabetical character.
     * @param token
     * @return The validity of the string
     */
    public static boolean isValid(String token) {
        return token.matches(PRESSURE_GROUP_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PressureGroup)) {
            return false;
        }
        PressureGroup pg = (PressureGroup) obj;
        return pg.getPressureGroup().equals(pressureGroup);
    }
}
