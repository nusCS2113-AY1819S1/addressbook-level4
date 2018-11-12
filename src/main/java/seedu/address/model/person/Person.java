package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final EmployeeId employeeId;
    private final Name name;
    private final DateOfBirth dateOfBirth;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Department department;
    private final Position position;
    private final Address address;
    private final Salary salary;
    private final Bonus bonus;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(EmployeeId employeeId, Name name, DateOfBirth dateOfBirth, Phone phone, Email email,
                  Department department, Position position, Address address, Salary salary, Bonus bonus,
                  Set<Tag> tags) {
        requireAllNonNull(name, dateOfBirth, phone, email, department, position, address, salary, tags);
        this.employeeId = employeeId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.position = position;
        this.address = address;
        this.salary = salary;
        this.bonus = bonus;
        this.tags.addAll(tags);
    }

    public Person(EmployeeId employeeId) {
        SampleDataUtil sample = new SampleDataUtil();
        requireAllNonNull(employeeId);
        this.employeeId = employeeId;
        this.name = new Name(sample.SAMPLE_NAME.toString());
        this.dateOfBirth = new DateOfBirth(sample.SAMPLE_DATEOFBIRTH.toString());
        this.phone = new Phone(sample.SAMPLE_PHONE.toString());
        this.email = new Email(sample.SAMPLE_EMAIL.toString());
        this.department = new Department(sample.SAMPLE_DEPARTMENT.toString());
        this.position = new Position(sample.SAMPLE_POSITION.toString());
        this.address = new Address(sample.SAMPLE_ADDRESS.toString());
        this.salary = new Salary(sample.SAMPLE_SALARY.toString());
        this.bonus = new Bonus(sample.SAMPLE_BONUS.toString());
        this.tags.addAll(new HashSet<>());
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public Name getName() {
        return name;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Department getDepartment() {
        return department;
    }

    public Position getPosition() {
        return position;
    }

    public Address getAddress() {
        return address;
    }

    public Salary getSalary() {
        return salary;
    }

    public Bonus getBonus() {
        return bonus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same phone or same email or same name and date of birth.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        AddCommand.setIsEmailDuplicated(false);
        EditCommand.setIsEmailDuplicated(false);
        AddCommand.setIsPhoneDuplicated(false);
        EditCommand.setIsPhoneDuplicated(false);

        if (otherPerson != null && otherPerson.getEmail().equals(getEmail())) {
            AddCommand.setIsEmailDuplicated(true);
            EditCommand.setIsEmailDuplicated(true);
        } else if (otherPerson != null && otherPerson.getPhone().equals(getPhone())) {
            AddCommand.setIsPhoneDuplicated(true);
            EditCommand.setIsPhoneDuplicated(true);
        }

        return otherPerson != null && ((otherPerson.getEmail().equals(getEmail()))
                || (otherPerson.getPhone().equals(getPhone()))
                || ((otherPerson.getName().equals(getName()))
                && (otherPerson.getDateOfBirth().equals(getDateOfBirth()))));
    }

    /**
     * Returns true if both persons have the same employee id.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameEmployeeId(Person person) {
        if (person.getEmployeeId() == this.getEmployeeId()) {
            return true;
        }

        return person.getEmployeeId() != null
                && person.getEmployeeId().equals(getEmployeeId());
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
        return otherPerson.getEmployeeId().equals(getEmployeeId())
                && otherPerson.getName().equals(getName())
                && otherPerson.getDateOfBirth().equals(getDateOfBirth())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getSalary().equals(getSalary())
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
        builder.append(getEmployeeId())
                .append(" Name: ")
                .append(getName())
                .append(" Date Of Birth: ")
                .append(getDateOfBirth())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Department: ")
                .append(getDepartment())
                .append(" Position: ")
                .append(getPosition())
                .append(" Address: ")
                .append(getAddress())
                .append(" Salary: ")
                .append(getSalary())
                .append(" Bonus ")
                .append(getBonus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
