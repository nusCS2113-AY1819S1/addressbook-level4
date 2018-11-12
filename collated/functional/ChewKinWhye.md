# ChewKinWhye
###### \java\seedu\address\logic\commands\ChangeStatusCommand.java
``` java

/**
 * Updates the status of an existing item in the stock list.
 */

public class ChangeStatusCommand extends Command {
    public static final String COMMAND_WORD = "changeStatus";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the status of the item identified "
            + "by the name of the item.\n"
            + "The status field can only take the values Ready or Faulty\n"
            + "Changing the status to On_Loan is not allowed\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_ORIGINAL_STATUS + "ORIGINAL STATUS "
            + PREFIX_NEW_STATUS + "NEW STATUS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Arduino "
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

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        List<Item> lastShownList = model.getFilteredItemList();

        index = getIndex(lastShownList, changeStatusDescriptor);

        Item itemToUpdate = lastShownList.get(index.getZeroBased());

        Item updatedItem = createUpdatedItem(itemToUpdate, changeStatusDescriptor);

        model.updateItem(itemToUpdate, updatedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_CHANGE_STATUS_SUCCESS, updatedItem));


    }

    public Index getIndex(List<Item> lastShownList, ChangeStatusDescriptor changeStatusDescriptor)
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
    public Item createUpdatedItem(Item itemToUpdate,
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
                itemToUpdate.getLoststatus(), updatedStatus, itemToUpdate.getTags());
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

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof ChangeStatusDescriptor)) {
                return false;
            }

            ChangeStatusDescriptor otherItem = (ChangeStatusDescriptor) other;
            return (otherItem.getName().equals(this.getName())
                    && otherItem.getQuantity().equals(this.getQuantity())
                    && otherItem.getInitialStatus().equals(this.getInitialStatus())
                    && otherItem.getUpdatedStatus().equals(this.getUpdatedStatus()));
        }

    }
}
```
###### \java\seedu\address\logic\commands\DeleteLoanListCommand.java
``` java

/**
 * Deletes an entry in the loan list base on the Index
 */

public class DeleteLoanListCommand extends Command {
    public static final String COMMAND_WORD = "deleteLoanList";
    public static final String MESSAGE_SUCCESS = "Loan list entry has been deleted";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the loan list entry identified "
            + "by the index number used in the display of the loan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_EMPTY = "Loan list is currently empty";
    public static final String MESSAGE_INVALID_INDEX = "The input index is invalid";

    private final Index index;

    public DeleteLoanListCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (!MainApp.getLoanListFile().exists()) {
            throw new CommandException(MESSAGE_EMPTY);
        }
        File loanListFile = MainApp.getLoanListFile();
        try {
            deleteLoanList(model, history, loanListFile);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
    /**
     * Deletes an entry in the loan list base on the Index
     */
    public void deleteLoanList(Model model, CommandHistory history, File loanListFile) throws Exception {

        JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XmlAdaptedLoanList xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                .unmarshal(loanListFile);
        ArrayList<XmlAdaptedLoanerDescription> loanList = xmlAdaptedLoanList.getLoanList();

        if (index.getOneBased() > loanList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        updateStatus(model, history, loanList.get(index.getZeroBased()));

        loanList.remove(index.getZeroBased());

        LoanListCommand.updateXmlLoanListFile(new XmlAdaptedLoanList(loanList), loanListFile);
    }
    /**
     * Changes the status from Ready to On_Loan
     */
    private void updateStatus(Model model, CommandHistory history, XmlAdaptedLoanerDescription loanerDescription)
            throws CommandException {
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptor =
                new ChangeStatusCommand.ChangeStatusDescriptor(new Name(loanerDescription.getItemName()),
                        loanerDescription.getQuantity(), "On_Loan", "Ready");
        ChangeStatusCommand changeStatusCommand = new ChangeStatusCommand(changeStatusDescriptor);
        changeStatusCommand.execute(model, history);
    }
}
```
###### \java\seedu\address\logic\commands\LoanListCommand.java
``` java

/**
 * Creates a loan list, and updates the status from Ready to On_Loan
 */

public class LoanListCommand extends Command {
    public static final String COMMAND_WORD = "loanList";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a loan list. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_LOANER + "LOANER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Arduino "
            + PREFIX_QUANTITY + "20 "
            + PREFIX_LOANER + "KinWhye";

    public static final String MESSAGE_SUCCESS = "Loan list created";

    private final LoanerDescription loaner;

    public LoanListCommand(LoanerDescription loaner) {
        this.loaner = loaner;
    }
    /**
     * Updates the XmlLoanListFile
     */

    public static void updateXmlLoanListFile(XmlAdaptedLoanList xmlAdaptedLoanList, File loanListFile)
            throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlAdaptedLoanList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(xmlAdaptedLoanList, System.out);
        jaxbMarshaller.marshal(xmlAdaptedLoanList, loanListFile);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        updateStatus(model, history);
        try {
            updateLoanList(MainApp.getLoanListFile(), loaner);
        } catch (JAXBException e) {
            System.out.println(e.toString());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
    /**
     * Updates the XmlAdaptedLoanList, then updates the XmlLoanListFile
     */
    public void updateLoanList(File loanListFile, LoanerDescription loaner) throws JAXBException {
        XmlAdaptedLoanerDescription toAdd = new XmlAdaptedLoanerDescription(loaner);
        JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);
        XmlAdaptedLoanList xmlAdaptedLoanList = new XmlAdaptedLoanList();
        if (loanListFile.exists()) {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                    .unmarshal(loanListFile);
        }
        xmlAdaptedLoanList.addLoaner(toAdd);
        updateXmlLoanListFile(xmlAdaptedLoanList, loanListFile);
    }
    /**
     * Changes the status from On_Loan to Ready
     */
    public void updateStatus(Model model, CommandHistory history) throws CommandException {
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptor =
                new ChangeStatusCommand.ChangeStatusDescriptor(loaner.getItemName(),
                        loaner.getQuantity().toInteger(), "Ready", "On_Loan");
        ChangeStatusCommand changeStatusCommand = new ChangeStatusCommand(changeStatusDescriptor);
        changeStatusCommand.execute(model, history);
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
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

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
###### \java\seedu\address\logic\commands\ViewLoanListCommand.java
``` java

