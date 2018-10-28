package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.MilestoneDescription;
import seedu.address.model.task.Rank;


//@@author JeremyInElysium
/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedMilestone {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Milestone's %s field is missing.";

    @XmlElement
    private String descrip;
    @XmlElement
    private String rank;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */

    public XmlAdaptedMilestone() {}

    /**
     * Constructs a {@code XmlAdaptedMilestone} with the given {@code milestone}.
     */
    public XmlAdaptedMilestone(MilestoneDescription milestoneDescription, Rank rank) {
        this.descrip = milestoneDescription.getMilestoneDescription();
        this.rank = rank.getRank();
    }

    /**
     * Converts a given Milestone into this class for JAXB use.
     * @param source future changes to this will not affect the created
     */

    public XmlAdaptedMilestone(Milestone source) {
        descrip = source.getMilestoneDescriptionString();
        rank = source.getRankString();
    }

    /**
     * Converts this jaxb-friendly adapted milestone object into the model's Milestone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */

    public Milestone toModelType() throws IllegalValueException {
        if (descrip == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, MilestoneDescription.class.getSimpleName()));
        }

        if (!MilestoneDescription.isValidMilestoneDescription(descrip)) {
            throw new IllegalValueException(MilestoneDescription.MESSAGE_MILESTONEDESCRIPTION_CONSTRAINTS);
        }
        final MilestoneDescription modelMilestoneDescription = new MilestoneDescription(descrip);

        if (rank == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Rank.class.getSimpleName()));
        }

        if (!Rank.isValidRank(rank)) {
            throw new IllegalValueException(Rank.MESSAGE_RANK_CONSTRAINTS);
        }
        final Rank modelRank = new Rank(rank);


        return new Milestone(modelMilestoneDescription, modelRank);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMilestone)) {
            return false;
        }

        XmlAdaptedMilestone otherMilestone = (XmlAdaptedMilestone) other;
        return Objects.equals(descrip, otherMilestone.descrip)
                && Objects.equals(rank, otherMilestone.rank);
    }
}

