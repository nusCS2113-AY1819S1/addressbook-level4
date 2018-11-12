package seedu.divelog.model.dive;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.enums.SortingMethod;
import seedu.divelog.commons.util.CollectionUtil;
import seedu.divelog.commons.util.CompareUtil;
import seedu.divelog.logic.pressuregroup.PressureGroupLogic;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.exceptions.DiveNotFoundException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

/**
 * Stores a list of dives
 */
public class DiveSessionList implements Iterable<DiveSession> {
    private static final float SIX_HOURS_IN_MINUTES = (6 * 60) - 1;

    private final ObservableList<DiveSession> internalList = FXCollections.observableArrayList();
    /**
     * Returns true if the list contains an equivalent dive session as the given argument.
     */
    public boolean contains(DiveSession toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    //@@author Cjunx
    /**
     * Sorts the InternalList based on Time
     * Can be scaled to sort based on other things
     */
    public void sortDiveSession(SortingMethod sortByCategory) {
        Comparator<DiveSession> dateTimeComparator = (one, two) -> {
            Date dateTime1 = one.getDiveUtcDateStart();
            Date dateTime2 = two.getDiveUtcDateEnd();
            return dateTime2.compareTo(dateTime1);
        };

        switch(sortByCategory) {
        default:
            FXCollections.sort(internalList, dateTimeComparator);
            break;
        }
    }

    //@@author arjo129
    /**
     * Gets the most recent dive.
     * @return a handle to the DiveSession, returns null if the list is empty
     */
    public DiveSession getMostRecentDive() {
        Logger log = LogsCenter.getLogger(DiveSessionList.class);
        log.info("Retrieving latest dive");
        DiveSession mostRecent = null;
        for (DiveSession diveSession: internalList) {
            if (mostRecent == null
                    && CompareUtil.getCurrentUtcTime().compareTo(diveSession.getDiveUtcDateStart()) > 0) {
                mostRecent = diveSession;
                continue;
            }

            if (mostRecent == null) {
                continue;
            }


            if (mostRecent.compareTo(diveSession) < 0
                    && CompareUtil.getCurrentUtcTime().compareTo(diveSession.getDiveUtcDateStart()) > 0) {
                log.severe("Updating dive " + mostRecent.toString() + "to" + diveSession.toString());
                mostRecent = diveSession;
            }
        }
        return mostRecent;
    }

    /**
     * Recalculate pressure groups for all dives. Assumes oldest dive is the correct starting point.
     * @throws LimitExceededException if the new dive cannot be accommodated within the system.
     * @throws InvalidTimeException if the dive data is malformed
     */
    public void recalculatePressureGroup() throws LimitExceededException, InvalidTimeException {
        //sort dives
        sortDiveSession(SortingMethod.TIME);
        Logger logger = LogsCenter.getLogger(DiveSessionList.class);

        //iterate through list and solve dives
        logger.info("Recalculating dives");

        //No dives to solve
        if (internalList.size() == 0) {
            return;
        }

        //Iterate through all dives
        internalList.get(internalList.size() - 1).computePressureGroupNonRepeated();
        DiveSession prevDive = internalList.get(internalList.size() - 1);
        for (int i = internalList.size() - 2; i >= 0; i--) {
            float surfaceInterval = prevDive.getTimeBetweenDiveSession(internalList.get(i));
            if (surfaceInterval > SIX_HOURS_IN_MINUTES) {
                internalList.get(i).computePressureGroupNonRepeated();
            } else {
                PressureGroup newPg = PressureGroupLogic.computePressureGroupAfterSurfaceInterval(
                        prevDive.getPressureGroupAtEnd(), surfaceInterval);
                internalList.get(i).setPressureGroupAtBeginning(newPg);
                internalList.get(i).computePressureGroupComputeRepeated();
            }

            prevDive = internalList.get(i);
        }
    }

    /**
     * Checks if the dive list has any overlaps
     * @return True if dives overlap (this is bad), false otherwise
     */
    public boolean hasOverlap() {

        FXCollections.sort(internalList);

        for (int i = 1; i < internalList.size(); i++) {
            Date endOfLastDive = internalList.get(i - 1).getDiveUtcDateEnd();
            Date startOfNextDive = internalList.get(i).getDiveUtcDateStart();
            if (endOfLastDive.compareTo(startOfNextDive) > 0) {
                return true;
            }
        }
        return false;
    }
    //@@author

    /**
     * Adds a Dive Session to the list.
     * If planning mode, adds to planningInternalList;
     */
    public void add(DiveSession toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        sortDiveSession(SortingMethod.TIME);
    }

    /**
     * Replaces the dive session {@code target} in the list with {@code editedDiveSession}.
     * {@code target} must exist in the list.
     * The dive of {@code editedDiveSession} must not be the same as another existing dive session in the list.
     */
    public void setDiveSession(DiveSession target, DiveSession editedDiveSession) throws DiveNotFoundException {
        CollectionUtil.requireAllNonNull(target, editedDiveSession);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DiveNotFoundException();
        }
        sortDiveSession(SortingMethod.TIME);
        internalList.set(index, editedDiveSession);
    }

    /**
     * Removes the equivalent dive from the list.
     * The dive session must exist in the list.
     */
    public void remove(DiveSession toRemove) throws DiveNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DiveNotFoundException();
        }
    }


    /**
     * Sets all the dives in a dive session list.
     * @param replacement
     */
    public void setDives(DiveSessionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sortDiveSession(SortingMethod.TIME);

    }

    /**
     * Replaces the contents of this list with {@code diveSessions}.
     *
     */
    public void setDives(List<DiveSession> diveSessions) {
        CollectionUtil.requireAllNonNull(diveSessions);
        internalList.setAll(diveSessions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<DiveSession> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {

        if (!(other instanceof DiveSessionList)) {
            return false;
        }

        DiveSessionList otherDiveList = (DiveSessionList) other;

        otherDiveList.sortDiveSession(SortingMethod.TIME);
        sortDiveSession(SortingMethod.TIME);

        if (otherDiveList.internalList.size() != internalList.size()) {
            return false;
        }

        for (int i = 0; i < internalList.size(); i++) {
            if (!internalList.get(i).equals(otherDiveList.internalList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (DiveSession diveSession: internalList) {
            stringBuilder.append(diveSession);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public Iterator<DiveSession> iterator() {
        return null;
    }
}
