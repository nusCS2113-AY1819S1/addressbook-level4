package seedu.address.security;

import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserStub {

    public static Person getUser() {
        HashSet<Friend> friendSetStub = new HashSet<>();
        Friend friend1 = new Friend(new Name("Julian Tan"));
        Friend friend2 = new Friend(new Name("Chun Teck"));
        Friend friend3 = new Friend(new Name("Julian Lim"));

        friendSetStub.addAll( Arrays.asList( friend1, friend2, friend3));

        Person personStub = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
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