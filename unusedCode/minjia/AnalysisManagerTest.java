package seedu.address.analysis;
    // analyseProfit
    // analyseCost
    // analyseRevenue
    // calculateTotalProfit
    // calculateTotalCost
    // calculateTotalRevenue


    @Test
    public void calculateTotalProfit() {
        Price actualProfit = analysisManager.analyseProfit(AnalysisPeriodType.MONTH);

    }

    @Test
    public void calculateTotalCost() {

    }

    @Test
    public void calculateTotalRevenue() {

    }

import static seedu.address.testutil.transaction.TypicalTransactions.getTypicalFilteredTransactions;
import static seedu.address.testutil.transaction.TypicalTransactions.getTypicalTransactionList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.drink.Price;

public class AnalysisManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AnalysisManager analysisManager = new AnalysisManager(getTypicalTransactionList(),
            getTypicalFilteredTransactions());


    // analyseProfit
    // analyseCost
    // analyseRevenue
    // calculateTotalProfit
    // calculateTotalCost
    // calculateTotalRevenue


    @Test
    public void calculateTotalProfit() {
        Price actualProfit = analysisManager.analyseProfit(AnalysisPeriodType.MONTH);

    }

    @Test
    public void calculateTotalCost() {

    }

    @Test
    public void calculateTotalRevenue() {

    }

}
