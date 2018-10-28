package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Skill's Level in the address book.
 * Guarantees: immutable; is always valid
 */

public class SkillLevel {

    public static final String MESSAGE_SKILLLEVEL_CONSTRAINTS = "This skill level is not valid. "
            + "Please enter a whole number between 0 to 100.";
    public final int skillLevel;

    public SkillLevel(int skillLevel) {
        checkArgument(isValidSkillLevel(skillLevel), MESSAGE_SKILLLEVEL_CONSTRAINTS);
        this.skillLevel = skillLevel;
    }

    public boolean isValidSkillLevel(int skillLevel) {
        return skillLevel >= 0 && skillLevel <= 100;
    }

    @Override
    public String toString() {
        return Integer.toString(skillLevel);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SkillLevel // instanceof handles nulls
                && skillLevel == ((SkillLevel) other).skillLevel); // state check
    }
}
