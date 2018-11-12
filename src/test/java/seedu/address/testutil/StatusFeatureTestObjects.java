package seedu.address.testutil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.StockList;
import seedu.address.model.item.Item;
import seedu.address.model.item.LoanerDescription;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.SimpleItem;
import seedu.address.model.item.Status;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests, specifically
 * designed for the status feature test cases.
 */
public class StatusFeatureTestObjects {
    private static final Item ARDUINO = new Item(new Name("Arduino"), new Quantity("100"),
            new Quantity("20"), new Status(70, 20, 10), new HashSet<>());
    private static final Item Lidar = new Item(new Name("Lidar"), new Quantity("200"),
            new Quantity("80"), new Status(150, 20, 30), new HashSet<>());
    private static final Item Motor = new Item(new Name("Motor"), new Quantity("500"),
            new Quantity("100"), new Status(300, 120, 80), new HashSet<>());

    private static File userDirectory = new File(System.getProperty("user.dir"));
    private static String loanListDirectory = userDirectory.getAbsolutePath().replace("\\", "/");
    private static File loanListTestFile = new File(loanListDirectory + "/data/LoanListTest.xml");

    private StatusFeatureTestObjects() {
    } // prevents instantiation

    public static File getLoanListTestFile() {
        return loanListTestFile;
    }

    public static StockList getStockList() {
        StockList ab = new StockList();
        for (Item item : getItems()) {
            ab.addItem(item);
        }
        return ab;
    }
    public static List<Item> getItems() {
        return new ArrayList<>(Arrays.asList(ARDUINO, Lidar, Motor));
    }
    public static ArrayList<SimpleItem> getExpectedReadyItems() {
        ArrayList<SimpleItem> expectedReadyItems = new ArrayList<>();
        for (Item item : getItems()) {
            if (item.getStatus().getStatusReady() > 0) {
                expectedReadyItems.add(new SimpleItem(item.getName(),
                        new Quantity(item.getStatus().getStatusReady().toString())));
            }
        }
        return expectedReadyItems;
    }
    public static ArrayList<SimpleItem> getExpectedOnLoanItems() {
        ArrayList<SimpleItem> expectedOnLoanItems = new ArrayList<>();
        for (Item item : getItems()) {
            if (item.getStatus().getStatusOnLoan() > 0) {
                expectedOnLoanItems.add(new SimpleItem(item.getName(),
                        new Quantity(item.getStatus().getStatusOnLoan().toString())));
            }
        }
        return expectedOnLoanItems;
    }
    public static ArrayList<SimpleItem> getExpectedFaultyItems() {
        ArrayList<SimpleItem> expectedFaultyItems = new ArrayList<>();
        for (Item item : getItems()) {
            if (item.getStatus().getStatusFaulty() > 0) {
                expectedFaultyItems.add(new SimpleItem(item.getName(),
                        new Quantity(item.getStatus().getStatusFaulty().toString())));
            }
        }
        return expectedFaultyItems;
    }
    public static String getExpectedMessageOutput() {
        return "Items listed according to status\n"
                + "Ready: Arduino: 70, Lidar: 150, Motor: 300\n"
                + "On Loan: Arduino: 20, Lidar: 20, Motor: 120\n"
                + "Faulty: Arduino: 10, Lidar: 30, Motor: 80";
    }
    public static Index getTestIndex() {
        return Index.fromZeroBased(0);
    }
    public static CommandResult getExpectedCommandResult() {
        return new CommandResult(getExpectedMessageOutput());
    }
    public static LoanerDescription getLoanerDescription() {
        return new LoanerDescription(new Name("Arduino"), new Name("KinWhye"), new Quantity("10"));
    }
    public static Item getExpectedUpdatedArduinoItem() {
        return new Item(new Name("Arduino"), new Quantity("100"),
                new Quantity("20"), new Status(60, 30, 10), new HashSet<>());
    }
    public static String getExpectedViewLoanListMessageOutput() {
        return ("Loan list displayed\n"
                + "1. KinWhye: Arduino 10\n");
    }
    public static ChangeStatusCommand.ChangeStatusDescriptor getChangeStatusDescriptor() {
        return new ChangeStatusCommand.ChangeStatusDescriptor(new Name("Lidar"), 80,
                "Ready", "Faulty");
    }
    public static Index getExpectedIndex() {
        return Index.fromZeroBased(1);
    }
    public static Item getItemToUpdate() {
        return getItems().get(1);
    }
    public static Item getExpectedUpdatedItem() {
        return new Item(new Name("Lidar"), new Quantity("200"),
                new Quantity("80"), new Status(70, 20, 110), new HashSet<>());
    }
}
