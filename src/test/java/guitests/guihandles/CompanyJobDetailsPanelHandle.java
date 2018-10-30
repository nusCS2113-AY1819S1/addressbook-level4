package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.recruit.model.company.Company;

/**
 * Provides a handle for {@code CompanyJobDetailsPanel} containing the list of {@code CompanyCard }
 * and { @code JobCard }.
 */
public class CompanyJobDetailsPanelHandle extends NodeHandle<ListView<Company>> {
    public static final String COMPANY_DETAILS_VIEW_ID = "#companyView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Company> lastRememberedSelectedPersonCard;

    public CompanyJobDetailsPanelHandle(ListView<Company> companyDetailsNode) {
        super(companyDetailsNode);
    }

    /**
     * Returns a handle to the selected {@code CompanyCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CompanyCardHandle getHandleToSelectedCard() {
        List<Company> selectedCompanyList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCompanyList.size() != 1) {
            throw new AssertionError("Company list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(CompanyCardHandle::new)
                .filter(handle -> handle.equals(selectedCompanyList.get(0)))
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
        List<Company> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code candidate}.
     */
    public void navigateToCard(Company company) {
        if (!getRootNode().getItems().contains(company)) {
            throw new IllegalArgumentException("Candidate does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(company);
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
     * Selects the {@code CandidateCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the candidate card handle of a candidate associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CompanyCardHandle getCompanyCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(CompanyCardHandle::new)
                .filter(handle -> handle.equals(getCompany(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Company getCompany(int index) {
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
     * Remembers the selected {@code CompanyCard} in the list.
     */
    public void rememberSelectedCompanyCard() {
        List<Company> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CompanyCard} is different from the value remembered by the most recent
     * {@code rememberSelectedCompanyCard()} call.
     */
    public boolean isSelectedCompanyCardChanged() {
        List<Company> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
