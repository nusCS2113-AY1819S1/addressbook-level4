package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Address;
import seedu.address.model.event.Contact;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Venue;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Halloween Party";
    public static final String DEFAULT_CONTACT = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Contact contact;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<Attendee> attendees;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        contact = new Contact(DEFAULT_CONTACT);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        attendees = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        contact = eventToCopy.getContact();
        phone = eventToCopy.getPhone();
        email = eventToCopy.getEmail();
        address = eventToCopy.getAddress();
        tags = new HashSet<>(eventToCopy.getTags());
        attendees = new HashSet<>(eventToCopy.getAttendees());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code attendees} into a {@code Set<attendee>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withAttendees(String ... attendees) {
        this.attendees = SampleDataUtil.getAttendeeSet(attendees);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Event} that we are building.
     */
    public EventBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Event} that we are building.
     */
    public EventBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code Event} that we are building.
     */
    public EventBuilder withContact(String contact) {
        this.contact = new Contact(contact);
        return this;
    }

    public Event build() {
        return new Event(name, contact, phone, email, venue, tags, attendees);
    }

}
