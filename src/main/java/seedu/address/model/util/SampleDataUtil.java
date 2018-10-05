package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 * TODO: At least 6 sets of Tasks
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task("1/1", "Complete code refactoring", "refer to notes", new PriorityLevel("high")),
        };
    }

    public static ReadOnlyTaskBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
    }

    //    /**
    //     * Returns a tag set containing the list of strings given.
    //     */
    //    public static Set<Tag> getTagSet(String... strings) {
    //        return Arrays.stream(strings)
    //                .map(Tag::new)
    //                .collect(Collectors.toSet());
    //    }

}
