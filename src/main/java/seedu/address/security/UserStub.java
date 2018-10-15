package seedu.address.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeTable;
import seedu.address.model.tag.Tag;

/**
 * A stub to test for user and the concept of "me"
 */
public class UserStub {

    public static Person getUser() {
        HashSet<Friend> friendSetStub = new HashSet<>();
        Friend friend1 = new Friend(new Name("Julian Tan"));
        Friend friend2 = new Friend(new Name("Chun Teck"));
        Friend friend3 = new Friend(new Name("Julian Lim"));
        friendSetStub.addAll(Arrays.asList(friend1, friend2, friend3));

        Person personStub = new Person(new Name("Charlotte Oliveiro"),
                new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new TimeTable(), friendSetStub);
        return personStub;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
