package seedu.recruit.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Left side of the Panel containing the list of company.
 * Upon selection, right side of the panel shows the
 * list of job offers offered in that selected company.
 */
public class ShortlistPanel extends UiPart<Region> {
    private static final String FXML = "ShortlistPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ShortlistPanel.class);

    private CandidateDetailsPanel candidateDetailsPanel;
    private CompanyJobDetailsPanel companyJobDetailsPanel;

    @FXML
    private StackPane leftPlaceholder;

    @FXML
    private  StackPane rightPlaceholder;

    public ShortlistPanel(ObservableList<Candidate> candidateList, ObservableList<Company> companyList,
                          ObservableList<JobOffer> companyJobList) {
        super(FXML);
        candidateDetailsPanel = new CandidateDetailsPanel(candidateList);
        companyJobDetailsPanel = new CompanyJobDetailsPanel(companyList, companyJobList);
        leftPlaceholder.getChildren().add(candidateDetailsPanel.getRoot());
        rightPlaceholder.getChildren().add(companyJobDetailsPanel.getRoot());
        registerAsAnEventHandler(this);
    }

}
