//@@author XiaoYunhan
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * Provides a handle to a expenditure card in the expenditure list panel.
 */
public class ExpenditureCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String CATEGORY_FIELD_ID = "#category";
    private static final String DATE_FIELD_ID = "#date";
    private static final String MONEY_FIELD_ID = "#money";

    private final Label idLabel;
    private final Label descriptionLabel;
    private final Label categoryLabel;
    private final Label dateLabel;
    private final Label moneyLabel;

    public ExpenditureCardHandle(Node cardNode) {

        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        categoryLabel = getChildNode(CATEGORY_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        moneyLabel = getChildNode(MONEY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getCategory() {
        return categoryLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getMoney() {
        return moneyLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code expenditure}
     */
    public boolean equals(Expenditure expenditure) {
        return getDescription().equals(expenditure.getDescription().toString())
                && getDate().equals(expenditure.getDate().toString())
                && getCategory().equals(expenditure.getCategory().toString())
                && getMoney().equals(expenditure.getMoney().toString());
    }
}
