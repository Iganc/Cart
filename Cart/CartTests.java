import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CartTests {
    private Cart cart;
    private Towar item0, item1, item2, item3, item4, item5, item6, item7, item8;
    private Product product;

    @BeforeEach
    public void setUp() {
        cart = new Cart();
        product = new Product();
        item0 = product.towar[0];
        item1 = product.towar[1];
        item2 = product.towar[2];
        item3 = product.towar[3];
        item4 = product.towar[4];
        item5 = product.towar[5];
        item6 = product.towar[6];
        item7 = product.towar[7];
        item8 = product.towar[8];

    }

    @Test
    public void testAddToCart() {
        cart.addtocart(item0);
        assertEquals(1, cart.itemsincart());
        assertTrue(cart.getItems().contains(item0));
    }

    @Test
    public void testGetItems() {
        cart.addtocart(item0);
        cart.addtocart(item1);
        List<Towar> items = cart.getItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item0));
        assertTrue(items.contains(item1));
    }

    @Test
    public void testItemsInCart() {
        cart.addtocart(item0);
        cart.addtocart(item1);
        assertEquals(2, cart.itemsincart());
    }

    @Test
    public void testCartSum() {
        cart.addtocart(item0);
        cart.addtocart(item1);
        assertEquals(3.0, cart.cartsum());
    }

    @Test
    public void testSortItems() {
        cart.addtocart(item0);
        cart.addtocart(item1);
        cart.addtocart(item2);
        cart.sortItems();
        List<Towar> items = cart.getItems();
        assertEquals(item1, items.get(0)); // maka (2.0)
        assertEquals(item2, items.get(1)); // salata (1.5)
        assertEquals(item0, items.get(2)); // pomidor (1.0)
    }

    @Test
    public void testFindCheapestProduct() {
        cart.addtocart(item0); // pomidor
        cart.addtocart(item1); // maka
        cart.addtocart(item2); // salata

        Towar cheapest = product.findCheapestProduct(cart);
        assertNotNull(cheapest);
        assertEquals("001", cheapest.code);
        assertEquals(1.0, cheapest.price);
    }

    @Test
    public void testFindMostExpensiveProduct() {
        Towar mostExpensive = product.findMostExpensiveProduct();
        assertNotNull(mostExpensive);
        assertEquals("004", mostExpensive.code);
        assertEquals(5.0, mostExpensive.price);
    }

    @Test
    public void testFindNCheapestProducts() {
        List<Towar> cheapestProducts = product.findNCheapestProducts(3);
        assertEquals(3, cheapestProducts.size());
        assertEquals("001", cheapestProducts.get(0).code);
        assertEquals("005", cheapestProducts.get(1).code);
        assertEquals("003", cheapestProducts.get(2).code);
    }

    @Test
    public void testFindNMostExpensiveProducts() {
        List<Towar> mostExpensiveProducts = product.findNMostExpensiveProducts(3);
        assertEquals(3, mostExpensiveProducts.size());
        assertEquals("004", mostExpensiveProducts.get(0).code);
        assertEquals("009", mostExpensiveProducts.get(1).code);
        assertEquals("008", mostExpensiveProducts.get(2).code);
    }

    @Test
    public void testSortProductsByPrice() {
        product.sortProductsByPrice();
        Towar[] sortedProducts = product.towar;
        assertEquals("001", sortedProducts[0].code);
        assertEquals("005", sortedProducts[1].code);
        assertEquals("003", sortedProducts[2].code);
    }

    @Test
    public void testSortProductsByName() {
        product.sortProductsByName();
        Towar[] sortedProducts = product.towar;
        assertEquals("005", sortedProducts[0].code); // chleb
        assertEquals("009", sortedProducts[1].code); // czekolada
        assertEquals("006", sortedProducts[2].code); // jajka
    }

    @Test
    public void testApplyDiscounts_TotalPriceDiscount() {
        cart.addtocart(item3);
        cart.addtocart(item3);

        //total = 10 10-5%=9.5
        double totalAfterDiscounts = product.applyDiscounts(cart);
        assertEquals(9.5, totalAfterDiscounts, 0.01); // Assuming original total is 10.0 and 5% discount is applied
    }

    @Test
    public void testApplyOnlyFreeItemPromotion() {
        cart.addtocart(item3); // Add items to meet the free item promotion condition
        cart.addtocart(item3);
        cart.addtocart(item0);
        cart.addtocart(item0);

        double totalBeforeDiscounts = cart.cartsum();
        double totalAfterDiscounts = product.applyDiscounts(cart);

        // Verify that the total price before discounts is greater than or equal to the free item threshold
        assertTrue(totalBeforeDiscounts >= 10.0);

        // Verify that the free item is added to the cart
        List<Towar> items = cart.getItems();
        assertTrue(items.stream().anyMatch(item -> item.price == 0.0));
        assertTrue(items.stream().anyMatch(item -> item.code.equals("009")));
    }

    @Test
    public void testApplyDiscounts_EggsDiscount() {
        Towar eggs = new Towar("006", "jajka", 2.0, 2.0);
        cart.addtocart(eggs);

        double totalAfterDiscounts = product.applyDiscounts(cart);
        assertEquals(1.7, totalAfterDiscounts, 0.01); // Assuming 15% discount on eggs
    }

}