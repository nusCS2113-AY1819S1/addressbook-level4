# HeHaowei
###### \java\seedu\address\logic\commands\FoundCommand.java
``` java

/**
 * Found an existing item in the stock list.
 */

public class FoundCommand extends Command {
    public static final String COMMAND_WORD = "found";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Found a item from the stock list identified  "
            + "by the index number used in the displayed item list"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "25";


    public static final String MESSAGE_FOUND_ITEM_SUCCESS = "Found Item: %1$s";
    public static final String MESSAGE_INVALID_QUANTITY = "The found quantity input is invalid";

    private final Index targetIndex;
    private final FoundDescriptor foundDescriptor;

    public FoundCommand(Index targetIndex, FoundDescriptor foundDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(foundDescriptor);
        this.targetIndex = targetIndex;
        this.foundDescriptor = new FoundDescriptor(foundDescriptor);

    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToFound = lastShownList.get(targetIndex.getZeroBased());
        Item foundItem = createFoundItem(itemToFound, foundDescriptor);

        if (!itemToFound.isSameItem(foundItem) && model.hasItem(foundItem)) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        model.updateItem(itemToFound, foundItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_FOUND_ITEM_SUCCESS, foundItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToFound}
     * edited with {@code FoundDescriptor}.
     */
    private static Item createFoundItem(Item itemToFound, FoundDescriptor foundDescriptor)
            throws CommandException {
        assert itemToFound != null;
        Loststatus currentLoststatus = itemToFound.getLoststatus();
        Loststatus updatedLoststatus;
        Integer updatedLost = currentLoststatus.getLoststatusLost();
        Integer updatedFound = currentLoststatus.getLoststatusFound();

        Integer updatedValue = foundDescriptor.getFoundQuantity();
        Integer initialValue = itemToFound.getQuantity().toInteger();

        updatedLost -= updatedValue;
        updatedFound += updatedValue;
        if (updatedLost < 0) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        updatedLoststatus = new Loststatus(updatedLost, updatedFound);

        Quantity updatedQuantity = new Quantity(Integer.toString(initialValue + updatedValue));



        return new Item(itemToFound.getName(), updatedQuantity,
                itemToFound.getMinQuantity(), updatedLoststatus, itemToFound.getTags());
    }

    /**
     * Stores the details to lost the item with.
     */

    public static class FoundDescriptor {
        private Integer foundQuantity;

        public FoundDescriptor() {}

        public FoundDescriptor(FoundDescriptor toCopy) {
            setFoundQuantity(toCopy.foundQuantity);

        }
        public void setFoundQuantity(Integer foundQuantity) {
            this.foundQuantity = foundQuantity; }

        public Integer getFoundQuantity() {
            return foundQuantity; }

    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoundCommand // instanceof handles nulls
                && targetIndex.equals(((FoundCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\LostandFoundCommand.java
``` java

/**
 * Lists all lost items with the lost number in the stock list to the user.
 */
public class LostandFoundCommand extends Command {

    public static final String COMMAND_WORD = "lost&found";
    public static final String MESSAGE_SUCCESS = "Lost items listed";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ArrayList<SimpleItem> lostItems = new ArrayList<>();
        List<Item> lastShownList = model.getFilteredItemList();
        sortSimpleItems(lastShownList, lostItems);
        String messageOutput = getMessageOutput(lostItems);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(messageOutput);
    }

    /**
     * Sorts the lost item from the Stock List.
     */
    void sortSimpleItems (List<Item> lastShownList, List<SimpleItem> lostItems
                          ) {
        for (Item item : lastShownList) {
            if (item.getLoststatus().getLoststatusLost() > 0) {
                lostItems.add(new SimpleItem(item.getName(),
                        new Quantity(Integer.toString(item.getLoststatus().getLoststatusLost()))));
            }

        }
    }
    String getMessageOutput (List<SimpleItem> lostItems) {
        String messageOutput = "";
        messageOutput += MESSAGE_SUCCESS + "\n";

        messageOutput += "Lost: ";
        int counter = 0;
        for (SimpleItem simpleItem : lostItems) {
            counter++;
            messageOutput += counter + ". "
                    +
                    simpleItem.getName()
                    +
                    ": "
                    + simpleItem.getQuantity().toString()
                    + "\n"
                    + "        ";

        }

        return messageOutput;
    }

}
```
###### \java\seedu\address\logic\commands\LostCommand.java
``` java

/**
 * Lost an existing item in the stock list.
 */

public class LostCommand extends Command {
    public static final String COMMAND_WORD = "lost";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lost a item from the stock list identified  "
            + "by the index number used in the displayed item list"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "25";


