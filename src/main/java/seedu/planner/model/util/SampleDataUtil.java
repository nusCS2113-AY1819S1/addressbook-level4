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
            new Record(new Name("Shopping with girlfriend"), new Date("21-9-2018"), new MoneyFlow("-101.30"),
                    getTagSet("girlfriend", "shopping")),
            new Record(new Name("Lunch jifan"), new Date("23-4-2018"), new MoneyFlow("-3.50"),
                    getTagSet("colleagues", "friends")),
            new Record(new Name("Lunch with dad"), new Date("29-5-2018"), new MoneyFlow("-24.49"),
                    getTagSet("neighbours")),
            new Record(new Name("Allowance from Dad"), new Date("30-6-2018"), new MoneyFlow("+437.49"),
                    getTagSet("family")),
            new Record(new Name("Payment for fine food"), new Date("30-9-2018"), new MoneyFlow("-7.58"),
                    getTagSet("Utown")),
            new Record(new Name("Payment for western"), new Date("15-7-2018"), new MoneyFlow("-4.50"),
                    getTagSet("food")),
            new Record(new Name("Indo"), new Date("28-2-2018"), new MoneyFlow("-5.6"),
                    getTagSet("friends")),
            new Record(new Name("caifan"), new Date("26-9-2018"), new MoneyFlow("-3.80"),
                    getTagSet("owesMoney", "friends")),
            new Record(new Name("Income from work"), new Date("26-9-2018"), new MoneyFlow("+60.0"),
                    getTagSet("job")),
            new Record(new Name("Payment from ZT"), new Date("25-9-2018"), new MoneyFlow("+5.90"),
                    getTagSet("friends")),
            new Record(new Name("Payment for mala"), new Date("26-9-2018"), new MoneyFlow("-10.50"),
                    getTagSet()),
            new Record(new Name("Payment for chicken rice"), new Date("27-9-2018"), new MoneyFlow("-0.90"),
                    getTagSet("food")),
            new Record(new Name("Random income"), new Date("31-03-2018"), new MoneyFlow("+14.50"),
                    getTagSet("random"))
        };
    }

    public static ReadOnlyFinancialPlanner getSampleFinancialPlanner() {
        FinancialPlanner sampleAb = new FinancialPlanner();
        for (Record sampleRecord : getSampleRecords()) {
            sampleAb.addRecord(sampleRecord);
            sampleAb.addRecordToSummary(sampleRecord);
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
