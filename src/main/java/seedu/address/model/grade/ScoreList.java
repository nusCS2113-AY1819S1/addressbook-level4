package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.print.attribute.standard.MediaSize;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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
