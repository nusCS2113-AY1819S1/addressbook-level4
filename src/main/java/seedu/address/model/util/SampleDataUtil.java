package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 * TODO: At least 6 sets of Tasks
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Deadline("1/11/2018"), "CS2113", "Complete code refactoring",
                    "refer to notes", new PriorityLevel("high"), 2),
            new Task(new Deadline("2/11/2018"), "CG2271", "Complete lab 4",
                    "Synchronization", new PriorityLevel("medium"), 2),
            new Task(new Deadline("3/11/2018"), "CS2101", "Prepare presentation",
                    "slides not done", new PriorityLevel("low"), 2),
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
