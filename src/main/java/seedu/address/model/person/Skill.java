package seedu.address.model.person;
import static java.util.Objects.requireNonNull;
/**
 * Represents a Person's skill in the address book.
 * Guarantees: immutable; is always valid
 */
public class Skill {
    public final String value;
    public Skill(String skill) {
        requireNonNull(skill);
        value = skill;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Skill // instanceof handles nulls
                && value.equals(((Skill) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
