package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Description {
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description should be alphanumeric";
    public static final String DESCRIPTION_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String value;

    public Description(String description){
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        value = description;
    }

    public static boolean isValidDescription (String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}


