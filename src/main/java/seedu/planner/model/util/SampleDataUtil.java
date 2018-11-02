package seedu.planner.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Name;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FinancialPlanner} with sample data.
 */
public class SampleDataUtil {
    public static Record[] getSampleRecords() {
        return new Record[] {
            new Record(new Name("Alex Yeoh"), new Date("17-9-2004"), new MoneyFlow("+101.30"),
                getTagSet("friends")),
            new Record(new Name("Bernice Yu"), new Date("23-4-1869"), new MoneyFlow("-140.40"),
                getTagSet("colleagues", "friends")),
            new Record(new Name("Charlotte Oliveiro"), new Date("29-5-1999"), new MoneyFlow("+24.49"),
                getTagSet("neighbours")),
            new Record(new Name("David Li"), new Date("30-6-2014"), new MoneyFlow("-437.49"),
                getTagSet("family")),
            new Record(new Name("Irfan Ibrahim"), new Date("30-9-2010"), new MoneyFlow("-437.58"),
                getTagSet("classmates")),
            new Record(new Name("Roy Balakrishnan"), new Date("15-7-2005"), new MoneyFlow("+24.50"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyFinancialPlanner getSampleFinancialPlanner() {
        FinancialPlanner sampleAb = new FinancialPlanner();
        for (Record sampleRecord : getSampleRecords()) {
            sampleAb.addRecord(sampleRecord);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
