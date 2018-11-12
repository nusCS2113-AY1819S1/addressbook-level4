package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.expenses.Expenses;

/**
 * Provides a handle to a expenses card in the expenses list panel.
 */
public class ExpensesCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String EMPLOYEEID_FIELD_ID = "#employeeId";
    private static final String EXPENSESAMOUNT_ID = "#expensesAmount";
    private static final String TRAVELEXPENSES_ID = "#travelExpenses";
    private static final String MEDICALEXPENSES_ID = "#medicalExpenses";
    private static final String MISCELLANEOUSEXPENSES_ID = "#miscellaneousExpenses";

    private final Label idLabel;
    private final Label employeeIdLabel;
    private final Label expensesAmountLabel;
    private final Label travelExpensesLabel;
    private final Label medicalExpensesLabel;
    private final Label miscellaneousExpensesLabel;

    public ExpensesCardHandle(Node cardNode) {
        super(cardNode);
        idLabel = getChildNode(ID_FIELD_ID);
        employeeIdLabel = getChildNode(EMPLOYEEID_FIELD_ID);
        expensesAmountLabel = getChildNode(EXPENSESAMOUNT_ID);
        travelExpensesLabel = getChildNode(TRAVELEXPENSES_ID);
        medicalExpensesLabel = getChildNode(MEDICALEXPENSES_ID);
        miscellaneousExpensesLabel = getChildNode(MISCELLANEOUSEXPENSES_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEmployeeId() {
        return employeeIdLabel.getText();
    }

    public String getExpensesamount() {
        return expensesAmountLabel.getText();
    }

    public String getTravelexpenses() {
        return travelExpensesLabel.getText();
    }

    public String getMedicalexpenses() {
        return medicalExpensesLabel.getText();
    }

    public String getMiscellaneousexpenses() {
        return miscellaneousExpensesLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code schedule}.
     */
    public boolean equals(Expenses expenses) {
        return getEmployeeId().equals(expenses.getEmployeeId().value)
                && getExpensesamount().equals(expenses.getExpensesAmount().expensesAmount)
                && getTravelexpenses().equals(expenses.getTravelExpenses().travelExpenses)
                && getMedicalexpenses().equals(expenses.getMedicalExpenses().medicalExpenses)
                && getMiscellaneousexpenses().equals(expenses.getMiscellaneousExpenses().miscellaneousExpenses);
    }

}
