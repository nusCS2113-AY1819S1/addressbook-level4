package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Skill's Level in the address book.
 * Guarantees: immutable; is always valid
 */

public class SkillLevel {

    public final int skillLevel;

    public SkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public boolean isValidSkillLevel() {
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
