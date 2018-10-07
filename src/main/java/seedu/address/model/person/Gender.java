package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isInputAccepted(String)}
 */
public class Gender {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Gender can input as 'M' or \"Male\" for Male and 'F' or \"Female\" for Female. It should not be blank";

    public static final String VALID_GENDER_MALE = "MALE";
    public static final String VALID_GENDER_FEMALE = "FEMALE";
    public static final String VALID_GENDER_ABBREVIATION_MALE = "M";
    public static final String VALID_GENDER_ABBREVIATION_FEMALE = "F";

    public final String gender;

    /**
     * Constructs a {@code Gender}.
     * Conducts an input check {@link #isInputAccepted(String)}
     *
     * @param gender A valid gender
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isInputAccepted(gender), MESSAGE_NAME_CONSTRAINTS);
        gender = inputTransform(gender);
        requireNonNull(gender);
        this.gender = gender;
    }

    /**
     * Returns true if a given string is a valid name.
     * Accepted Valid Strings are : "MALE"
     *                              "FEMALE"
     *                              "M"
     *                              "F"
     *
     */
    public static boolean isInputAccepted(String input){
        String testInput = input.toUpperCase();
        switch (testInput) {
            case VALID_GENDER_MALE :
            case VALID_GENDER_FEMALE :
            case VALID_GENDER_ABBREVIATION_MALE:
            case VALID_GENDER_ABBREVIATION_FEMALE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns a static string for the program to register Male or Female
     * Program will determine Male via "MALE" string
     * Program will determine Female via "FEMALE" string
     */
    public static String inputTransform(String input){
        input = input.toUpperCase();
        switch (input) {
            case VALID_GENDER_MALE :
            case VALID_GENDER_ABBREVIATION_MALE:
                return VALID_GENDER_MALE;
            case VALID_GENDER_FEMALE :
            case VALID_GENDER_ABBREVIATION_FEMALE:
                return VALID_GENDER_FEMALE;
        }
        return null;
    }

    @Override
    public String toString(){
        return gender;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && gender.equals(((Gender) other).gender)); // state check
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }
}
