package seedu.address.storage.serializable;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.user.User;
import seedu.address.storage.adapter.XmlAdaptedUser;

/**
 This user manager stores users for Trajectory.
 */
@XmlRootElement(namespace = "seedu.address.model")
public class XmlSerializableUserList {

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private ArrayList<XmlAdaptedUser> userList = new ArrayList<XmlAdaptedUser>();

    public XmlSerializableUserList() {
        userList = new ArrayList<>();
    }

    public XmlSerializableUserList(ArrayList<User> src) {
        userList.addAll(src.stream().map(XmlAdaptedUser::new).collect(Collectors.toList()));
    }


    public ArrayList<XmlAdaptedUser> getList() {
        return userList;
    }

    public void setUserList(ArrayList<XmlAdaptedUser> userList) {
        this.userList = userList;
    }




}



