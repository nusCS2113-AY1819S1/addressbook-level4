package com.t13g2.forum.commons.events.ui;

import com.t13g2.forum.commons.events.BaseEvent;
import com.t13g2.forum.model.forum.Module;

/**
 * Represents a selection change in the Module List Panel
 */
public class ModulePanelSelectionChangedEvent extends BaseEvent {


    private final Module newSelection;

    public ModulePanelSelectionChangedEvent(Module newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Module getNewSelection() {
        return newSelection;
    }
}