/**
 * Lists all the entries in the loan list to the user.
 */

public class ViewLoanListCommand extends Command {
    public static final String COMMAND_WORD = "viewLoanList";
    public static final String MESSAGE_SUCCESS = "Loan list displayed";
    public static final String MESSAGE_EMPTY = "Loan list is currently empty";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (!MainApp.getLoanListFile().exists()) {
            throw new CommandException(MESSAGE_EMPTY);
        }
        String messageOutput = getMessageOutput(MainApp.getLoanListFile());
        return new CommandResult(messageOutput);
    }

    public String getMessageOutput(File loanListFile) throws CommandException {
        try {
            Integer counter = 1;
            String messageOutput = new String();
            messageOutput += MESSAGE_SUCCESS + "\n";
            JAXBContext context = JAXBContext.newInstance(XmlAdaptedLoanList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlAdaptedLoanList xmlAdaptedLoanList = (XmlAdaptedLoanList) unmarshaller
                    .unmarshal(loanListFile);
            if (xmlAdaptedLoanList.getLoanList().size() == 0) {
                throw new CommandException(MESSAGE_EMPTY);
            }
            for (XmlAdaptedLoanerDescription loanerDescription : xmlAdaptedLoanList.getLoanList()) {
                messageOutput += counter + ". ";
                messageOutput += loanerDescription.getLoanerName() + ": ";
                messageOutput += loanerDescription.getItemName() + " ";
                messageOutput += loanerDescription.getQuantity() + "\n";
                counter++;
            }
            return messageOutput;
        } catch (JAXBException e) {
            System.out.println(e.toString());
        }
        return null;
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
###### \java\seedu\address\logic\parser\DeleteLoanListCommandParser.java
``` java

/**
 * Parses input arguments and creates a new DeleteLoanListCommand object
 */
public class DeleteLoanListCommandParser implements Parser<DeleteLoanListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLoanListCommand
     * and returns an DeleteLoanListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public DeleteLoanListCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLoanListCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLoanListCommand.MESSAGE_USAGE), pe);
        }
    }
}
```
###### \java\seedu\address\logic\parser\LoanListCommandParser.java
``` java

/**
 * Parses input arguments and creates a new LoanListCommand object
 */
public class LoanListCommandParser implements Parser<LoanListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LoanListCommand
     * and returns an LoanListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public LoanListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_LOANER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_LOANER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
        }
        Name itemName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name loanerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_LOANER).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        return new LoanListCommand(new LoanerDescription(itemName, loanerName, quantity));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\item\LoanerDescription.java
``` java

/**
 * Represents a loaner in the loan list.
 */

public class LoanerDescription {
    private final Name itemName;
    private final Name loanerName;
    private final Quantity quantity;

    public LoanerDescription(Name itemName, Name loanerName, Quantity quantity) {
        requireAllNonNull(itemName, loanerName, quantity);
        this.itemName = itemName;
        this.loanerName = loanerName;
        this.quantity = quantity;
    }
    public LoanerDescription(LoanerDescription loaner) {
        itemName = loaner.getItemName();
        loanerName = loaner.getLoanerName();
        quantity = loaner.getQuantity();
    }
    public Name getItemName() {
        return itemName;
    }

    public Quantity getQuantity() {
        return quantity;
    }
    public Name getLoanerName() {
        return loanerName;
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getLoanerName())
                .append(" loanerName: ")
                .append(getLoanerName())
                .append(" itemName: ")
                .append(getItemName())
                .append(" Quantity: ")
                .append(getQuantity());
        return builder.toString();
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SimpleItem)) {
            return false;
        }

        SimpleItem otherItem = (SimpleItem) other;
        return (otherItem.getName().toString().equals(getName().toString())
                && otherItem.getQuantity().toString().equals(getQuantity().toString()));
    }
}
```
###### \java\seedu\address\model\item\Status.java
``` java

