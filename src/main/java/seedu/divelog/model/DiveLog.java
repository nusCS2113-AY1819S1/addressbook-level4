package seedu.divelog.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.enums.SortingMethod;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.DiveSessionList;
import seedu.divelog.model.dive.exceptions.DiveNotFoundException;
import seedu.divelog.model.dive.exceptions.DiveOverlapsException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;


/**
 * Wraps all data at the divelog-book level
 * Duplicates are not allowed (by .isSameDiveSession comparison)
 */
public class DiveLog implements ReadOnlyDiveLog {

    protected final DiveSessionList diveSessions;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        diveSessions = new DiveSessionList();
    }

    public DiveLog() {}

    /**
     * Creates an DiveLog using the DiveSessions in the {@code toBeCopied}
     */
    public DiveLog(ReadOnlyDiveLog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the dive list with {@code diveSessions}.
     * {@code diveSessions} must not contain duplicate diveSessions.
     * @param diveSessions
     */
    public void setDives(List<DiveSession> diveSessions) {
        this.diveSessions.setDives(diveSessions);
    }

    /**
     * Resets the existing data of this {@code DiveLog} with {@code newData}.
     */
    public void resetData(ReadOnlyDiveLog newData) {
        requireNonNull(newData);

        setDives(newData.getDiveList());
    }

    //// DiveSession-level operations

    /**
     * Returns true if a dive session with the same identity as {@code DiveSession} exists in the divelog book.
     * @param diveSession
     */
    public boolean hasDive(DiveSession diveSession) {
        requireNonNull(diveSession);
        return diveSessions.contains(diveSession);
    }

    /**
     * Adds a dive session to the dive log. The dive will be checked for consistency. If the update fails the DiveLog
     * will not be updated.
     * @param diveSession - A new dive to be added
     * @throws LimitExceededException if the dive is too long and violates PADI's guidelines
     * @throws InvalidTimeException if the time input is weird.
     * @throws DiveOverlapsException if the dive has an overlap
     */
    public void addDive(DiveSession diveSession)
            throws DiveOverlapsException, InvalidTimeException, LimitExceededException {
        DiveSessionList tempDiveSessionList = new DiveSessionList();
        tempDiveSessionList.setDives(diveSessions);
        tempDiveSessionList.add(diveSession);
        if (tempDiveSessionList.hasOverlap()) {
            throw new DiveOverlapsException();
        }
        tempDiveSessionList.recalculatePressureGroup();
        diveSessions.setDives(tempDiveSessionList);
    }

    /**
     * Replaces the given dive {@code target} in the list with {@code editedDive}.
     * {@code target} must not violate the DiveLog object. Like the {@code addDive} function, this function is
     * transactional meaning that if a dive is invalid the transaction will be rolled back to the last good state.
     * The dive of {@code editedDive} must not be the same as another existing dive session in the divelog book.
     * @param target - The dive you wish to edit
     * @param editedDive - The dive you want to edit
     * @throws DiveNotFoundException if the target dive does not exist
     * @throws LimitExceededException if the editedDive violates limits.
     * @throws InvalidTimeException if the editedDive has a malformatted time
     */
    public void updateDive(DiveSession target, DiveSession editedDive)
            throws DiveNotFoundException, DiveOverlapsException, InvalidTimeException, LimitExceededException {
        requireNonNull(editedDive);
        DiveSessionList tempDiveSessionList = new DiveSessionList();
        tempDiveSessionList.setDives(diveSessions);
        tempDiveSessionList.setDiveSession(target, editedDive);
        if (tempDiveSessionList.hasOverlap()) {
            throw new DiveOverlapsException();
        }
        tempDiveSessionList.recalculatePressureGroup();
        diveSessions.setDives(tempDiveSessionList);
    }

    /**
     * Removes {@code key} from this {@code DiveLog}.
     * {@code key} must exist in the divelog book.
     * @throws DiveNotFoundException if the five is not found
     */
    public void removeDive(DiveSession key) throws DiveNotFoundException {
        diveSessions.remove(key);
        try {
            diveSessions.recalculatePressureGroup();
        } catch (LimitExceededException | InvalidTimeException e) {
            //This should technically never happen as the add and edit are now transactional,
            //and removing dives will always make things safer
            Logger logger = LogsCenter.getLogger(DiveLog.class);
            logger.severe("Something went wrong with the pressure group calculation."
                    + "The divelog seemed to be in an invalid state." + e.toString());
        }
    }

    /**
     * Sorts dive session according to their category
     * @param sortByCategory
     */
    public void sortDiveSession(SortingMethod sortByCategory) {
        diveSessions.sortDiveSession(sortByCategory);
    }

    /**
     * Returns the most recent dive session
     * @return a DiveSession object
     */
    public DiveSession getMostRecentDive() {
        return diveSessions.getMostRecentDive();
    }

    /**
     * This method recalculates dive pressure groups. It assumes the oldest dive has the correct starting pressure.
     *
     */
    public void recalculatePressureGroups() throws LimitExceededException, InvalidTimeException {
        diveSessions.recalculatePressureGroup();
    }
    //// util methods

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(diveSessions.asUnmodifiableObservableList().size() + " diveSessions:\n");
        sb.append(diveSessions.toString());
        return sb.toString();
    }

    @Override
    public ObservableList<DiveSession> getDiveList() {
        return diveSessions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiveLog // instanceof handles nulls
                && diveSessions.equals(((DiveLog) other).diveSessions));
    }

    @Override
    public int hashCode() {
        return diveSessions.hashCode();
    }
}
