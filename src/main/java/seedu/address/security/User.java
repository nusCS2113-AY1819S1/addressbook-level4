package seedu.address.security;

import seedu.address.model.person.Person;

/***
 * Represents the authenticated User
 */
public class User {
    private String username;
    private Person person;

    public User(String username) {
        this.username = username;
        //this.person = matchPerson(username);
    }

//    private Person matchPerson(String name){
//        //TODO Loop through personlist to get PersonClass
//    }

    /***
     *
     * @param name of the person that you are trying to find as a friend
     * @return boolean whether name is found as a friend
     */
//    public boolean isFriend(String name){
//
//    }
//
//    public void addFriend(String name){
//
//    }
//
//    public void removeFriend(String name){
//
//    }
}
