package seedu.address.model.login.exceptions;


import seedu.address.model.person.exceptions.DuplicatePersonException;

public class DuplicateUserException extends DuplicatePersonException {
    public DuplicateUserException(){
        super("Doing this will result in duplicate users!");
    }
}
