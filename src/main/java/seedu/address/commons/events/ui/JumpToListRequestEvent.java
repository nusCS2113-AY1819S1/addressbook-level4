package seedu.address.commons.events.ui;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final ArrayList<Integer> targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = new ArrayList<>(targetIndex.getZeroBased());
    }

    public JumpToListRequestEvent(ArrayList<Index> targetIndex) {
        this.targetIndex = new ArrayList<>();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    private ArrayList<Integer> extractIndexAsIntergers(ArrayList<Index> indexArrayList) {

        for (Index index : indexArrayList) {

        }
        return new ArrayList<>();
    }
}
