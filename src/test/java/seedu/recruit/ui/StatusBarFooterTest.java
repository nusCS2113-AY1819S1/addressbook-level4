package seedu.recruit.ui;

import static org.junit.Assert.assertEquals;
import static seedu.recruit.testutil.EventsUtil.postNow;
import static seedu.recruit.testutil.TypicalPersons.ALICE;
import static seedu.recruit.ui.StatusBarFooter.SYNC_CANDIDATE_STATUS_INITIAL;
import static seedu.recruit.ui.StatusBarFooter.SYNC_CANDIDATE_STATUS_UPDATED;
import static seedu.recruit.ui.StatusBarFooter.TOTAL_CANDIDATES_STATUS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.recruit.commons.events.model.CandidateBookChangedEvent;
import seedu.recruit.testutil.AddressBookBuilder;

@Ignore
public class StatusBarFooterTest extends GuiUnitTest {

    private static final Path STUB_CANDIDATE_SAVE_LOCATION = Paths.get("Stub");
    private static final Path CANDIDATE_RELATIVE_PATH = Paths.get(".");
    private static final Path STUB_COMPANY_SAVE_LOCATION = Paths.get("Stub");
    private static final Path COMPANY_RELATIVE_PATH = Paths.get(".");

    private static final CandidateBookChangedEvent EVENT_STUB = new CandidateBookChangedEvent(
            new AddressBookBuilder().withPerson(ALICE).build());

    private static final int INITIAL_TOTAL_PERSONS = 0;
    private static final int INITIAL_TOTAL_COMPANIES = 0;

    private static final Clock originalClock = StatusBarFooter.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeClass
    public static void setUpBeforeClass() {
        // inject fixed clock
        StatusBarFooter.setClock(injectedClock);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        StatusBarFooter.setClock(originalClock);
    }

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_CANDIDATE_SAVE_LOCATION, STUB_COMPANY_SAVE_LOCATION,
                                                                INITIAL_TOTAL_PERSONS, INITIAL_TOTAL_COMPANIES);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(CANDIDATE_RELATIVE_PATH.resolve(STUB_CANDIDATE_SAVE_LOCATION).toString(),
                SYNC_CANDIDATE_STATUS_INITIAL, String.format(TOTAL_CANDIDATES_STATUS, INITIAL_TOTAL_PERSONS));

        // after recruit book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(CANDIDATE_RELATIVE_PATH.resolve(STUB_CANDIDATE_SAVE_LOCATION).toString(),
                String.format(SYNC_CANDIDATE_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
                String.format(TOTAL_CANDIDATES_STATUS, EVENT_STUB.data.getCandidateList().size()));
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, the
     * sync status matches that of {@code expectedSyncStatus}, and the
     * total persons matches that of {@code expectedTotalPersonsStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus,
                                        String expectedTotalPersonsStatus) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedTotalPersonsStatus, statusBarFooterHandle.getTotalPersonsStatus());
        guiRobot.pauseForHuman();
    }

}
