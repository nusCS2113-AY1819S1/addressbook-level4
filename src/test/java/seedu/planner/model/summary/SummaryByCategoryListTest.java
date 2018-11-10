package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.CHICKENRICE;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.MALA;
import static seedu.planner.testutil.TypicalRecords.RANDOM;
import static seedu.planner.testutil.TypicalRecords.WORK;
import static seedu.planner.testutil.TypicalRecords.ZT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.SummaryBuilder;
//@@author tenvinc

public class SummaryByCategoryListTest {

    private static final Record CAIFAN_A =
            new RecordBuilder(CAIFAN).withTags("A").build();
    private static final Record INDO_A =
            new RecordBuilder(INDO).withTags("A").build();
    private static final Record INDO_B =
            new RecordBuilder(INDO).withTags("B").build();
    private static final Record RANDOM_C =
            new RecordBuilder(RANDOM).withTags("C").build();
    private static final Record WORK_D =
            new RecordBuilder(WORK).withTags("D").build();
    private static final Record MALA_D =
            new RecordBuilder(MALA).withTags("D").build();
    private static final Record MALA_E =
            new RecordBuilder(MALA).withTags("E").build();
    private static final Record ZT_F =
            new RecordBuilder(ZT).withTags("F").build();
    private static final Record CHICKEN_RICE_G =
            new RecordBuilder(CHICKENRICE).withTags("G").build();


    private static final List<Record> recordListAllUniqueCategories = Arrays.asList(CAIFAN_A, INDO_B, RANDOM_C,
            WORK_D, MALA_E, ZT_F, CHICKEN_RICE_G);

    private static List<Record> recordListOverlappingCategories = Arrays.asList(CAIFAN_A, INDO_A, RANDOM_C,
            WORK_D, MALA_D, ZT_F, CHICKEN_RICE_G);

    @Test
    public void equals() {
        SummaryByCategoryList summaryByCategoryListOne = new SummaryByCategoryList(recordListAllUniqueCategories);
        SummaryByCategoryList summaryByCategoryListTwo = new SummaryByCategoryList(recordListOverlappingCategories);

        // same object -> returns true
        assertTrue(summaryByCategoryListOne.equals(summaryByCategoryListOne));

        // same values -> returns true
        SummaryByCategoryList summaryByCategoryListOneCopy = new SummaryByCategoryList(recordListAllUniqueCategories);
        assertTrue(summaryByCategoryListOne.equals(summaryByCategoryListOneCopy));

        // different types -> returns false
        assertFalse(summaryByCategoryListOne.equals(1));

        // different SummaryList types -> returns false
        SummaryByDateList summaryByByDateList =
                new SummaryByDateList(recordListAllUniqueCategories);
        assertFalse(summaryByCategoryListOne.equals(summaryByByDateList));

        // null -> returns false
        assertFalse(summaryByCategoryListOne.equals(null));

        // different values -> returns false
        assertFalse(summaryByCategoryListOne.equals(summaryByCategoryListTwo));
    }

    @Test
    public void constructor_nullParam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryByCategoryList(
                null));
    }

    @Test
    public void constructor_recordListWithUniqueCategoriesNoFilter_success() {
        HashMap<Set<Tag>, Summary<Set<Tag>>> expectedMap = new HashMap<>();
        for (Record r : recordListAllUniqueCategories) {
            expectedMap.put(r.getTags(), new Summary<>(r, r.getTags()));
        }
        assertEquals(new SummaryByCategoryList(recordListAllUniqueCategories).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithOverlappingCategoriesNoFilter_success() {
        Summary<Set<Tag>> summaryA = new SummaryBuilder(CAIFAN_A).buildCategorySummary();
        summaryA.add(INDO_A);

        Summary<Set<Tag>> summaryD = new SummaryBuilder(WORK_D).buildCategorySummary();
        summaryD.add(MALA_D);

        List<Summary<Set<Tag>>> summaryListOverLappingCategories = Arrays.asList(summaryA,
                new SummaryBuilder(RANDOM_C).buildCategorySummary(), summaryD,
                new SummaryBuilder(ZT_F).buildCategorySummary(),
                new SummaryBuilder(CHICKEN_RICE_G).buildCategorySummary());

        HashMap<Set<Tag>, Summary<Set<Tag>>> expectedMap = new HashMap<>();
        for (Summary<Set<Tag>> s : summaryListOverLappingCategories) {
            expectedMap.put(s.getIdentifier(), s);
        }
        assertEquals(new SummaryByCategoryList(recordListOverlappingCategories).getSummaryMap(),
                expectedMap);
    }
}
