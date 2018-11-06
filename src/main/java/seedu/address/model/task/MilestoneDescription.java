package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

//@@author JeremyInElysium
/**
 * Represents a description in the milestone of a task.
 */
public class MilestoneDescription {

    public static final String MESSAGE_MILESTONEDESCRIPTION_CONSTRAINTS =
            "Milestone description can only contain alphanumeric characters and spaces, and it should not be blank.";

    /**
     * The first character of the milestone description must not be a whitespace,
     * otherwise " " (a blank string) will become a valid input
     */
    public static final String MILESTONEDESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String milestoneDescription;

    /**
     * Creates a constructor for the milestone description
     * Guarantees that the milestone description is not null
     * @param milestoneDescription a valid milestone description
     */
    public MilestoneDescription(String milestoneDescription) {
        requireNonNull(milestoneDescription);
        //checkArgument(isValidMilestoneDescription(milestoneDescription), MESSAGE_MILESTONEDESCRIPTION_CONSTRAINTS);
        this.milestoneDescription = milestoneDescription;
    }


    /**
     * Checks whether milestone description entered by the user is valid
     * @param milestoneDescription
     * @return true if valid
     */
    /*
    public static boolean isValidMilestoneDescription(String milestoneDescription) {
        return true;
        //milestoneDescription.matches(MILESTONEDESCRIPTION_VALIDATION_REGEX);
    }
    */

    public String getMilestoneDescription() {
        return this.milestoneDescription;
    }

    /**
     * Pads milestone description to fill up 40 characters for neater layout in the UI
     * @param milestoneDescription
     * @return padded milestoneDescription
     */
    public static String padMilestoneDescription(String milestoneDescription) {
        int milestoneDescriptionLength = milestoneDescription.length();
        int toPad = 41 - milestoneDescriptionLength;
        return String.format("%1$-" + toPad + "s", milestoneDescription);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MilestoneDescription // instanceof handles nulls
                && milestoneDescription.equals(((MilestoneDescription) other).milestoneDescription));
    }

    @Override
    public String toString() {
        return milestoneDescription;
    }
}
