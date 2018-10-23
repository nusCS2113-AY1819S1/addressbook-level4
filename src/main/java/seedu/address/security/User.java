package seedu.address.security;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/***
 * Represents the authenticated User
 */
public class User {
    private Model model;
    private ObservableList<Person> list;
    private String username;
    private Person person;

    public User(String username, Model model) {
        this.username = username;
        this.model = model;
        matchPerson(username);
    }

    /***
     *
     * Finds @param name inside database to match it to the current user
     *
     */
    private void matchPerson(String name) {
        list = model.getAddressBook().getPersonList();
        //Loops through personlist to get matched name Person Class
        for (Person person : list) {
            if (name.equals(person.getName().toString())) {
                this.person = person;
            }
        }
    }

    /***
     *
     * @param name of the person that you are trying to find as a friend
     * @return boolean whether name is found as a friend
     */
    //public boolean isFriend(String name){
    //
    //}
    //
    //public void addFriend(String name){
    //
    //}
    //
    //public void removeFriend(String name){
    //
    //}
}
