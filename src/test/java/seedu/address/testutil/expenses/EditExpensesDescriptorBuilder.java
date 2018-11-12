package seedu.address.testutil.expenses;

import seedu.address.logic.commands.AddExpensesCommand.EditExpensesDescriptor;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.expenses.MedicalExpenses;
import seedu.address.model.expenses.MiscellaneousExpenses;
import seedu.address.model.expenses.TravelExpenses;

/**
 * A utility class to help with building EditExpensesDescriptor objects.
 */
public class EditExpensesDescriptorBuilder {

    private EditExpensesDescriptor descriptor;

    public EditExpensesDescriptorBuilder() {
        descriptor = new EditExpensesDescriptor();
    }

    public EditExpensesDescriptorBuilder(EditExpensesDescriptor descriptor) {
        this.descriptor = new EditExpensesDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpensesDescriptor} with fields containing {@code person}'s details
     */
    public EditExpensesDescriptorBuilder(Expenses expenses) {
        descriptor = new EditExpensesDescriptor();
        descriptor.setExpensesAmount(expenses.getExpensesAmount());
        descriptor.setTravelExpenses(expenses.getTravelExpenses());
        descriptor.setMedicalExpenses(expenses.getMedicalExpenses());
        descriptor.setMiscellaneousExpenses(expenses.getMiscellaneousExpenses());
    }

    /**
     * Sets the {@code ExpensesAmount} of the {@code EditExpensesDescriptor} that we are building.
     */
    public EditExpensesDescriptorBuilder withExpensesAmount(String expensesAmount) {
        descriptor.setExpensesAmount(new ExpensesAmount(expensesAmount));
        return this;
    }

    /**
     * Sets the {@code TravelExpenses} of the {@code EditExpensesDescriptor} that we are building.
     */
    public EditExpensesDescriptorBuilder withTravelExpenses(String travelExpenses) {
        descriptor.setTravelExpenses(new TravelExpenses(travelExpenses));
        return this;
    }

    /**
     * Sets the {@code MedicalExpenses} of the {@code EditExpensesDescriptor} that we are building.
     */
    public EditExpensesDescriptorBuilder withMedicalExpenses(String medicalExpenses) {
        descriptor.setMedicalExpenses(new MedicalExpenses(medicalExpenses));
        return this;
    }

    /**
     * Sets the {@code MiscellaneousExpenses} of the {@code EditExpensesDescriptor} that we are building.
     */
    public EditExpensesDescriptorBuilder withMiscellaneousExpenses(String miscellaneousExpenses) {
        descriptor.setMiscellaneousExpenses(new MiscellaneousExpenses(miscellaneousExpenses));
        return this;
    }

    public EditExpensesDescriptor build() {
        return descriptor;
    }
}
