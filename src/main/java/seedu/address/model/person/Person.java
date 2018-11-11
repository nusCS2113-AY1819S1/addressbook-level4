package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.SortingParams.PARAM_NAME;
import static seedu.address.logic.parser.SortingParams.PARAM_SKILL;
import static seedu.address.logic.parser.SortingParams.PARAM_SKILLLEVEL;
import static seedu.address.model.person.Parameter.MESSAGE_UNKNOWN_PARAM;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private static Comparator<Person> bySkillLevel = Comparator.comparingInt(p -> p.getSkillLevel().skillLevel);

    private static Comparator<Person> byName = (p1, p2) -> {
        String name1 = p1.toString();
        String name2 = p2.toString();
        return name1.compareTo(name2);
    };

    private static Comparator<Person> bySkill = (p1, p2) -> {
        String s1 = p1.getSkill().value;
        String s2 = p2.getSkill().value;
        return s1.compareTo(s2);
    };

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Skill skill;
    private final SkillLevel skillLevel;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Skill skill,
                  SkillLevel skillLevel, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.skill = skill;
        this.skillLevel = skillLevel;
        this.tags.addAll(tags);
    }

    public static Comparator<Person> getComparator(Parameter parameter) throws IllegalArgumentException {
        switch(parameter.value) {
        case PARAM_SKILL:
            return bySkill;
        case PARAM_NAME:
            return byName;
        case PARAM_SKILLLEVEL:
            return bySkillLevel;
        default:
            throw new IllegalArgumentException(MESSAGE_UNKNOWN_PARAM);
        }
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Skill getSkill() {
        return skill;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true; //memory when you compare between 2 objects
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
