package seedu.address.model.budgetelements;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Club's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClubName(String)}
 */

public class ClubName {

    public static final String MESSAGE_CLUB_NAME_CONSTRAINTS =
            "Club names should only contain alphanumeric characters and it should not be blank";

    /*
     * The first character of the club name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String CLUB_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String clubName;

    /**
     * Constructs a {@code Name}.
     *
     * @param clubname A valid club name.
     */
    public ClubName(String clubname) {
        requireNonNull(clubname);
        checkArgument(isValidClubName(clubname), MESSAGE_CLUB_NAME_CONSTRAINTS);
        clubName = clubname;
    }

    /**
     * Returns true if a given string is a valid club name.
     */
    public static boolean isValidClubName(String test) {
        return test.matches(CLUB_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return clubName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClubName // instanceof handles nulls
                && clubName.equals(((ClubName) other).clubName)); // state check
    }

    @Override
    public int hashCode() {
        return clubName.hashCode();
    }

}