/**
 * Represents an Item's status in the stock list.
 */
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
###### \java\seedu\address\storage\XmlAdaptedLoanerDescription.java
``` java

/**
 * JAXB-friendly version of the LoanerDescription.
 */
@XmlRootElement(name = "Loaner")
@XmlAccessorType(XmlAccessType.FIELD)

public class XmlAdaptedLoanerDescription {
    private String itemName;
    private String loanerName;
    private Integer quantity;

    public XmlAdaptedLoanerDescription() {

    }
    public XmlAdaptedLoanerDescription(String itemName, String loanerName, Integer quantity) {
        this.itemName = itemName;
        this.loanerName = loanerName;
        this.quantity = quantity;
    }
    public XmlAdaptedLoanerDescription(LoanerDescription loaner) {
        itemName = loaner.getItemName().toString();
        loanerName = loaner.getLoanerName().toString();
        quantity = loaner.getQuantity().toInteger();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedLoanList.java
``` java

/**
 * JAXB-friendly loan list.
 */

@XmlRootElement(name = "LoanList")
@XmlAccessorType(XmlAccessType.FIELD)

public class XmlAdaptedLoanList {
    @XmlElement(name = "Loaner")
    private ArrayList<XmlAdaptedLoanerDescription> xmlAdaptedLoanList = null;

    public XmlAdaptedLoanList() {
        xmlAdaptedLoanList = new ArrayList<>();
    }
    public XmlAdaptedLoanList(ArrayList<XmlAdaptedLoanerDescription> xmlAdaptedLoanList) {
        this.xmlAdaptedLoanList = xmlAdaptedLoanList;
    }
    public ArrayList<XmlAdaptedLoanerDescription> getLoanList() {
        return xmlAdaptedLoanList;
    }

    public void addLoaner(XmlAdaptedLoanerDescription toAdd) {
        xmlAdaptedLoanList.add(toAdd);
    }

}
```
