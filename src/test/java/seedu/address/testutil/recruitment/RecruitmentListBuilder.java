package seedu.address.testutil.recruitment;

import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.RecruitmentList;

/**
 * A utility class to help with building RecruitmentList objects.
 * Example usage: <br>
 *     {@code RecruitmentList rl = new RecruitmentListBuilder().withRecruitment("Recruitment_example",
 *     "Recruitment_example1").build();}
 */
public class RecruitmentListBuilder {
    private RecruitmentList recruitmentList;

    public RecruitmentListBuilder() {
        recruitmentList = new RecruitmentList();
    }

    public RecruitmentListBuilder(RecruitmentList recruitmentList) {
        this.recruitmentList = recruitmentList;
    }

    /**
     * Adds a new {@code recruitment} to the {@code RecruitmentList} that we are building.
     */
    public RecruitmentListBuilder withRecruitment(Recruitment recruitment) {
        recruitmentList.addRecruitment(recruitment);
        return this;
    }

    public RecruitmentList build() {
        return recruitmentList;
    }
}
