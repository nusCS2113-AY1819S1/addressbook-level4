package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.distributor.DistributorProduct;

/**
 * Represents a selection change in the Distributor List Panel
 */
public class DistributorProductPanelSelectionChangedEvent extends BaseEvent {


    private final DistributorProduct newSelection;

    public DistributorProductPanelSelectionChangedEvent(DistributorProduct newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public DistributorProduct getNewSelection() {
        return newSelection;
    }
}
