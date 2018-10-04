package com.t13g2.forum.commons.events.model;

import com.t13g2.forum.commons.events.BaseEvent;
import com.t13g2.forum.model.ReadOnlyForumBook;

/** Indicates the ForumBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyForumBook data;

    public AddressBookChangedEvent(ReadOnlyForumBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
