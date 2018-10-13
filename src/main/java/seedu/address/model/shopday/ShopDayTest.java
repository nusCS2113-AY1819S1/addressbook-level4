package seedu.address.model.shopday;

/**
 * TODO
 */
public class ShopDayTest {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ShopDay shopDay = new ShopDay();
        ShopDay shopDay1 = new ShopDay("2018/08/16");
        System.out.println("Can compile. Current day: " + shopDay.getDay() + ", test day: " + shopDay1.getDay());
    }
}
