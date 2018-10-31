package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.JAP;
import static seedu.planner.testutil.TypicalRecords.KOREAN;
import static seedu.planner.testutil.TypicalRecords.ZT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;

public class CategoryStatisticsListTest {

    public static final List<Record> TEST_INPUT_LIST = Arrays.asList(INDO, KOREAN, JAP);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullRecordList_throwsException() {
        assertThrows(NullPointerException.class, () -> new CategoryStatisticsList(null));
    }

    @Test
    public void constructor_nonOverlappingRecordList_success() {
        CategoryStatistic indoStat = new CategoryStatistic(INDO);
        CategoryStatistic koreanStat = new CategoryStatistic(KOREAN);
        CategoryStatistic japStat = new CategoryStatistic(JAP);
        HashMap<Set<Tag>, CategoryStatistic> expectedMap = new HashMap<>();
        expectedMap.put(indoStat.getTags(), indoStat);
        expectedMap.put(koreanStat.getTags(), koreanStat);
        expectedMap.put(japStat.getTags(), japStat);
        assertEquals(expectedMap, new CategoryStatisticsList(TEST_INPUT_LIST).getCategoryStatsMap());
    }

    @Test
    public void constructor_overlappingRecordList_success() {
        List<Record> inputList = Arrays.asList(INDO, ZT);
        CategoryStatistic indoStat = new CategoryStatistic(INDO);
        indoStat.add(ZT);
        HashMap<Set<Tag>, CategoryStatistic> expectedMap = new HashMap<>();
        expectedMap.put(indoStat.getTags(), indoStat);
        assertEquals(expectedMap, new CategoryStatisticsList(inputList).getCategoryStatsMap());
    }

    @Test
    public void getReadOnlyList_validInputList_success() {
        CategoryStatistic indoStat = new CategoryStatistic(INDO);
        CategoryStatistic koreanStat = new CategoryStatistic(KOREAN);
        CategoryStatistic japStat = new CategoryStatistic(JAP);
        List<CategoryStatistic> expectedList = Arrays.asList(indoStat, koreanStat, japStat);
        CategoryStatisticsList toTest = new CategoryStatisticsList(TEST_INPUT_LIST);
        assertUnorderedListSame(expectedList, toTest.getReadOnlyStatsList());
    }

    @Test
    public void getReadOnlyList_modifyList_throwsException() {
        CategoryStatisticsList stats = new CategoryStatisticsList(Collections.emptyList());
        thrown.expect(UnsupportedOperationException.class);
        stats.getReadOnlyStatsList().add(new CategoryStatistic(INDO));
    }

    private void assertUnorderedListSame(List<CategoryStatistic> expectedList, List<CategoryStatistic> toTest) {
        assertTrue(expectedList.containsAll(toTest));
    }
}
