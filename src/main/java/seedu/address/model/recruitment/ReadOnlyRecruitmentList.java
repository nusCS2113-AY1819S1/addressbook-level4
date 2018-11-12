package seedu.address.model.recruitment;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an recruitmentList
 */
public interface ReadOnlyRecruitmentList {

    /**
     * Returns an unmodifiable view of the recruitmentPost list.
     * This list will not contain any duplicate recruitment posts.
     */
    ObservableList<Recruitment> getRecruitmentList();

}
