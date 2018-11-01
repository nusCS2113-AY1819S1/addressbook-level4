package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Description {
    public final String ThisDescription;
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_DESCRIPTION_CCONSTRAINT =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public Description(String description) {
        requireNonNull(description);
        checkArgument(CheckValid(description), MESSAGE_DESCRIPTION_CCONSTRAINT);
        ThisDescription= description;
    }
    public static boolean CheckValid(String description) {
        return description.matches(DESCRIPTION_VALIDATION_REGEX);
    } //

    public String toString() {
        return ThisDescription;
    }

}