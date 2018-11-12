package seedu.address.model.recruitment;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRecruitment comparison)
 */
public class RecruitmentList implements ReadOnlyRecruitmentList {

    private final UniqueRecruitmentList recruitments;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        recruitments = new UniqueRecruitmentList();
    }

    public RecruitmentList() {}

    /**
     * Creates an AddressBook using the Recruitment in the {@code toBeCopied}
     */
    public RecruitmentList(ReadOnlyRecruitmentList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the recruitmentPost list with {@code recruitment}.
     * {@code recruitment} must not contain duplicate recruitment.
     */
    public void setRecruitments(List<Recruitment> recruitments) {
        this.recruitments.setRecruitments(recruitments);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRecruitmentList newData) {
        requireNonNull(newData);

        setRecruitments(newData.getRecruitmentList());
    }

    //// recruitment-level operations

    /**
     * Returns true if a recruitment with the same identity as {@code recruitment} exists in the address book.
     */
    public boolean hasRecruitment(Recruitment recruitment) {
        requireNonNull(recruitment);
        return recruitments.contains(recruitment);
    }

    /**
     * Adds a recruitment to the address book.
     * The recruitmentPost must not already exist in the address book.
     */
    public void addRecruitment(Recruitment recruitment) {
        recruitments.add(recruitment);
    }

    /**
     * Replaces the given recruitmentPost {@code target} in the recruitmentList with {@code editedRecruitment}.
     * {@code target} must exist in the address book.
     * The recruitmentPost identity of {@code editedRecruitment} must not be the same as another existing
     * recruitmentPost in the address book.
     */
    public void updateRecruitment(Recruitment recruitment, Recruitment editedRecruitment) {
        requireNonNull(editedRecruitment);

        recruitments.setRecruitment(recruitment, editedRecruitment);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeRecruitment(Recruitment key) {
        recruitments.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return recruitments.asUnmodifiableObservableList().size() + " recruitments";
        // TODO: refine later
    }

    @Override
    public ObservableList<Recruitment> getRecruitmentList() {
        return recruitments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecruitmentList // instanceof handles nulls
                && recruitments.equals(((RecruitmentList) other).recruitments));
    }

    @Override
    public int hashCode() {
        return recruitments.hashCode();
    }
}
