package seedu.address.model.drink;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class DrinkBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;

    private Set<Tag> tags;

    public DrinkBuilder() {
        name = new Name(DEFAULT_NAME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code drinkToCopy}.
     */
    public DrinkBuilder(Drink drinkToCopy) {
        name = drinkToCopy.getName();
        tags = new HashSet<>(drinkToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public DrinkBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags);
    }
}
