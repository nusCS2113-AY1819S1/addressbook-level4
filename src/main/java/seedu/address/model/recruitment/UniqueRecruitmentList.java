package seedu.address.model.recruitment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recruitment.exceptions.DuplicateRecruitmentException;
import seedu.address.model.recruitment.exceptions.RecruitmentNotFoundException;

/**
 * A list of recruitmentPost that enforces uniqueness between its elements and does not allow nulls.
 * A recruitmentPost is considered unique by comparing using {@code Recruitment#isSameRecruitment(Recruitment)}.
 * As such,
 * adding and updating of
 * recruitmentPosts uses Recruitment#isSameRecruitment(Recruitment) for equality so as to ensure
 * that the recruitmentPost being added or updated is
 * unique in terms of identity in the UniqueRecruitmentList. However, the removal of
 * a recruitmentPost uses Recruitment#equals(Object) so
 * as to ensure that the recruitmentPost with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Recruitment#isSameRecruitment(Recruitment)
 */
public class UniqueRecruitmentList implements Iterable<Recruitment> {

    private final ObservableList<Recruitment> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent recruitment as the given argument.
     */
    public boolean contains(Recruitment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecruitment);
    }

    /**
     * Adds a recruitmentPost to the list.
     * The recruitmentPost must not already exist in the list.
     */
    public void add(Recruitment toAddRecruitment) {
        requireNonNull(toAddRecruitment);
        if (contains(toAddRecruitment)) {
            throw new DuplicateRecruitmentException();
        }
        internalList.add(toAddRecruitment);
    }

    /**
     * Replaces the recruitmentPost {@code target} in the list with {@code editedRecruitment}.
     * {@code target} must exist in the list.
     * The recruitmentPost identity of {@code editedRecruitment} must not be the same as another existing
     * recruitmentPost in the list.
     */
    public void setRecruitment(Recruitment target, Recruitment editedRecruitment) {
        requireAllNonNull(target, editedRecruitment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecruitmentNotFoundException();
        }

        if (!target.isSameRecruitment(editedRecruitment) && contains(editedRecruitment)) {
            throw new DuplicateRecruitmentException();
        }

        internalList.set(index, editedRecruitment);
    }

    /**
     * Removes the equivalent recruitmentPost from the list.
     * The recruitmentPost must exist in the list.
     */
    public void remove(Recruitment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecruitmentNotFoundException();
        }
    }

    public void setRecruitments(UniqueRecruitmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code recruitment}.
     * {@code recruitment} must not contain duplicate recruitmentPosts.
     */
    public void setRecruitments(List<Recruitment> recruitments) {
        requireAllNonNull(recruitments);
        if (!personsAreUnique(recruitments)) {
            throw new DuplicateRecruitmentException();
        }

        internalList.setAll(recruitments);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Recruitment> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Recruitment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRecruitmentList // instanceof handles nulls
                && internalList.equals(((UniqueRecruitmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code recruitments} contains only unique recruitmentPosts.
     */
    private boolean personsAreUnique(List<Recruitment> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSameRecruitment(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
