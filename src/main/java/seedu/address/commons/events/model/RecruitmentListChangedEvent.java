package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;

/** Indicates the recruitmentList in the model has changed*/
public class RecruitmentListChangedEvent extends BaseEvent {

    public final ReadOnlyRecruitmentList data;

    public RecruitmentListChangedEvent(ReadOnlyRecruitmentList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of recruitment posts " + data.getRecruitmentList().size();
    }
}
