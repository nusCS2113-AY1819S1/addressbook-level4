package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.EventList;
import seedu.address.testutil.TypicalEvents;

public class XmlSerializableEventListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableEventListTest");
    private static final Path TYPICAL_EVENTS_FILE = TEST_DATA_FOLDER.resolve("typicalEventsEventList.xml");
    private static final Path INVALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("invalidEventEventList.xml");
    private static final Path DUPLICATE_EVENT_FILE = TEST_DATA_FOLDER.resolve("duplicateEventEventList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableEventList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EVENTS_FILE,
                XmlSerializableEventList.class);
        EventList eventListFromFile = dataFromFile.toModelType();
        EventList typicalEventsEventList = TypicalEvents.getTypicalEventList();
        assertEquals(eventListFromFile, typicalEventsEventList);
    }

    @Test
    public void toModelType_invalidEventFile_throwsIllegalValueException() throws Exception {
        XmlSerializableEventList dataFromFile = XmlUtil.getDataFromFile(INVALID_EVENT_FILE,
                XmlSerializableEventList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
        XmlSerializableEventList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_EVENT_FILE,
                XmlSerializableEventList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableEventList.MESSAGE_DUPLICATE_EVENT);
        dataFromFile.toModelType();
    }

}
