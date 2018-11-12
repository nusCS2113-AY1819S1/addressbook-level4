package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.address.model.WorkoutBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends WorkoutBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected WorkoutBook getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void workoutBook_dataFileDoesNotExist_loadSampleData() {
        Workout[] expectedList = SampleDataUtil.getSampleWorkouts();
        assertListMatching(getWorkoutListPanel(), expectedList);
    }
}
