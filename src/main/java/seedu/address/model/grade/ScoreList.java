package seedu.address.model.grade;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;

import java.util.Objects;
import java.util.Set;
/**
 * Represents a scoreList in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ScoreList {

    private final Set<Test> scoreList = new HashSet<>();

    public ScoreList(Set<Test> scoreList) {
        requireAllNonNull(scoreList);
        this.scoreList.addAll(scoreList);
    }

    public Set<Test> getScoreList() {
        return Collections.unmodifiableSet(scoreList);
    }

    public boolean isSameList(ScoreList otherList) {
        if (otherList == this) {
            return true;
        }
        return otherList != null
                && otherList.getScoreList().equals(getScoreList());
    }

    /**
     * Returns true if both tests have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ScoreList)) {
            return false;
        }
        ScoreList otherList = (ScoreList) other;
        return otherList.getScoreList().equals(getScoreList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoreList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Score List ");
        getScoreList().forEach(builder::append);
        return builder.toString();
    }
}
