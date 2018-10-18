package seedu.address.testutil;

import seedu.address.logic.commands.AddInfoCommand.AddInfoPersonDescriptor;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * A unity class to help with building AddInfoPersonDescriptor objects.
 */
public class AddInfoPersonDescriptorBuilder {
    private AddInfoPersonDescriptor descriptor;

    public AddInfoPersonDescriptorBuilder() {
        descriptor = new AddInfoPersonDescriptor();
    }

    public AddInfoPersonDescriptorBuilder(AddInfoPersonDescriptor descriptor) {
        this.descriptor = new AddInfoPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code AddInfoPersonDescriptor} with fields containing {@code person}'s details
     */
    public AddInfoPersonDescriptorBuilder(Person person) {
        descriptor = new AddInfoPersonDescriptor();
        descriptor.setDateOfBirth(person.getDateOfBirth());
        descriptor.setNric(person.getNric());
    }

    /**
     * Sets the {@code dateOfBirth} of the {@code AddInfoPersonDescriptor} that we are building.
     */
    public AddInfoPersonDescriptorBuilder withDateOfBirth(String dateOfBirth) {
        descriptor.setDateOfBirth(new DateOfBirth(dateOfBirth));
        return this;
    }

    /**
     * Sets the {@code nric} of the {@code AddInfoPersonDescriptor} that we are building.
     */
    public AddInfoPersonDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new Nric(nric));
        return this;
    }

    public AddInfoPersonDescriptor build() {
        return descriptor;
    }
}
