package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Helper class for User Class
 */
public class UserParameterHelper {
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags;

    //Timetable field (Currently only one)
    private TimeTable timeTable;

    // Friend list of the person
    private Set<Friend> friendList;

    public UserParameterHelper(Name name, Phone phone, Email email, Address address, Set<Tag> tags, TimeTable timeTable,
                               Set<Friend> friendList) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
        this.timeTable = timeTable;
        this.friendList = friendList;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public Set<Friend> getFriendList() {
        return friendList;
    }
}
