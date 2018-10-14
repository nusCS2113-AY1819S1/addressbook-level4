package seedu.address.model.task;

import java.util.Objects;

/**
 * Represents a Milestone for any Task in the TaskBook
 */
public class Milestone extends Task {
    private final String title;
    private final String milestone_description;
    private final Integer rank;

    public Milestone(String title, String milestone_description, Integer rank) {
        this.title = title;
        this.milestone_description = milestone_description;
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public String getMileStoneDescription() {
        return milestone_description;
    }

    public Integer getRank() {
        return rank;
    }

    @Override
    public String toString() = {
            final StringBuilder = new StringBuilder();
            builder.append(getTitle())
                    .append(" : ")
                    .append(getMileStoneDescription())
                    .append(" | ")
                    .append(getRank());
            return builder.toString();
    }

}
