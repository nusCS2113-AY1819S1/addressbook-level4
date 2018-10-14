package seedu.address.model.expenditureinfo.exceptions;

public class DuplicateExpenditureException extends RuntimeException{
        public DuplicateExpenditureException() {
            super("Operation would result in duplicate expenditures");
        }


}
