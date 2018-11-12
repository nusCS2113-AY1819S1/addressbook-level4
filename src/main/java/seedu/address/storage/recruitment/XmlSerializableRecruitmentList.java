package seedu.address.storage.recruitment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.RecruitmentList;


/**
 * An Immutable RecruitmentList that is serializable to XML format
 */
@XmlRootElement(name = "recruitmentlist")
public class XmlSerializableRecruitmentList {

    public static final String MESSAGE_DUPLICATE_RECRUITMENT = "RecruitmentLists contain duplicate recruitmentPost(s).";

    @XmlElement
    private List<XmlAdaptedRecruitment> recruitments;

    /**
     * Creates an empty XmlSerializableRecruitmentList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableRecruitmentList() {
        recruitments = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRecruitmentList(ReadOnlyRecruitmentList src) {
        this();
        recruitments.addAll(src.getRecruitmentList().stream().map(XmlAdaptedRecruitment::new).collect(Collectors.toList
                ()));
    }

    /**
     * Converts this RecruitmentList into the model's {@code RecruitmentList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecruitment}.
     */
    public RecruitmentList toModelType() throws IllegalValueException {
        RecruitmentList recruitmentList = new RecruitmentList();
        for (XmlAdaptedRecruitment p : recruitments) {
            Recruitment recruitment = p.toModelPost();
            if (recruitmentList.hasRecruitment(recruitment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECRUITMENT);
            }
            recruitmentList.addRecruitment(recruitment);
        }
        return recruitmentList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRecruitmentList)) {
            return false;
        }
        return recruitments.equals(((XmlSerializableRecruitmentList) other).recruitments);
    }
}
