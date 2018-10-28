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
                        return self.getModuleCode().compareTo(other.getModuleCode());
                    }
                    case ("deadlines"): {
                        return self.getDeadline().toString().compareTo(other.getDeadline().toString());
                    }
                    case ("priority"): {
                        return Integer.valueOf(self.getPriorityLevelInt())
                                .compareTo(Integer.valueOf(other.getPriorityLevelInt()));
                    }
                    case ("title"): {
                        return self.getTitle().compareTo(other.getTitle());
                    }
                    default:
                        return 0;
                }
            }

        });
        return internalList;
    }
}
