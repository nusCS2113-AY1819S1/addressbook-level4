package seedu.address.storage.recruitment;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.WorkExp;


/**
 * JAXB-friendly version of the Recruitment.
 */
public class XmlAdaptedRecruitment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "recruitment's %s field is missing!";

    @XmlElement(required = true)
    private String post;
    @XmlElement(required = true)
    private String workExp;
    @XmlElement(required = true)
    private String jobDescription;

    /**
     * Constructs an XmlAdaptedRecruitment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecruitment() {}

    /**
     * Constructs an {@code XmlAdaptedRecruitment} with the given recruitmentPost details.
     */
    public XmlAdaptedRecruitment(String post, String workExp, String jobDescription) {
        this.post = post;
        this.workExp = workExp;
        this.jobDescription = jobDescription;
    }

    /**
     * Converts a given Recruitment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecruitment
     */
    public XmlAdaptedRecruitment(Recruitment source) {
        post = source.getPost().value;
        workExp = source.getWorkExp().workExp;
        jobDescription = source.getJobDescription().value;
    }

    /**
     * Converts this jaxb-friendly adapted recruitment object into the model's Recruitment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recruitment
     */
    public Recruitment toModelPost() throws IllegalValueException {

        if (post == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Post.class.getSimpleName()));
        }
        if (!Post.isValidPost(post)) {
            throw new IllegalValueException(Post.MESSAGE_POST_CONSTRAINTS);
        }
        final Post modelPost = new Post(post);

        if (workExp == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, WorkExp.class.getSimpleName()));
        }
        if (!WorkExp.isValidWorkExp(workExp)) {
            throw new IllegalValueException(WorkExp.MESSAGE_WORK_EXP_CONSTRAINTS);
        }
        final WorkExp modelWorkExp = new WorkExp(workExp);

        if (jobDescription == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, JobDescription.class.getSimpleName()));
        }
        if (!JobDescription.isValidJobDescription(jobDescription)) {
            throw new IllegalValueException(JobDescription.MESSAGE_JOB_DESCRIPTION_CONSTRAINTS);
        }
        final JobDescription modelJobDescription = new JobDescription(jobDescription);

        return new Recruitment(modelPost, modelWorkExp, modelJobDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecruitment)) {
            return false;
        }

        XmlAdaptedRecruitment otherRecruitment = (XmlAdaptedRecruitment) other;
        return Objects.equals(post, otherRecruitment.post)
                && Objects.equals(workExp, otherRecruitment.workExp)
                && Objects.equals(jobDescription, otherRecruitment.jobDescription);
    }
}
