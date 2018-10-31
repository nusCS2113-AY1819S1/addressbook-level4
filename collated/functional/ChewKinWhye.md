# ChewKinWhye
###### \java\seedu\address\logic\commands\ChangeStatusCommand.java
``` java

/**
 * Updates the status of an existing item in the stock list.
 */

public class ChangeStatusCommand extends Command {
    public static final String COMMAND_WORD = "changeStatus";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the status of the item identified "
            + "by the name of the item. "
            + "Parameters: "
            + PREFIX_NAME + "NAME  "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_ORIGINAL_STATUS + "ORIGINAL STATUS "
            + PREFIX_NEW_STATUS + "NEW STATUS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUANTITY + "5 "
            + PREFIX_ORIGINAL_STATUS + "Ready "
            + PREFIX_NEW_STATUS + "Faulty";
    public static final String MESSAGE_CHANGE_STATUS_SUCCESS = "Changed Status: %1$s";
    public static final String MESSAGE_INVALID_STATUS_FIELD = "The status description is invalid";
    public static final String MESSAGE_INVALID_NAME_FIELD = "The item does not exist";
    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "The updated value of each status field has to be positive";

    private Index index;
    private final ChangeStatusDescriptor changeStatusDescriptor;
    public ChangeStatusCommand(ChangeStatusDescriptor changeStatusDescriptor) {
        requireNonNull(changeStatusDescriptor);
        this.changeStatusDescriptor = new ChangeStatusDescriptor(changeStatusDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        index = getIndex(lastShownList, changeStatusDescriptor);

        Item itemToUpdate = lastShownList.get(index.getZeroBased());

        Item updatedItem = createUpdatedItem(itemToUpdate, changeStatusDescriptor);

        model.updateItem(itemToUpdate, updatedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_CHANGE_STATUS_SUCCESS, updatedItem));


    }

    private static Index getIndex(List<Item> lastShownList, ChangeStatusDescriptor changeStatusDescriptor)
            throws CommandException {
        Index index;
        int counter = 0;
        for (Item item:lastShownList) {
            if (item.getName().equals(changeStatusDescriptor.getName())) {
                index = Index.fromZeroBased(counter);
                return index;
            }
            counter++;
        }
        throw new CommandException(MESSAGE_INVALID_NAME_FIELD);
    }
    /**
     * Creates and returns a {@code Item} with the details of {@code itemToUpdate}
     * edited with {@code changeStatusDescriptor}.
     */
    private static Item createUpdatedItem(Item itemToUpdate,
                                          ChangeStatusDescriptor changeStatusDescriptor) throws CommandException {
        assert itemToUpdate != null;
        Status currentStatus = itemToUpdate.getStatus();
        Status updatedStatus;
        Integer updatedReady = currentStatus.getStatusReady();
        Integer updatedOnLoan = currentStatus.getStatusOnLoan();
        Integer updatedFaulty = currentStatus.getStatusFaulty();

        Integer changeStatusValue = changeStatusDescriptor.getQuantity();
        switch (changeStatusDescriptor.getInitialStatus()) {
        case "Ready":
            updatedReady -= changeStatusValue;
            break;
        case "On_Loan":
            updatedOnLoan -= changeStatusValue;
            break;
        case "Faulty":
            updatedFaulty -= changeStatusValue;
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_STATUS_FIELD);
        }

        switch (changeStatusDescriptor.getUpdatedStatus()) {
        case "Ready":
            updatedReady += changeStatusValue;
            break;
        case "On_Loan":
            updatedOnLoan += changeStatusValue;
            break;
        case "Faulty":
            updatedFaulty += changeStatusValue;
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_STATUS_FIELD);
        }
        if (updatedReady < 0 || updatedOnLoan < 0 || updatedFaulty < 0) {
            throw new CommandException(MESSAGE_STATUS_CONSTRAINTS);
        }
        updatedStatus = new Status(updatedReady, updatedOnLoan, updatedFaulty);

        return new Item(itemToUpdate.getName(), itemToUpdate.getQuantity(), itemToUpdate.getMinQuantity(),
                updatedStatus, itemToUpdate.getTags());
    }
    /**
     * Stores the details to update the item with.
     */
    public static class ChangeStatusDescriptor {
        private Name name;
        private Integer changeStatusQuantity;
        private String initialStatus;
        private String updatedStatus;

        public ChangeStatusDescriptor() {
        }

        public ChangeStatusDescriptor(Name name, Integer changeStatusQuantity,
                                      String initialStatus, String updatedStatus) {
            this.name = name;
            this.changeStatusQuantity = changeStatusQuantity;
            this.initialStatus = initialStatus;
            this.updatedStatus = updatedStatus;
        }
        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public ChangeStatusDescriptor (ChangeStatusDescriptor toCopy) {
            setName(toCopy.name);
            setQuantity(toCopy.changeStatusQuantity);
            setInitialStatus(toCopy.initialStatus);
            setUpdatedStatus(toCopy.updatedStatus);
        }
        public void setName(Name name) {
            this.name = name;
        }
        public Name getName() {
            return name;
        }
        public void setQuantity(Integer changeStatusQuantity) {
            this.changeStatusQuantity = changeStatusQuantity;
        }
        public Integer getQuantity() {
            return changeStatusQuantity;
        }
        public void setInitialStatus(String initialStatus) {
            this.initialStatus = initialStatus;
        }
        public String getInitialStatus() {
            return initialStatus;
        }

        public void setUpdatedStatus(String updatedStatus) {
            this.updatedStatus = updatedStatus;
        }
        public String getUpdatedStatus() {
            return updatedStatus;
        }

    }
}
```
###### \java\seedu\address\logic\commands\StatusCommand.java
``` java

/**
 * Lists all items in the stock list to the user grouped by the status.
 */
public class StatusCommand extends Command {

    public static final String COMMAND_WORD = "status";
    public static final String MESSAGE_SUCCESS = "Items listed according to status";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ArrayList<SimpleItem> readyItems = new ArrayList<>();
        ArrayList<SimpleItem> onLoanItems = new ArrayList<>();
        ArrayList<SimpleItem> faultyItems = new ArrayList<>();

        List<Item> lastShownList = model.getFilteredItemList();

        sortSimpleItems(lastShownList, readyItems, onLoanItems, faultyItems);

        String messageOutput = getMessageOutput(readyItems, onLoanItems, faultyItems);

        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);

        return new CommandResult(messageOutput);
    }

    /**
     * Sorts the item in the item list into Ready, On_Loan, and Faulty.
     */
    void sortSimpleItems (List<Item> lastShownList, ArrayList<SimpleItem> readyItems,
                          ArrayList<SimpleItem> onLoanItems, ArrayList<SimpleItem> faultyItems) {
        for (Item item : lastShownList) {
            if (item.getStatus().getStatusReady() > 0) {
                readyItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getStatus().getStatusReady()))));
            }
            if (item.getStatus().getStatusOnLoan() > 0) {
                onLoanItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getStatus().getStatusOnLoan()))));
            }
            if (item.getStatus().getStatusFaulty() > 0) {
                faultyItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getStatus().getStatusFaulty()))));
            }
        }
    }

    String getMessageOutput (ArrayList<SimpleItem> readyItems,
                           ArrayList<SimpleItem> onLoanItems, ArrayList<SimpleItem> faultyItems) {
        String messageOutput = "";
        messageOutput += MESSAGE_SUCCESS + "\n";

        messageOutput += "Ready: ";
        int counter = 0;
        for (SimpleItem simpleItem : readyItems) {
            counter++;
            messageOutput += simpleItem.getName() + ": " + simpleItem.getQuantity().toString();
            if (counter != readyItems.size()) {
                messageOutput += ", ";
            }
        }

        messageOutput += "\nOn Loan: ";
        counter = 0;
        for (SimpleItem simpleItem : onLoanItems) {
            counter++;
            messageOutput += simpleItem.getName() + ": " + simpleItem.getQuantity().toString();
            if (counter != onLoanItems.size()) {
                messageOutput += ", ";
            }
        }


        messageOutput += "\nFaulty: ";
        counter = 0;
        for (SimpleItem simpleItem : faultyItems) {
            counter++;
            messageOutput += simpleItem.getName() + ": " + simpleItem.getQuantity().toString();
            if (counter != onLoanItems.size()) {
                messageOutput += ", ";
            }
        }

        return messageOutput;
    }

}
```
###### \java\seedu\address\logic\parser\ChangeStatusCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangeStatusCommand object
 */
