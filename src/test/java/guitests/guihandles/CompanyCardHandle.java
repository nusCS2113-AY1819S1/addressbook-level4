package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.recruit.model.company.Company;

/**
 * Provides a handle to a company card in the company list panel.
 */
public class CompanyCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_ID = "#phone";
    private static final String EMAIL_ID = "#email";
    private static final String ADDRESS_ID = "#address";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label addressLabel;

    public CompanyCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_ID);
        emailLabel = getChildNode(EMAIL_ID);
        addressLabel = getChildNode(ADDRESS_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhoneId() {
        return phoneLabel.getText();
    }

    public String getEmailId() {
        return emailLabel.getText();
    }

    public String getAddressId() {
        return addressLabel.getText();
    }


    /**
     * Returns true if this handle contains {@code company}.
     */
    public boolean equals(Company company) {
        return getName().equals(company.getCompanyName().value)
                && getPhoneId().equals(company.getPhone().value)
                && getEmailId().equals(company.getEmail().value)
                && getAddressId().equals(company.getAddress().value);
    }
}
