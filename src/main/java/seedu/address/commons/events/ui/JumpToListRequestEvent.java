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
        this.targetIndex = new ArrayList<>(extractIndexAsIntegers(targetIndex));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    private ArrayList<Integer> extractIndexAsIntegers(ArrayList<Index> indexArrayList) {
        ArrayList<Integer> output = new ArrayList<>();
        for (Index index : indexArrayList) {
            output.add(index.getZeroBased());
        }
        return output;
    }
}
