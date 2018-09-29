//author @tianhang
package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.BatchCommand;

import seedu.address.logic.commands.BatchToppingCommand;
import seedu.address.logic.parser.BatchIceCreamCommandParser;
import seedu.address.logic.parser.BatchToppingCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatchCommandParser  {
    public static final Pattern ICE_CREAM_CHECKER = Pattern.compile ("(?<iceCream>i/)");
    public static final Pattern TOPPING_CHECKER = Pattern.compile ("(?<topping>to/)");
    public Optional<BatchCommand> split (String args) throws ParseException {
        Matcher m = ICE_CREAM_CHECKER.matcher (args);
        System.out.println (m);
        if(m.find ()){
            System.out.println (1);
            return Optional.of(new BatchIceCreamCommandParser().parse (args));
        }
        m = TOPPING_CHECKER.matcher (args);
        System.out.println (m);
        if(m.find ()){
            System.out.println (2);
            return Optional.of(new BatchToppingCommandParser ().parse (args));
        }

        return Optional.empty ();
    }

}
