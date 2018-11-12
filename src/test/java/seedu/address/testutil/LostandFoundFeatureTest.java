package seedu.address.testutil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FoundCommand;
import seedu.address.logic.commands.LostCommand;
import seedu.address.model.StockList;
import seedu.address.model.item.Item;
import seedu.address.model.item.Loststatus;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.SimpleItem;
import seedu.address.model.item.Status;

//@@author HeHaowei

/**
 * A utility class containing a list of {@code Item} objects to be used in tests, specifically
 * designed for the status feature test cases.
 */
public class LostandFoundFeatureTest {
    private static final Item ARDUINO = new Item(new Name("Arduino"), new Quantity("100"),
            new Quantity("20"), new Loststatus(80,100), new Status(70, 20, 10), new HashSet<>());
    private static final Item Lidar = new Item(new Name("Lidar"), new Quantity("200"),
            new Quantity("80"), new Loststatus(50,200), new Status(150, 20, 30), new HashSet<>());
    private static final Item Motor = new Item(new Name("Motor"), new Quantity("500"),
            new Quantity("100"), new Loststatus(80,500), new Status(300, 120, 80), new HashSet<>());

    private static File userDirectory = new File(System.getProperty("user.dir"));
    private static String lostandfoundDirectory = userDirectory.getAbsolutePath().replace("\\", "/");
    private static File lostandfoundTestFile = new File(lostandfoundDirectory + "/data/lostandfoundTest.xml");

    private LostandFoundFeatureTest() {
    } // prevents instantiation

    public static File getLostandfoundTestFileTestFile() {
        return lostandfoundTestFile;
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
    public static ArrayList<SimpleItem> getExpectedLostItems() {
        ArrayList<SimpleItem> expectedLostItems = new ArrayList<>();
        for (Item item : getItems()) {
            if (item.getLoststatus().getLoststatusLost() > 0) {
                expectedLostItems.add(new SimpleItem(item.getName(),
                        new Quantity(item.getLoststatus().getLoststatusLost().toString())));
            }
        }
        return expectedLostItems;
    }
    public static ArrayList<SimpleItem> getExpectedFoundItems() {
        ArrayList<SimpleItem> expectedFoundItems = new ArrayList<>();
        for (Item item : getItems()) {
            if (item.getLoststatus().getLoststatusFound() > 0) {
                expectedFoundItems.add(new SimpleItem(item.getName(),
                        new Quantity(item.getLoststatus().getLoststatusFound().toString())));
            }
        }
        return expectedFoundItems;
    }

    public static String getExpectedMessageOutput() {
        return ("Lost items listed\n"
                + "Lost: 1.Arduino:80\n"
                + "2.Lidar:50\n"
                + "3.Motor:80");

    }
    public static Index getTestIndex() {
        return Index.fromZeroBased(0);
    }
    public static CommandResult getExpectedCommandResult() {
        return new CommandResult(getExpectedMessageOutput());
    }

    public static LostCommand.LostDescriptor getLostDescriptor() {
        return new LostCommand.LostDescriptor(20);
    }
    public static FoundCommand.FoundDescriptor getFoundDescriptor(){
        return new FoundCommand.FoundDescriptor(15);
    }

    public static Index getExpectedIndexone() {
        return Index.fromZeroBased(1);
    }
    public static Item getItemoneToUpdate() {
        return getItems().get(1);
    }
    public static Item getExpectedUpdatedItemone() {
        return new Item(new Name("Lidar"), new Quantity("180"),
                new Quantity("80"), new Loststatus(70,110), new Status(130, 20, 30), new HashSet<>());
    }

    public static Index getExpectedIndextwo() {
        return Index.fromZeroBased(2);
    }
    public static Item getItemtwoToUpdate() {
        return getItems().get(2);
    }
    public static Item getExpectedUpdatedItemtwo() {
        return new Item(new Name("Motor"), new Quantity("515"),
                new Quantity("100"), new Loststatus(65,515), new Status(315, 120, 80), new HashSet<>());
    }


    public static String getExpectedLostListone() {
        return ("Lost items listed\n"
                + "Lost: 1.Arduino:80\n"
                + "2.Lidar:70\n"
                + "3.Motor:80");
    }

    public static String getExpectedLostListtwo() {
        return ("Lost items listed\n"
                + "Lost: 1.Arduino:80\n"
                + "2.Lidar:70\n"
                + "3.Motor:65");
    }


}
