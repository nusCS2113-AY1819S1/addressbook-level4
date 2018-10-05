package seedu.address.model.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.RPLIDAR;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.model.item.exceptions.ItemNotFoundException;
import seedu.address.testutil.ItemBuilder;

public class UniqueItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.contains(null);
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(ARDUINO));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(ARDUINO);
        assertTrue(uniqueItemList.contains(ARDUINO));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(ARDUINO);
        Item editedAlice = new ItemBuilder(ARDUINO).withQuantity(VALID_QUANTITY_ARDUINO).withTags(VALID_TAG_LAB1)
                .build();
        assertTrue(uniqueItemList.contains(editedAlice));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.add(null);
    }

    @Test
    public void add_duplicateItem_throwsDuplicateItemException() {
        uniqueItemList.add(ARDUINO);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.add(ARDUINO);
    }

    @Test
    public void setItem_nullTargetItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(null, ARDUINO);
    }

    @Test
    public void setItem_nullEditedItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(ARDUINO, null);
    }

    @Test
    public void setItem_targetItemNotInList_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.setItem(ARDUINO, ARDUINO);
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueItemList.add(ARDUINO);
        uniqueItemList.setItem(ARDUINO, ARDUINO);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(ARDUINO);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueItemList.add(ARDUINO);
        Item editedAlice = new ItemBuilder(ARDUINO).withQuantity(VALID_QUANTITY_ARDUINO).withTags(VALID_TAG_LAB1)
                .build();
        uniqueItemList.setItem(ARDUINO, editedAlice);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(editedAlice);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueItemList.add(ARDUINO);
        uniqueItemList.setItem(ARDUINO, RPLIDAR);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(RPLIDAR);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueItemList.add(ARDUINO);
        uniqueItemList.add(RPLIDAR);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItem(ARDUINO, RPLIDAR);
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.remove(null);
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.remove(ARDUINO);
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueItemList.add(ARDUINO);
        uniqueItemList.remove(ARDUINO);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((UniqueItemList) null);
    }

    @Test
    public void setItems_uniqueItemList_replacesOwnListWithProvidedUniqueItemList() {
        uniqueItemList.add(ARDUINO);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(RPLIDAR);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((List<Item>) null);
    }

    @Test
    public void setItems_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(ARDUINO);
        List<Item> itemList = Collections.singletonList(RPLIDAR);
        uniqueItemList.setItems(itemList);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(RPLIDAR);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_listWithDuplicateItems_throwsDuplicateItemException() {
        List<Item> listWithDuplicateItems = Arrays.asList(ARDUINO, ARDUINO);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItems(listWithDuplicateItems);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueItemList.asUnmodifiableObservableList().remove(0);
    }
}
