package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String NRIC_FIELD_ID = "#nric";
    private static final String MEDICAL_REPORTS_FIELD_ID = "#reports";
    private static final String MEDHISTORY_FIELD_ID = "#medHistories";
    private static final String APPTS_FIELD_ID = "#appts";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label nricLabel;
    private final List<Label> medicalReportLabels;
    private final List<Label> medHistoryLabel;
    private final List<Label> apptLabels;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        nricLabel = getChildNode(NRIC_FIELD_ID);

        Region reportsContainer = getChildNode(MEDICAL_REPORTS_FIELD_ID);
        medicalReportLabels = reportsContainer.getChildrenUnmodifiable().stream()
                .map(Label.class::cast).collect(Collectors.toList());

        Region medHistoriesContainer = getChildNode(MEDHISTORY_FIELD_ID);
        medHistoryLabel = medHistoriesContainer.getChildrenUnmodifiable().stream()
                .map(Label.class::cast).collect(Collectors.toList());

        Region apptsContainer = getChildNode(APPTS_FIELD_ID);
        apptLabels = apptsContainer.getChildrenUnmodifiable().stream()
                .map(Label.class::cast).collect(Collectors.toList());

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer.getChildrenUnmodifiable().stream()
                .map(Label.class::cast).collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getNric() {
        return nricLabel.getText();
    }

    public List<String> getMedicalReports() {
        return medicalReportLabels.stream().map(Label::getText).collect(Collectors.toList());
    }

    public List<String> getMedHistory() {
        return medHistoryLabel.stream().map(Label::getText).collect(Collectors.toList());
    }

    public List<String> getAppts() {
        return apptLabels.stream().map(Label::getText).collect(Collectors.toList());
    }

    public List<String> getTags() {
        return tagLabels.stream().map(Label::getText).collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Person person) {
        return getName().equals(person.getName().fullName)
                && getAddress().equals(person.getAddress().value)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && ImmutableMultiset.copyOf(getMedicalReports())
                .equals(ImmutableMultiset.copyOf(person.getMedicalReports().stream()
                        .map(report -> report.toString())
                        .collect(Collectors.toList())))
                && ImmutableMultiset.copyOf(getMedHistory())
                .equals(ImmutableMultiset.copyOf(person.getMedHistory().stream()
                        .map(medHistory -> medHistory.toString()).collect(Collectors.toList())))
                && ImmutableMultiset.copyOf(getAppts()).equals(ImmutableMultiset.copyOf(person.getAppts().stream()
                .map(appt -> appt.toString())
                .collect(Collectors.toList())))
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(person.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
