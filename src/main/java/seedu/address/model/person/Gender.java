package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Gender {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Gender can only be 1 character long. 'M' for Male and 'F' for Female. It should not be blank";

    public static final String GENDER_VALIDATION_REGEX = "^[\\\\\fM\\\\F\\\\A\\\\L\\\\E]*$";
    public static final String VALID_GENDER_MALE = "MALE";
    public static final String VALID_GENDER_FEMALE = "FEMALE";
    public static final String VALID_GENDER_ABBREVIATION_MALE = "M";
    public static final String VALID_GENDER_ABBREVIATION_FEMALE = "F";

    public final String gender;

    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_NAME_CONSTRAINTS);
        this.gender = gender;
    }
    
    public static boolean isValidGender(String input){
        return input.matches(GENDER_VALIDATION_REGEX);
    }

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
