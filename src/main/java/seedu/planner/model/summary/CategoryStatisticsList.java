package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;
//@@author tenvinc
/**
 * This class represents a list to store the statistics of each category within a certain time period.
 */
public class CategoryStatisticsList {

    private HashMap<Set<Tag>, CategoryStatistic> categoryStats;

    public CategoryStatisticsList(List<Record> recordList) {
        requireNonNull(recordList);
        categoryStats = new HashMap<>();
        for (Record r : recordList) {
            Set<Tag> tags = r.getTags();
            addToCategoryStatistics(r, tags);
        }
    }

    /**
     * Adds the record into the internal hashMap
     * @param record record to be added
     * @param tags the key of the addition
     */
    private void addToCategoryStatistics(Record record, Set<Tag> tags) {
        if (categoryStats.containsKey(tags)) {
            categoryStats.get(tags).add(record);
        } else {
            categoryStats.put(record.getTags(), new CategoryStatistic(record));
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

    public HashMap<Set<Tag>, CategoryStatistic> getCategoryStatsMap() {
        return categoryStats;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryStatisticsList // instanceof handles nulls
                && categoryStats.equals(((CategoryStatisticsList) other).categoryStats));
    }
}