    public static final String MESSAGE_LOST_ITEM_SUCCESS = "Lost Item: %1$s";
    public static final String MESSAGE_INVALID_QUANTITY = "The lost quantity input is invalid";

    private final Index targetIndex;
    private final LostDescriptor lostDescriptor;

    public LostCommand(Index targetIndex, LostDescriptor lostDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(lostDescriptor);
        this.targetIndex = targetIndex;
        this.lostDescriptor = new LostDescriptor(lostDescriptor);

    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToLost = lastShownList.get(targetIndex.getZeroBased());
        Item lostItem = createLostItem(itemToLost, lostDescriptor);

        if (!itemToLost.isSameItem(lostItem) && model.hasItem(lostItem)) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        model.updateItem(itemToLost, lostItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_LOST_ITEM_SUCCESS, lostItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToLost}
     * edited with {@code LostDescriptor}.
     */
    private static Item createLostItem(Item itemToLost, LostDescriptor lostDescriptor)
            throws CommandException {
        assert itemToLost != null;
        Loststatus currentLoststatus = itemToLost.getLoststatus();
        Loststatus updatedLoststatus;
        Integer updatedLost = currentLoststatus.getLoststatusLost();
        Integer updatedFound = currentLoststatus.getLoststatusFound();

        Integer updatedValue = lostDescriptor.getLostQuantity();
        Integer initialValue = itemToLost.getQuantity().toInteger();
        if (initialValue - updatedValue < 0) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        updatedLost += updatedValue;
        updatedFound -= updatedValue;
        updatedLoststatus = new Loststatus(updatedLost, updatedFound);

        Quantity updatedQuantity = new Quantity(Integer.toString(initialValue - updatedValue));



        return new Item(itemToLost.getName(), updatedQuantity,
                itemToLost.getMinQuantity(), updatedLoststatus, itemToLost.getTags());
    }

    /**
     * Stores the details to lost the item with.
     */

    public static class LostDescriptor {
        private Integer lostQuantity;

        public LostDescriptor(){}

        public LostDescriptor(LostDescriptor toCopy) {
            setLostQuantity(toCopy.lostQuantity);
        }
        public void setLostQuantity(Integer lostQuantity) {
            this.lostQuantity = lostQuantity; }

        public Integer getLostQuantity() {
            return lostQuantity; }

    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LostCommand // instanceof handles nulls
                && targetIndex.equals(((LostCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\FoundCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FoundCommand object
 */
public class FoundCommandParser implements Parser<FoundCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FoundCommand
     * and returns an FoundCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FoundCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY);

        Index targetIndex;
        try {
            targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FoundCommand.MESSAGE_USAGE), pe);
        }
        FoundDescriptor foundDescriptor = new FoundDescriptor();

        foundDescriptor.setFoundQuantity(ParserUtil
                .parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()).toInteger());

        return new FoundCommand(targetIndex, foundDescriptor);
    }

}

```
###### \java\seedu\address\logic\parser\LostCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LostCommand object
 */
public class LostCommandParser implements Parser<LostCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LostCommand
     * and returns an LostCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LostCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY);

        Index targetIndex;
        try {
            targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LostCommand.MESSAGE_USAGE), pe);
        }
        LostDescriptor lostDescriptor = new LostDescriptor();
        lostDescriptor.setLostQuantity(ParserUtil
                .parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()).toInteger());
        return new LostCommand(targetIndex, lostDescriptor);
    }
}

```
###### \java\seedu\address\model\item\Loststatus.java
``` java
public class Loststatus {
    public static final int LOSTSTATUS_LOST = 0;
    public static final int LOSTSTATUS_FOUND = 1;
    private final List<Integer> loststatus = new ArrayList<>(2);

    public Loststatus() {
        this(0, 0); }
    public Loststatus(int lost, int found) {
        loststatus.add(lost);
        loststatus.add(found);
    }
    public Integer getLoststatusLost() {
        return loststatus.get(LOSTSTATUS_LOST); }
    public Integer getLoststatusFound() {
        return loststatus.get(LOSTSTATUS_FOUND);
    }

    public void setLoststatusLost(Integer lost) {
        loststatus.set(LOSTSTATUS_LOST, lost);
    }
    public void setLoststatusFound(Integer found) {
        loststatus.set(LOSTSTATUS_FOUND, found);
    }
    public void setDefaultValues(int quantity) {
        loststatus.set(LOSTSTATUS_FOUND, quantity);
        loststatus.set(LOSTSTATUS_LOST, 0);

    }

}

```
