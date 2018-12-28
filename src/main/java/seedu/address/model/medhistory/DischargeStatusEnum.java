package seedu.address.model.medhistory;

//@@author xShaivan
/**
 * DischargeStatusEnum stores the key-value pair of
 * discharge status codes and the string they are associated with.
 */
public enum DischargeStatusEnum {
    d("discharged"), a("admitted"), e("expired");

    private String code;

    DischargeStatusEnum(String enumCode) {
        this.code = enumCode;
    }

    public String getCode() {
        return this.code;
    }
}
