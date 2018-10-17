package seedu.planner.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.RecordCardHandle;
import guitests.guihandles.RecordListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.planner.model.record.Record;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RecordCardHandle expectedCard, RecordCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getMoneyFlow(), actualCard.getMoneyFlow());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRecord}.
     */
    public static void assertCardDisplaysRecord(Record expectedRecord, RecordCardHandle actualCard) {
        assertEquals(expectedRecord.getName().fullName, actualCard.getName());
        assertEquals(expectedRecord.getDate().value, actualCard.getDate());
        assertEquals(expectedRecord.getMoneyFlow().value, actualCard.getMoneyFlow());
        assertEquals(expectedRecord.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code recordListPanelHandle} displays the details of {@code records} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecordListPanelHandle recordListPanelHandle, Record... records) {
        for (int i = 0; i < records.length; i++) {
            recordListPanelHandle.navigateToCard(i);
            assertCardDisplaysRecord(records[i], recordListPanelHandle.getRecordCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code recordListPanelHandle} displays the details of {@code records} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecordListPanelHandle recordListPanelHandle, List<Record> records) {
        assertListMatching(recordListPanelHandle, records.toArray(new Record[0]));
    }

    /**
     * Asserts the size of the list in {@code recordListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RecordListPanelHandle recordListPanelHandle, int size) {
        int numberOfPeople = recordListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
