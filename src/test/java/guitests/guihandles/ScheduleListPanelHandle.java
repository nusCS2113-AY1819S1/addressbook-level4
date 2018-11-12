package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.schedule.Schedule;

/**
 * Provides a handle for {@code ScheduleListPanel} containing the list of {@code ScheduleCard}.
 */
public class ScheduleListPanelHandle extends NodeHandle<ListView<Schedule>> {
    public static final String SCHEDULE_LIST_VIEW_ID = "#scheduleListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Schedule> lastRememberedSelectedScheduleCard;

    public ScheduleListPanelHandle(ListView<Schedule> scheduleListPanelNode) {
        super(scheduleListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ScheduleCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ScheduleCardHandle getHandleToSelectedCard() {
        List<Schedule> selectedScheduleList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedScheduleList.size() != 1) {
            throw new AssertionError("Schedule list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ScheduleCardHandle::new)
                .filter(handle -> handle.equals(selectedScheduleList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Schedule> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code schedule}.
     */
    public void navigateToCard(Schedule schedule) {
        if (!getRootNode().getItems().contains(schedule)) {
            throw new IllegalArgumentException("Schedule does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(schedule);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code ScheduleCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the schedule card handle of a schedule associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ScheduleCardHandle getScheduleCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ScheduleCardHandle::new)
                .filter(handle -> handle.equals(getSchedule(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Schedule getSchedule(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code ScheduleCard} in the list.
     */
    public void rememberSelectedScheduleCard() {
        List<Schedule> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedScheduleCard = Optional.empty();
        } else {
            lastRememberedSelectedScheduleCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedScheduleCard()} call.
     */
    public boolean isSelectedScheduleCardChanged() {
        List<Schedule> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedScheduleCard.isPresent();
        } else {
            return !lastRememberedSelectedScheduleCard.isPresent()
                    || !lastRememberedSelectedScheduleCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }


}
