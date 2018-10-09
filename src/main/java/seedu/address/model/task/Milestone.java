package seedu.address.model.task;

import java.util.Objects;

/**
 * Represents a Milestone for any Task in the TaskBook
 */
public class Milestone extends Task {
    private final String title;
    private final String milestone_description;
    private final Integer order;

    public Milestone(String title, String milestone_description, Integer order){
        this.title = title;
        this.milestone_description = milestone_description;
        this.order = order;
    }

    public String getTitle(){
        return title;
    }

    public String getMileStoneDescription(){
        return milestone_description;
    }

    public Integer getOrder(){
        return order;
    }

    @Override
    public String toString() = {
            final StringBuilder = new StringBuilder();
            builder.append(getTitle())
                    .append(" : ")
                    .append(getMileStoneDescription())
                    .append(" | ")
                    .append(getOrder());
            return builder.toString();
    }

}
