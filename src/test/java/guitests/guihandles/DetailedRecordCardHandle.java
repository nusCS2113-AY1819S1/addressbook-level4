package guitests.guihandles;

import static seedu.planner.model.record.MoneyFlow.CURRENCY;
import static seedu.planner.model.record.MoneyFlow.MONEYFLOW_NO_SIGN_REGEX;
import static seedu.planner.model.record.MoneyFlow.SIGN_REGEX;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A handle to the {@code DetailedRecordCard} in the GUI
 */
public class DetailedRecordCardHandle extends NodeHandle<Node> {
    public static final String DETAILED_CARD_ID = "#detailedCard";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#date";
    private static final String MONEYFLOW_FIELD_ID = "#moneyFlow";
    private static final String TAGS_FIELD_ID = "#biggerTags";

    private static final Pattern MONEY_PATTERN = Pattern.compile(SIGN_REGEX + "\\" + CURRENCY
            + MONEYFLOW_NO_SIGN_REGEX);

    private final Label nameLabel;
    private final Label dateLabel;
    private final Label moneyFlowLabel;
    private final Region tagsContainer;

    private String lastRememberedName;
    private String lastRememberedDate;
    private String lastRememberedMoneyFlow;
    private List<String> lastRememberedTags;

    public DetailedRecordCardHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        moneyFlowLabel = getChildNode(MONEYFLOW_FIELD_ID);

        tagsContainer = getChildNode(TAGS_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getMoneyFlow() {
        return moneyFlowLabel.getText();
    }

    /**
     * Gets numerical part from a money string. Assumes the money string has S in it.
     */
    public String getNumericalMoneyFlow(String moneyFlow) {
        Matcher matcher = MONEY_PATTERN.matcher(moneyFlow);
        if (!matcher.matches()) {
            throw new IllegalStateException();
        }
        return matcher.group("sign") + matcher.group("money");
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public List<String> getTags() {
        List<Label> tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Remembers the current state of the panel
     */
    public void rememberDetailedRecordCard() {
        lastRememberedName = getName();
        lastRememberedDate = getDate();
        lastRememberedMoneyFlow = getMoneyFlow();
        lastRememberedTags = getTags();
    }

    /**
     * Returns true if the current DetailedRecordCard is different from the last remembered DetailedRecordCard
     */
    public boolean isDetailedRecordCardChanged() {
        boolean isSame = lastRememberedName.equals(getName())
                && lastRememberedDate.equals(getDate())
                && lastRememberedMoneyFlow.equals(getMoneyFlow())
                && lastRememberedTags.equals(getTags());
        return !isSame;
    }

    public Boolean isVisible() {
        return this.getRootNode().isVisible();
    }

    /**
     * Returns true if this handle references information from {@code RecordCardHandle}.
     */
    public boolean equals(RecordCardHandle recordCardHandle) {
        return getName().equals(recordCardHandle.getName())
                && getNumericalMoneyFlow(getMoneyFlow()).equals(recordCardHandle.getMoneyFlow())
                && getDate().equals(recordCardHandle.getDate())
                && getTags().equals(recordCardHandle.getTags());
    }
}
