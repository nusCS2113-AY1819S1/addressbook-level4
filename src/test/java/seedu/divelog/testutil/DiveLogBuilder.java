package seedu.divelog.testutil;

import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveOverlapsException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

/**
 * A utility class to help with building _valid_ DiveLog objects.
 * Example usage: <br>
 *     {@code DiveLog ab = new DiveLogBuilder().withDiveSession("John", "Doe").build();}
 */
public class DiveLogBuilder {

    private DiveLog diveLog;

    public DiveLogBuilder() {
        diveLog = new DiveLog();
    }

    public DiveLogBuilder(DiveLog diveLog) {
        this.diveLog = diveLog;
    }

    /**
     * Adds a new {@code DiveSession} to the {@code DiveLog} that we are building.
     * @param dive
     * @throws AssertionError if the class is misused to produce an invalid diveLog.
     */
    public DiveLogBuilder withDive(DiveSession dive) {
        try {
            diveLog.addDive(dive);
        } catch (LimitExceededException | InvalidTimeException | DiveOverlapsException le) {
            throw new AssertionError(
                    "You should only be building valid dives for example test cases instead got" + le.toString());
        }
        return this;
    }

    public DiveLog build() {
        return diveLog;
    }
}
