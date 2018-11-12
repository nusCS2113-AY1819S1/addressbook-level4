package seedu.address.model.task;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@@author ChanChunCheong
/**
 * SortTaskList is a comparator for the task book to sort according to lexicographical order
 */
public class SortTaskList {
    /**
     * Return 0 if self = other. Return 1 if self > other. Return -1 if self < other.
     * @param internalList
     * @param method
     * @return SortedList
     */
    public ObservableList<Task> sortTask(ObservableList<Task> internalList, String method) {

        FXCollections.sort(internalList, new Comparator<Task>() {
            @Override
            public int compare(Task self, Task other) {
                switch(method) {
                    case ("module"): {
                        return self.getModuleCode().toString().toLowerCase()
                                .compareTo(other.getModuleCode().toString().toLowerCase());
                    }
                    case ("deadline"): {
                        return self.getDeadline().getDate().compareTo(other.getDeadline().getDate());
                    }
                    case ("priority"): {
                        return self.getPriorityLevelInt() - other.getPriorityLevelInt();
                    }
                    case ("title"): {
                        return self.getTitle().toLowerCase().compareTo(other.getTitle().toLowerCase());
                    }
                    default:
                        return 0;
                }
            }

        });
        return internalList;
    }
}
