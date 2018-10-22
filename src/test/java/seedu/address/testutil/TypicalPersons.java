package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_HAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_TED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventManager;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Event ALICE = new PersonBuilder().withName("Inter Neighbourhood Cooking Competition")
            .withContact("Alice Pauline")
            .withVenue("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withAttendees("Peter Parker").build();
    public static final Event BENSON = new PersonBuilder().withName("Dancing Tryouts")
            .withContact("Benson Meier")
            .withVenue("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withAttendees("Mary Kate").build();
    public static final Event CARL = new PersonBuilder().withName("Frisbee Competition").withContact("Carl Kurz")
            .withPhone("95352563").withEmail("heinz@example.com").withVenue("wall street").build();
    public static final Event DANIEL = new PersonBuilder().withName("Art and Crafts").withContact("Daniel Meier")
            .withPhone("87652533").withEmail("cornelia@example.com").withVenue("10th street").withTags("friends")
            .withAttendees("Scarlet Witch").build();
    public static final Event ELLE = new PersonBuilder().withName("Music Tryouts").withContact("Elle Meyer")
            .withPhone("9482224").withEmail("werner@example.com").withVenue("michegan ave").build();
    public static final Event FIONA = new PersonBuilder().withName("Dining In The Dark").withContact("Fiona Kunz")
            .withPhone("9482427").withEmail("lydia@example.com").withVenue("little tokyo").build();
    public static final Event GEORGE = new PersonBuilder().withName("College Games").withContact("George Best")
            .withPhone("9482442").withEmail("anna@example.com").withVenue("4th street").build();

    // Manually added
    public static final Event HOON = new PersonBuilder().withName("Eating Competition").withContact("Hoon Meier")
            .withPhone("8482424").withEmail("stefan@example.com").withVenue("little india").build();
    public static final Event IDA = new PersonBuilder().withName("Yoga Session").withContact("Ida Mueller")
            .withPhone("8482131").withEmail("hans@example.com").withVenue("chicago ave").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event AMY = new PersonBuilder().withName(VALID_NAME_AMY).withContact(VALID_CONTACT_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withVenue(VALID_VENUE_AMY)
            .withTags(VALID_TAG_FRIEND).withAttendees(VALID_ATTENDEE_TED).build();
    public static final Event BOB = new PersonBuilder().withName(VALID_NAME_BOB).withContact(VALID_CONTACT_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withVenue(VALID_VENUE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withAttendees(VALID_ATTENDEE_TED, VALID_ATTENDEE_HAN)
            .build();

    public static final String KEYWORD_MATCHING_TRYOUTS = "Tryouts"; // A keyword that matches Tryouts

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code EventManager} with all the typical persons.
     */
    public static EventManager getTypicalAddressBook() {
        EventManager ab = new EventManager();
        for (Event event : getTypicalPersons()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
