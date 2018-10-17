package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.distributor.Distributor;

/**
 * Represents a selection change in the Product List Panel
 */
public class DistributorPanelSelectionChangedEvent extends BaseEvent {


    private final Distributor newSelection;

    public DistributorPanelSelectionChangedEvent(Distributor newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Distributor getNewSelection() {
        return newSelection;
    }
}
