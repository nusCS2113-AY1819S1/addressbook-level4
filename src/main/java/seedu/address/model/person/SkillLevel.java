package seedu.address.model.person;

/**
 * Represents a Skill's Level in the address book.
 * Guarantees: immutable; is always valid
 */

public class SkillLevel {
    public final int skillLevel;

    public SkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
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
