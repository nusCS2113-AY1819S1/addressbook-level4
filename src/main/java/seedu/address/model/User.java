package seedu.address.model;

import seedu.address.model.person.Person;
import seedu.address.model.person.UserParameterHelper;

/***
 * Represents the authenticated User
 */
public class User extends Person {

    public User(UserParameterHelper param) {
        super(param.getName(), param.getPhone(), param.getEmail(), param.getAddress(), param.getTags(),
                param.getTimeTable(), param.getFriendList());
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
