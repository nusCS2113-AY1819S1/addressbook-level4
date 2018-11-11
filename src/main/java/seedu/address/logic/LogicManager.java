package seedu.address.logic;

import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.BookInventoryParser;
import seedu.address.logic.parser.DifferentiatingParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.request.Request;
import seedu.address.request.requestcommands.CommandSecondary;
import seedu.address.request.requestmodel.RequestModel;
import seedu.address.request.requestparser.RequestListParser;


/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final RequestModel requestModel;
    private final CommandHistory history;
    private final BookInventoryParser bookInventoryParser;
    private final RequestListParser requestListParser;
    private final DifferentiatingParser differentiatingParser;

    private String prevCommand;

    public LogicManager(Model model, RequestModel requestModel) {
        this.model = model;
        this.requestModel = requestModel;
        history = new CommandHistory();
        bookInventoryParser = new BookInventoryParser();
        requestListParser = new RequestListParser();
        differentiatingParser = new DifferentiatingParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        String[] string = commandText.trim().split("\\s+", 8);
        /*
         * Assign prevCommand appropriately, if Command History is not empty.
         */
        prevCommand = differentiatingParser.getPreviousCommand(history);
        /*
         * Decide if user input belongs to BookInventoryParser or RequestListParser.
         */
        if (differentiatingParser.parseInput(string, prevCommand, history)) {
            CommandSecondary command = requestListParser.parseCommandRequest(commandText);
            history.add(commandText);
            return command.execute(requestModel, history);
        } else {
            try {
                Command command = bookInventoryParser.parseCommand(commandText);
                return command.execute(model, history);
            } finally {
                history.add(commandText);
            }
        }
    }

    //@@author kennethcsj
    @Override
    public Queue<String> getCompleteIsbn(String isbnText) {
        return model.getCompleteIsbn(isbnText);
    }

    //@@author
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return model.getFilteredBookList();
    }

    @Override
    public ObservableList<Request> getFilteredRequestList() {
        return requestModel.getFilteredRequestList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    public List<String> getHistoryList () {
        return history.getHistory();
    }
}
