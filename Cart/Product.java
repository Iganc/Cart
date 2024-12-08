import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class Product {
    Towar[] towar;

    public Product() {
        towar = new Towar[9];
        towar[0] = new Towar("001", "pomidor", 1.0, 0.9);
        towar[1] = new Towar("002", "maka", 2.0, 1.8);
        towar[2] = new Towar("003", "salata", 1.5, 1.3);
        towar[3] = new Towar("004", "mieso", 5.0, 4.5);
        towar[4] = new Towar("005", "chleb", 1.2, 1.0);
        towar[5] = new Towar("006", "jajka", 2.5, 2.2);
        towar[6] = new Towar("007", "smietana", 1.8, 1.6);
        towar[7] = new Towar("008", "maslo", 3.0, 2.7);
        towar[8] = new Towar("009", "czekolada", 4.5, 4.0);
    }
    public double applyDiscounts(Cart cart) {
        double total = cart.cartsum();
        int total_items = cart.itemsincart();
        double disc_num = 8;
        double discount = 0.05;
        double free_item = 10;
        double promotion = 0.15;

        if (total >= disc_num) { // promocja 5% na zakupy od jakiejs sumy
            double disc = total * discount;
            total = total - disc;
        }

        if (total_items >= 3) { // przy zakupie 3 produktow, najtanszy gratis z koszyka
            Towar cheapest = findCheapestProduct(cart);
            if (cheapest != null) {
                total = total - cheapest.price;
            }
        }

        if (total >= free_item) { // gratisowy produkt od sumy
            Towar freeProduct = new Towar(towar[8].code, towar[8].name, 0.0, 0.0);
            cart.addtocart(freeProduct);
        }

        for (Towar item : cart.getItems()) { // obnizka jajek o 15%
            if (item.code.equals("006")) {
                double disc = item.price * promotion;
                total = total - disc;
                break;
            }
        }

        return total;
    }

    public Towar findCheapestProduct(Cart cart) {
        return cart.getItems().stream().min(Comparator.comparingDouble(t -> t.price)).orElse(null);
    }

    public Towar findMostExpensiveProduct() {
        return List.of(towar).stream().max(Comparator.comparingDouble(t -> t.price)).orElse(null);
    }

    public List<Towar> findNCheapestProducts(int n) {
        return List.of(towar).stream()
                .sorted(Comparator.comparingDouble(t -> t.price))
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Towar> findNMostExpensiveProducts(int n) {
        return List.of(towar).stream()
                .sorted(Comparator.comparingDouble(Towar::getPrice).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public void sortProductsByPrice() {
        List<Towar> mutableList = new ArrayList<>(List.of(towar));
        mutableList.sort(Comparator.comparingDouble(t -> t.price));
        towar = mutableList.toArray(new Towar[0]);
    }

    public void sortProductsByName() {
        List<Towar> mutableList = new ArrayList<>(List.of(towar));
        mutableList.sort(Comparator.comparing(t -> t.name));
        towar = mutableList.toArray(new Towar[0]);
    }
}