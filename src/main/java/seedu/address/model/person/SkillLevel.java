package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Skill's Level in the address book.
 * Guarantees: immutable; is always valid
 */

public class SkillLevel {
    public final int level;

    public SkillLevel(int level) {
        requireNonNull(level);
        this.level = level;
    }

    @Override
    public String toString() {
        return Integer.toString(level);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SkillLevel // instanceof handles nulls
                && level == ((SkillLevel) other).level); // state check
    }
}
