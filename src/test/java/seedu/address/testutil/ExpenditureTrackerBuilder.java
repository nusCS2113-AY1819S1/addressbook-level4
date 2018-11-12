//@@author SHININGGGG
package seedu.address.testutil;

import seedu.address.model.ExpenditureTracker;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * A utility class to help with building ExpenditureTracker objects.
 * Example usage: <br>
 *     {@code ExpenditureTracker et = new ExpenditureTrackerBuilder().withExpenditure("Food", "Drink").build();}
 */
public class ExpenditureTrackerBuilder {

    private ExpenditureTracker expenditureTracker;

    public ExpenditureTrackerBuilder() {
        expenditureTracker = new ExpenditureTracker();
    }

    public ExpenditureTrackerBuilder(ExpenditureTracker expenditureTracker) {
        this.expenditureTracker = expenditureTracker;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public ExpenditureTrackerBuilder withExpenditure(Expenditure expenditure) {
        expenditureTracker.addExpenditure(expenditure);
        return this;
    }

    public ExpenditureTracker build() {
        return expenditureTracker;
    }
}
