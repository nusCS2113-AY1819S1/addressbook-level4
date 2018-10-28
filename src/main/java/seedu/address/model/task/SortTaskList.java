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
                    case ("modules"): {
                        return self.getModuleCode().toLowerCase().compareTo(other.getModuleCode().toLowerCase());
                    }
                    case ("deadlines"): {
                        return self.getDeadline().toString().toLowerCase().compareTo(other.getDeadline()
                                .toString().toLowerCase());
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
