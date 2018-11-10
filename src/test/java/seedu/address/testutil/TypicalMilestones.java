package seedu.address.testutil;

import seedu.address.model.task.Milestone;

//@@author JeremyInElysium
/**
 * A utility class containing a list of {@code Milestone} objects to be used in tests.
 */
public class TypicalMilestones {
    //Manually added
    public static final Milestone FIRST_MILESTONE = new MilestoneBuilder().withMilestoneDescription("First milestone")
            .withRank("1").build();

    public static final Milestone SECOND_MILESTONE = new MilestoneBuilder().withMilestoneDescription("Second milestone")
            .withRank("2").build();

    private TypicalMilestones() {} //prevents instantiation
}
