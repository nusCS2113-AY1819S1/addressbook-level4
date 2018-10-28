package seedu.planner.model.summary;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;

/**
 * This class represents a list to store the statistics of each category within a certain time period.
 */
public class CategoryStatisticsList {

    private HashMap<Set<Tag>, CategoryStatistic> categoryStats;

    public CategoryStatisticsList(List<Record> recordList) {
        categoryStats = new HashMap<Set<Tag>, CategoryStatistic>();
        for (Record r : recordList) {
            Set<Tag> tags = r.getTags();
            if (categoryStats.containsKey(tags)) {
                categoryStats.get(tags).add(r);
            } else {
                categoryStats.put(r.getTags(), new CategoryStatistic(r));
            }
        }
    }

    /**
     * Returns the contents of the internal {@link HashMap} as a read only {@link ObservableList}
     */
    public ObservableList<CategoryStatistic> getReadOnlyStatsList() {
        List<CategoryStatistic> statsList = categoryStats.keySet().stream()
                .map(s -> categoryStats.get(s)).collect(Collectors.toList());
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(statsList));
    }
}
