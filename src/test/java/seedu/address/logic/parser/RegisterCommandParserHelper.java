package seedu.address.logic.parser;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.SendsAuthenticationStateEvent;

/**
 * Represents a Parser Class with EventsCenter
 */
public class RegisterCommandParserHelper {
    protected EventsCenter eventsCenter;

    /**
     * Uses default {@link EventsCenter}
     */
    public RegisterCommandParserHelper() {
        this(EventsCenter.getInstance());
    }

    public RegisterCommandParserHelper(EventsCenter eventsCenter) {
        this.eventsCenter = eventsCenter;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event) {
        eventsCenter.post(event);
    }

    //Returns true when testing for commands parsing
    @Subscribe
    public void handleGetAuthenticationEvent(GetAuthenticationEvent e) {
        raise(new SendsAuthenticationStateEvent(false));
    }
}
