package seedu.address.testutil;

import seedu.address.logic.commands.ModifyAllPayCommand.ModSalaryDescriptor;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Salary;

/**
 * A utility class to help with building ModSalaryDescriptor objects.
 */
public class ModAllSalaryDescriptorBuilder {

    private ModSalaryDescriptor descriptor;

    public ModAllSalaryDescriptorBuilder() {
        descriptor = new ModSalaryDescriptor();
    }

    public ModAllSalaryDescriptorBuilder(ModSalaryDescriptor descriptor) {
        this.descriptor = new ModSalaryDescriptor(descriptor);
    }

    /**
     * Returns an {@code ModSalaryDescriptor} with fields containing {@code person}'s details
     */
    public ModAllSalaryDescriptorBuilder(Person person) {
        descriptor = new ModSalaryDescriptor();
        descriptor.setSalary(person.getSalary());
        descriptor.setBonus(person.getBonus());
    }

    /**
     * Sets the {@code Salary} of the {@code ModSalaryDescriptor} that we are building.
     */
    public ModAllSalaryDescriptorBuilder withSalary(String salary) {
        descriptor.setSalary(new Salary(salary));
        return this;
    }

    /**
     * Sets the {@code Bonus} of the {@code ModSalaryDescriptor} that we are building.
     */
    public ModAllSalaryDescriptorBuilder withBonus(String bonus) {
        descriptor.setBonus(new Bonus(bonus));
        return this;
    }

    public ModSalaryDescriptor build() {
        return descriptor;
    }
}
