package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Equipment in the workout book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEquipment(String)}
 */
public class Equipment {

    public static final String MESSAGE_EQUIPMENT_CONSTRAINTS =
            "Equipment should only contain names separated by commas, and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EQUIPMENT_VALIDATION_REGEX = "[\\p{Alpha}|,][\\p{Alpha} |, ]*";

    public final String fullEquipment;

    /**
     * Constructs a {@code Equipment}.
     *
     * @param equipment A valid equipment.
     */
    public Equipment(String equipment) {
        requireNonNull(equipment);
        checkArgument(isValidEquipment(equipment), MESSAGE_EQUIPMENT_CONSTRAINTS);
        fullEquipment = equipment;
    }

    /**
     * Returns true if a given string is a valid equipment.
     */
    public static boolean isValidEquipment(String test) {
        return test.matches(EQUIPMENT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullEquipment;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Equipment // instanceof handles nulls
                && fullEquipment.equals(((Equipment) other).fullEquipment)); // state check
    }

    @Override
    public int hashCode() {
        return fullEquipment.hashCode();
    }

}
