package seedu.address.testutil;

import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Workout;

/**
 *
 */
public class RemarkBuilder {
    public static final String DEFAULT_REMARK = "This workout trains bicep";

    private Remark remark;
    public RemarkBuilder() {
        remark = new Remark(DEFAULT_REMARK);
    }
    public RemarkBuilder(Remark remark) {
        this.remark = remark;
    }

    public RemarkBuilder(Workout workout) {
        remark = workout.getRemark();
    }
    public Remark build() {
        return remark;
    }

}
