package seedu.address.testutil;

import seedu.address.model.task.Milestone;
import seedu.address.model.task.MilestoneDescription;
import seedu.address.model.task.Rank;

//@@author JeremyInElysium
/**
 * A utility class to help with building Milestone objects.
 */
public class MilestoneBuilder {

    public static final String DEFAULT_MILESTONEDESCRIPTION = "Dummy milestone";
    public static final String DEFAULT_RANK = "1";

    private MilestoneDescription milestoneDescription;
    private Rank rank;

    public MilestoneBuilder() {
        this.milestoneDescription = new MilestoneDescription(DEFAULT_MILESTONEDESCRIPTION);
        this.rank = new Rank(DEFAULT_RANK);
    }

    /**
     * Build the milestone with the parameters set
     * @return Milestone
     */
    public Milestone build() {
        return new Milestone(milestoneDescription, rank);
    }
}
