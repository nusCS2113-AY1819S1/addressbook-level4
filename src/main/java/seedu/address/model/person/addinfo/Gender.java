package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * Values for gender
 */
enum GenderEnum {
    Male("Male"), Female("Female");

    private String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
/**
 * Represents a person's gender in Health Book.
 */
public class Gender {
    public static final String GENDER_VALIDATION_REGEX = "[MF]";
    public static final String MESSAGE_GENDER_CONSTRAINTS = "Gender input can only be either M or F.";

    public final Optional<GenderEnum> value;

    private final String emptyString = "";

    public Gender(String gender) {
        requireNonNull(gender);
        switch (gender) {
        case "M":
        case "Male":
            this.value = Optional.of(GenderEnum.Male);
            break;
        case "F":
        case "Female":
            this.value = Optional.of(GenderEnum.Female);
            break;
        default:
            this.value = Optional.empty();
            break;
        }
    }

    public static boolean isValidGender(String test) {
        return test.matches(GENDER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (value.isPresent()) {
            return value.get().getGender();
        } else {
            return emptyString;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Gender
                && value.equals(((Gender) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
