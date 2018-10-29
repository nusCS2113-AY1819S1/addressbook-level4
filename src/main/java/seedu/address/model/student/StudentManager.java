package seedu.address.model.student;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * This class represents the model-level layer for student management.
 */
public class StudentManager {

    private static StudentManager initSM = new StudentManager();
    private Model studentModel;

    /**
     * Link StudentManager to initialized Model in MainApp.
     * @param model
     */
    public void initializeModel(Model model) {
        studentModel = model;

    }

    public static StudentManager getInstance() {
        if (initSM == null) {
            initSM = new StudentManager();
        }
        return initSM;
    }

    /**
     * Checks whether given matric code belongs to an existing student.
     * @param matricNo
     * @return
     */
    public boolean doesStudentExistForGivenMatricNo(String matricNo) {
        for (Person p:studentModel.getAddressBook().getPersonList()) {
            if (p.getMatricNo().matricNo.equals(matricNo)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether given matric code belongs to an existing student.
     * @param email
     * @return
     */
    public boolean doesStudentExistForGivenEmail(String email) {
        for (Person p:studentModel.getAddressBook().getPersonList()) {
            if (p.getEmail().value.equals(email)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Retrives a student object for a given matriculation no.
     * @param matricNo
     * @return
     */
    public Person retrieveStudentByMatricNo(String matricNo) {
        boolean studentFound = false;
        for (Person p:studentModel.getAddressBook().getPersonList()) {
            if (p.getMatricNo().matricNo.equals(matricNo)) {
                studentFound = true;
                return p;
            }
        }

        if (!studentFound) {
            throw new NullPointerException();
        }

        return null;
    }

    /**
     * Retrives a student object for a given matriculation no.
     * @param email
     * @return
     */
    public Person retrieveStudentByEmail(String email) {
        boolean studentFound = false;
        for (Person p:studentModel.getAddressBook().getPersonList()) {
            if (p.getEmail().value.equals(email)) {
                studentFound = true;
                return p;
            }
        }

        if (!studentFound) {
            throw new NullPointerException();
        }

        return null;
    }
}
