package seedu.address.model.task;

//@@author JeremyInElysium


/**
 * Represents a Milestone for any Task in the TaskBook
 */
public class Milestone implements Comparable<Milestone> {
    private final MilestoneDescription milestoneDescription;
    private final Rank rank;


    public Milestone(MilestoneDescription milestoneDescription, Rank rank) {
        //super(title, milestoneDescription, new PriorityLevel("high"));
        this.milestoneDescription = milestoneDescription;
        this.rank = rank;
    }

    public MilestoneDescription getMilestoneDescription() {
        return milestoneDescription;
    }

    public String getMilestoneDescriptionString() {
        return milestoneDescription.milestoneDescription;
    }


    public Rank getRank() {
        return rank;
    }

    public String getRankString() {
        return rank.rank;
    }


    /**
     * Returns true if both tasks have the same deadline and title.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameMilestone(Milestone otherMilestone) {
        if (otherMilestone == this) {
            return true;
        }

        return otherMilestone != null
                && otherMilestone.getMilestoneDescription().equals(getMilestoneDescription())
                && otherMilestone.getRank().equals(getRank());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Milestone // instanceof handles nulls
                && rank.equals(((Milestone) other).rank));
    }

    /**
     * To ensure that the milestoneList is sorted by the rank of each milestone
     */
    @Override
    public int compareTo(Milestone other) {
        return Integer.compare(this.rank.getRankInteger(), other.rank.getRankInteger());
    }

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
