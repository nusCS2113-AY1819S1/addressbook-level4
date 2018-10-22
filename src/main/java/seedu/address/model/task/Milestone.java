package seedu.address.model.task;

//@@author JeremyInElysium
/**
 * Represents a Milestone for any Task in the TaskBook
 */
public class Milestone {
    //private final String title;
    private final String milestoneDescription;
    private final String rank;

    public Milestone(String milestoneDescription, String rank) {
        //super(title, milestoneDescription, new PriorityLevel("high"));
        this.milestoneDescription = milestoneDescription;
        this.rank = rank;
    }

    public String getMilestoneDescription() {
        return milestoneDescription;
    }

    public String getRank() {
        return rank;
    }

    //need to edit this also
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Milestone ")
                .append(getRank())
                .append(": ")
                .append(getMilestoneDescription());
        return builder.toString();
    }

}
