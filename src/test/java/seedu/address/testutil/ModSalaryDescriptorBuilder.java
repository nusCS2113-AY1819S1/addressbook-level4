package seedu.address.testutil;

import seedu.address.logic.commands.ModifyPayCommand.ModSalaryDescriptor;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Salary;

/**
 * A utility class to help with building ModSalaryDescriptor objects.
 */
public class ModSalaryDescriptorBuilder {

    private ModSalaryDescriptor descriptor;

    public ModSalaryDescriptorBuilder() {
        descriptor = new ModSalaryDescriptor();
    }

    public ModSalaryDescriptorBuilder(ModSalaryDescriptor descriptor) {
        this.descriptor = new ModSalaryDescriptor(descriptor);
    }

    /**
     * Returns an {@code ModSalaryDescriptor} with fields containing {@code person}'s details
     */
    public ModSalaryDescriptorBuilder(Person person) {
        descriptor = new ModSalaryDescriptor();
        descriptor.setSalary(person.getSalary());
        descriptor.setBonus(person.getBonus());
    }

    /**
     * Sets the {@code Salary} of the {@code ModSalaryDescriptor} that we are building.
     */
    public ModSalaryDescriptorBuilder withSalary(String salary) {
        descriptor.setSalary(new Salary(salary));
        return this;
    }

    /**
     * Sets the {@code Bonus} of the {@code ModSalaryDescriptor} that we are building.
     */
    public ModSalaryDescriptorBuilder withBonus(String bonus) {
        descriptor.setBonus(new Bonus(bonus));
        return this;
    }

    public ModSalaryDescriptor build() {
        return descriptor;
    }
}
