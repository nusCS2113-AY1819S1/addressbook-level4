package seedu.address.model.task;

//@@author JeremyInElysium
/**
 * Represents a Milestone for any Task in the TaskBook
 */
public class Milestone extends Task {
    //private final String title;
    //private final String milestone_description;
    private final String rank;

    public Milestone(String title, String milestoneDescription, String rank) {
        super(title, milestoneDescription, new PriorityLevel("high"));
        //this.title = title;
        //this.milestoneDescription = milestoneDescription;
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" : ")
                .append(getDescription())
                .append(" | ")
                .append(getRank());
        return builder.toString();
    }

}
