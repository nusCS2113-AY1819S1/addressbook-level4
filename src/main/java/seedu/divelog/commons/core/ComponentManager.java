package seedu.divelog.commons.core;

import seedu.divelog.commons.events.BaseEvent;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventsCenter
 */
public abstract class ComponentManager {
    protected EventsCenter eventsCenter;
    protected ApplicationState applicationState;

    /**
     * Uses default {@link EventsCenter}
     */
    public ComponentManager() {
        this(EventsCenter.getInstance(), ApplicationState.getInstance());
    }

    public ComponentManager(EventsCenter eventsCenter, ApplicationState applicationState) {
        this.eventsCenter = eventsCenter;
        this.applicationState = applicationState;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event) {
        eventsCenter.post(event);
    }
}
