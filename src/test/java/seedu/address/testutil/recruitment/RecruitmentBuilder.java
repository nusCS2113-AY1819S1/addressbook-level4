package seedu.address.testutil.recruitment;

import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.WorkExp;

/**
 * A utility class to help with building recruitment objects for tests.
 */
public class RecruitmentBuilder {

    public static final String DEFAULT_POST = "IT Manager";
    public static final String DEFAULT_WORK_EXP = "3";
    public static final String DEFAULT_JOB_DESCRIPTION = "To maintain the company server";

    // Data fields
    private Post post;
    private WorkExp workExp;
    private JobDescription jobDescription;

    public RecruitmentBuilder () {
        post = new Post(DEFAULT_POST);
        workExp = new WorkExp(DEFAULT_WORK_EXP);
        jobDescription = new JobDescription(DEFAULT_JOB_DESCRIPTION);
    }

    /**
     * Initializes the RecruitmentBuilder with the data of {@code recruitmentToCopy}.
     */
    public RecruitmentBuilder (Recruitment recruitmentToCopy) {
        post = recruitmentToCopy.getPost();
        workExp = recruitmentToCopy.getWorkExp();
        jobDescription = recruitmentToCopy.getJobDescription();
    }


    /**
     * Sets the {@code recruitmentPost} of the {@code recruitment} that we are building.
     */
    public RecruitmentBuilder withPost(String post) {
        this.post = new Post(post);
        return this;
    }

    /**
     * Sets the {@code WorkExp} of the {@code recruitment} that we are building.
     */
    public RecruitmentBuilder withWorkExp(String workExp) {
        this.workExp = new WorkExp(workExp);
        return this;
    }

    /**
     * Sets the {@code JobDescription} of the {@code recruitment} that we are building.
     */
    public RecruitmentBuilder withJobDescription(String jobDescription) {
        this.jobDescription = new JobDescription(jobDescription);
        return this;
    }

    /**
     * Builds (@code recruitment) with required recruitment's variables
     */
    public Recruitment build() {
        return new Recruitment (post, workExp, jobDescription);
    }


}
