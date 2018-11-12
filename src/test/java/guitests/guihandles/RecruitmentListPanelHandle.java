package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.recruitment.Recruitment;

/**
 * Provides a handle for {@code RecruitmentListPanel} containing the list of {@code RecruitmentCard}.
 */
public class RecruitmentListPanelHandle extends NodeHandle<ListView<Recruitment>> {
    public static final String RECRUITMENT_LIST_VIEW_ID = "#recruitmentListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Recruitment> lastRememberedSelectedRecruitmentCard;

    public RecruitmentListPanelHandle(ListView<Recruitment> recruitmentListPanelNode) {
        super(recruitmentListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RecruitmentCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RecruitmentCardHandle getHandleToSelectedCard() {
        List<Recruitment> selectedRecruitmentList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedRecruitmentList.size() != 1) {
            throw new AssertionError("Recruitment list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(RecruitmentCardHandle::new)
                .filter(handle -> handle.equals(selectedRecruitmentList.get(0)))
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
        List<Recruitment> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code recruitment}.
     */
    public void navigateToCard(Recruitment recruitment) {
        if (!getRootNode().getItems().contains(recruitment)) {
            throw new IllegalArgumentException("Recruitment does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(recruitment);
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
     * Selects the {@code RecruitmentCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the recruitment card handle of a recruitment associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RecruitmentCardHandle getRecruitmentCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(RecruitmentCardHandle::new)
                .filter(handle -> handle.equals(getRecruitment(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Recruitment getRecruitment(int index) {
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
     * Remembers the selected {@code RecruitmentCard} in the list.
     */
    public void rememberSelectedRecruitmentCard() {
        List<Recruitment> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedRecruitmentCard = Optional.empty();
        } else {
            lastRememberedSelectedRecruitmentCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code RecruitmentCard} is different from the value remembered by the most recent
     * {@code rememberSelectedRecruitmentCard()} call.
     */
    public boolean isSelectedRecruitmentCardChanged() {
        List<Recruitment> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedRecruitmentCard.isPresent();
        } else {
            return !lastRememberedSelectedRecruitmentCard.isPresent()
                    || !lastRememberedSelectedRecruitmentCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }

}
