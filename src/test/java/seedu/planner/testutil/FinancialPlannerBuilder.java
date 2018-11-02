package seedu.planner.testutil;

import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.record.Record;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FinancialPlanner ab = new FinancialPlannerBuilder().withRecord("John", "Doe").build();}
 */
public class FinancialPlannerBuilder {

    private FinancialPlanner financialPlanner;

    public FinancialPlannerBuilder() {
        financialPlanner = new FinancialPlanner();
    }

    public FinancialPlannerBuilder(FinancialPlanner financialPlanner) {
        this.financialPlanner = financialPlanner;
    }

    /**
     * Adds a new {@code Record} to the {@code FinancialPlanner} that we are building.
     */
    public FinancialPlannerBuilder withRecord(Record record) {
        financialPlanner.addRecord(record);
        return this;
    }

    public FinancialPlanner build() {
        return financialPlanner;
    }
}
