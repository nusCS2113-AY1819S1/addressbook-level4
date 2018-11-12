# ChewKinWhye
\java\seedu\address\logic\commands\ChangeStatusCommandTest.java
``` java

public class ChangeStatusCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private List<Item> lastShownList = model.getFilteredItemList();
    private ChangeStatusCommand changeStatusCommand = new ChangeStatusCommand(getChangeStatusDescriptor());

    @Test
    public void constructorNullChangeStatusDescriptorThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeStatusCommand(null);
    }
    @Test
    public void changeStatusDescriptorConstructor() {
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptor =
                new ChangeStatusCommand.ChangeStatusDescriptor(getChangeStatusDescriptor());
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptorDuplicate =
                new ChangeStatusCommand.ChangeStatusDescriptor(changeStatusDescriptor);
        assertEquals(changeStatusDescriptor, changeStatusDescriptorDuplicate);
    }
    @Test
    public void changeStatusCommandGetIndexTest() {
        try {
            Index actualIndex = changeStatusCommand.getIndex(lastShownList, getChangeStatusDescriptor());
            assertEquals(actualIndex, getExpectedIndex());
        } catch (CommandException e) {
            throw new AssertionError("The item is present in the stock list.");
        }
    }
    @Test
    public void createUpdatedItemTest() {
        try {
            Item actualUpdatedItem = changeStatusCommand
                    .createUpdatedItem(getItemToUpdate(), getChangeStatusDescriptor());
            assertEquals(actualUpdatedItem, getExpectedUpdatedItem());
        } catch (Exception e) {
            throw new AssertionError("The updated item does not have invalid status");
        }
    }
    @Test
    public void changeStatusCommandExecuteTestWithoutLogin() {
        assertCommandFailure(changeStatusCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    @Test
    public void changeStatusCommandExecuteTestWithLogin() {
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        try {
            changeStatusCommand.execute(model, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("The execute function is valid");
        }
        assertCommandFailure(changeStatusCommand, model, commandHistory, MESSAGE_STATUS_CONSTRAINTS);
    }

}
```
\java\seedu\address\logic\commands\LoanListCommandsTest.java
``` java

public class LoanListCommandsTest {
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private CommandHistory commandHistory = new CommandHistory();
    private LoanListCommand loanListCommand = new LoanListCommand(getLoanerDescription());
    private ViewLoanListCommand viewLoanListCommand = new ViewLoanListCommand();
    private DeleteLoanListCommand deleteLoanListCommand = new DeleteLoanListCommand(getTestIndex());
    @Test
    public void executeViewLoanListCommandWithoutLogin() {
        assertCommandFailure(viewLoanListCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    @Test
    public void executeDeleteLoanListCommandWithoutLogin() {
        assertCommandFailure(deleteLoanListCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    @Test
    public void executeLoanListCommandWithoutLogin() {
        assertCommandFailure(loanListCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    /*@Test
    public void executeLoanListAndViewLoanListAndDeleteLoanList() {
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        try {
            loanListCommand.updateLoanList(getLoanListTestFile(), getLoanerDescription());
        } catch (JAXBException e) {
            throw new AssertionError("The loan list test file is vaild");
        }

        try {
            loanListCommand.updateStatus(model, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("The updated status is valid");
        }
        Item actualUpdatedArduinoItem = model.getFilteredItemList().get(0);
        assertEquals(actualUpdatedArduinoItem, getExpectedUpdatedArduinoItem());

        try {
            String actualViewLoanListMessageOutput = viewLoanListCommand.getMessageOutput(getLoanListTestFile());
            assertEquals(actualViewLoanListMessageOutput, getExpectedViewLoanListMessageOutput());
        } catch (CommandException e) {
            throw new AssertionError("Loan list file is not empty");
        }

        try {
            deleteLoanListCommand.deleteLoanList(model, commandHistory, getLoanListTestFile());
        } catch (Exception e) {
            throw new AssertionError("The loan list entry should be deleted");
        }

        try {
            viewLoanListCommand.getMessageOutput(getLoanListTestFile());
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_EMPTY);
        }
    }*/
}
```
\java\seedu\address\logic\commands\StatusCommandTest.java
``` java

public class StatusCommandTest {
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private CommandHistory commandHistory = new CommandHistory();
    private List<Item> lastShownList = model.getFilteredItemList();
    private StatusCommand statusCommand = new StatusCommand();
    private ArrayList<SimpleItem> actualReadyItems = new ArrayList<>();
    private ArrayList<SimpleItem> actualOnLoanItems = new ArrayList<>();
    private ArrayList<SimpleItem> actualFaultyItems = new ArrayList<>();

    @Test
    public void checkSortSimpleItems() {
        statusCommand.sortSimpleItems(lastShownList, actualReadyItems, actualOnLoanItems, actualFaultyItems);
        assertEquals(actualReadyItems, getExpectedReadyItems());
        assertEquals(actualOnLoanItems, getExpectedOnLoanItems());
        assertEquals(actualFaultyItems, getExpectedFaultyItems());
    }
    @Test
    public void checkGetMessageOutput() {
        statusCommand.sortSimpleItems(lastShownList, actualReadyItems, actualOnLoanItems, actualFaultyItems);
        String actualMessageOutput = statusCommand.getMessageOutput(actualReadyItems,
                actualOnLoanItems, actualFaultyItems);
        assertEquals(actualMessageOutput, getExpectedMessageOutput());
    }
    @Test
    public void executeStatusCommandWithoutLogin() {
        assertCommandFailure(new StatusCommand(), model, commandHistory, MESSAGE_LOGIN);
    }

    @Test
    public void executeStatusCommandWithLogin() {
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        try {
            CommandResult actualCommandResult = statusCommand.execute(model, commandHistory);
            assertEquals(actualCommandResult, getExpectedCommandResult());
        } catch (CommandException e) {
            throw new AssertionError("The account should be logged in.");
        }
    }
}
```
\java\seedu\address\testutil\StatusFeatureTestObjects.java
``` java

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
```
