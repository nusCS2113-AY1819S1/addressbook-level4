package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.recruitment.Recruitment;

/**
 * Provides a handle to a recruitment card in the recruitment list panel.
 */
public class RecruitmentCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String POST_FIELD_ID = "#post";
    private static final String WORKEXP_FIELD_ID = "#workExp";
    private static final String JOBDESCRIPTION_FIELD_ID = "#jobDescription";

    private final Label idLabel;
    private final Label postLabel;
    private final Label workExpLabel;
    private final Label jobDescriptionLabel;

    public RecruitmentCardHandle(Node cardNode) {
        super(cardNode);
        idLabel = getChildNode(ID_FIELD_ID);
        postLabel = getChildNode(POST_FIELD_ID);
        workExpLabel = getChildNode(WORKEXP_FIELD_ID);
        jobDescriptionLabel = getChildNode(JOBDESCRIPTION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getPost () {
        return postLabel.getText();
    }

    public String getWorkExp() {
        return workExpLabel.getText();
    }

    public String getJobDescription() {
        return jobDescriptionLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code recruitment}.
     */
    public boolean equals(Recruitment recruitment) {
        return getPost().equals(recruitment.getPost().value)
                && getWorkExp().equals(recruitment.getWorkExp().workExp + " years")
                && getJobDescription().equals(recruitment.getJobDescription().value);
    }
}
