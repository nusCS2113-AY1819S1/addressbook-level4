package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.recruit.model.candidate.Candidate;

/**
 * Provides a handle to a candidate card in the candidate list panel.
 */
public class CandidateCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String EDUCATION_FIELD_ID = "#education";
    private static final String SALARY_FIELD_ID = "#salary";
    private static final String JOB_FIELD_ID = "#desired_job";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label educationLabel;
    private final Label salaryLabel;
    private final Label jobLabel;
    private final List<Label> tagLabels;

    public CandidateCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        educationLabel = getChildNode(EDUCATION_FIELD_ID);
        salaryLabel = getChildNode(SALARY_FIELD_ID);
        jobLabel = getChildNode(JOB_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getEducation() {
        return educationLabel.getText();
    }

    public String getSalary() {
        return salaryLabel.getText();
    }

    public String getJob() {
        return jobLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code candidate}.
     */
    public boolean equals(Candidate candidate) {
        return getName().equals(candidate.getName().fullName)
                && getEducation().equals(candidate.getEducation().value)
                && getSalary().equals(candidate.getSalary().value)
                && getJob().equals(candidate.getJob().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(candidate.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
