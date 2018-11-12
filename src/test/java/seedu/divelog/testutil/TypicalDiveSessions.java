package seedu.divelog.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveOverlapsException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

/**
 * A utility class containing a list of {@code DiveSession} objects to be used in tests.
 */
public class TypicalDiveSessions {

    public static final DiveSession DIVE_AT_BALI = new DiveSessionBuilder().build();
    public static final DiveSession DIVE_AT_TIOMAN = new DiveSessionBuilder()
            .withLocation("Tioman")
            .withStart("1000")
            .withSafetyStop("1025")
            .withEnd("1030")
            .build();
    public static final DiveSession DIVE_AT_NOON = new DiveSessionBuilder()
            .withLocation("Tioman")
            .withStart("1200")
            .withSafetyStop("1245")
            .withEnd("1300")
            .build();
    public static final DiveSession DIVE_AT_NIGHT = new DiveSessionBuilder()
            .withLocation("Tioman")
            .withStart("2100")
            .withSafetyStop("2145")
            .withEnd("2200")
            .build();

    public static final String KEYWORD_MATCHING_TIOMAN = "tioman";

    private TypicalDiveSessions() {} // prevents instantiation

    /**
     * Returns an {@code DiveLog} with typical dive sessions.
     */
    public static DiveLog getTypicalDiveLog() {

        DiveLog ab = new DiveLog();

        for (DiveSession dive : getTypicalDives()) {
            try {
                ab.addDive(dive);
            } catch (InvalidTimeException | LimitExceededException | DiveOverlapsException de) {
                throw new AssertionError("Typical dive log should be a valid log! Recieved a " + de.toString());
            }
        }

        return ab;
    }

    public static List<DiveSession> getTypicalDives() {
        return new ArrayList<>(Arrays.asList(DIVE_AT_BALI, DIVE_AT_TIOMAN));
    }
}
