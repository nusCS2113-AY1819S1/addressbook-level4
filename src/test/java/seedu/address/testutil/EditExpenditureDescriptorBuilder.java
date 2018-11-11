package seedu.address.testutil;

import seedu.address.logic.commands.EditExpenditureCommand.EditExpenditureDescriptor;
import seedu.address.model.expenditureinfo.Category;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Description;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.expenditureinfo.Money;

/**
 * A utility class to help with building EditExpenditureDescriptor objects.
 */
public class EditExpenditureDescriptorBuilder {
    private EditExpenditureDescriptor descriptor;

    public EditExpenditureDescriptorBuilder() {
        descriptor = new EditExpenditureDescriptor();
    }

    public EditExpenditureDescriptorBuilder(EditExpenditureDescriptor descriptor) {
        this.descriptor = new EditExpenditureDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenditureDescriptor} with fields containing {@code task}'s details
     */
    public EditExpenditureDescriptorBuilder(Expenditure expenditure) {
        descriptor = new EditExpenditureDescriptor();
        descriptor.setCategory(expenditure.getCategory());
        descriptor.setDescription(expenditure.getDescription());
        descriptor.setDate(expenditure.getDate());
        descriptor.setMoney(expenditure.getMoney());
    }

    /**
     * Sets the {@code ExpenditureCategory} of the {@code EditExpenditureDescriptor} that we are building.
     */
    public EditExpenditureDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
        return this;
    }

    /**
     * Sets the {@code ExpenditureDate} of the {@code EditExpenditureDescriptor} that we are building.
     */
    public EditExpenditureDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code ExpenditureDescription} of the {@code EditExpenditureDescriptor} that we are building.
     */
    public EditExpenditureDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code ExpenditureMoney} of the {@code EditExpenditureDescriptor} that we are building.
     */
    public EditExpenditureDescriptorBuilder withMoney(String money) {
        descriptor.setMoney(new Money(money));
        return this;
    }

    public EditExpenditureDescriptor build() {
        return descriptor;
    }
}