public class ChangeStatusCommandParser implements Parser<ChangeStatusCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeStatusCommand
     * and returns an ChangeStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ChangeStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                        PREFIX_QUANTITY, PREFIX_ORIGINAL_STATUS, PREFIX_NEW_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_ORIGINAL_STATUS, PREFIX_NEW_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeStatusCommand.MESSAGE_USAGE));
        }
        String initialStatus = ParserUtil
                .parseStatus(argMultimap.getValue(PREFIX_ORIGINAL_STATUS).get());
        String updatedStatus = ParserUtil
                .parseStatus(argMultimap.getValue(PREFIX_NEW_STATUS).get());
        if (initialStatus.equals("On_Loan") || updatedStatus.equals("On_Loan")) {
            throw new ParseException(String.format(ChangeStatusCommand.MESSAGE_INVALID_STATUS_FIELD));
        }
        ChangeStatusDescriptor changeStatusDescriptor = new ChangeStatusDescriptor();
        changeStatusDescriptor.setName(ParserUtil
                .parseName(argMultimap.getValue(PREFIX_NAME).get()));
        changeStatusDescriptor.setQuantity(ParserUtil
                .parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()).toInteger());
        changeStatusDescriptor.setInitialStatus(initialStatus);
        changeStatusDescriptor.setUpdatedStatus(updatedStatus);

        return new ChangeStatusCommand(changeStatusDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\item\SimpleItem.java
``` java

/**
 * Simplified item with fields removed to be used in the status command
 */

public class SimpleItem {
    private final Name name;
    private final Quantity quantity;

    public SimpleItem(Name name, Quantity quantity) {
        requireAllNonNull(name, quantity);
        this.name = name;
        this.quantity = quantity;
    }
    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

}
```
###### \java\seedu\address\model\item\Status.java
``` java
public class Status {
    public static final int STATUS_READY = 0;
    public static final int STATUS_ON_LOAN = 1;
    public static final int STATUS_FAULTY = 2;
    private final List<Integer> status = new ArrayList<>(3);

    public Status() {
        this(0, 0, 0);
    }
    public Status(int ready, int onLoan, int faulty) {
        status.add(ready);
        status.add(onLoan);
        status.add(faulty);
    }
    public Integer getStatusReady() {
        return status.get(STATUS_READY);
    }
    public Integer getStatusOnLoan() {
        return status.get(STATUS_ON_LOAN);
    }
    public Integer getStatusFaulty() {
        return status.get(STATUS_FAULTY);
    }

    public void setStatusReady(Integer ready) {
        status.set(STATUS_READY, ready);
    }
    public void setStatusOnLoan(Integer onLoan) {
        status.set(STATUS_ON_LOAN, onLoan);
    }
    public void setStatusFaulty(Integer faulty) {
        status.set(STATUS_FAULTY, faulty);
    }
    public void setDefaultValues(int quantity) {
        status.set(STATUS_READY, quantity);
        status.set(STATUS_ON_LOAN, 0);
        status.set(STATUS_FAULTY, 0);
    }
}

```
