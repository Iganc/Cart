import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Towar> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addtocart(Towar item) {
        items.add(item);
    }
    public List<Towar> getItems() {
        return items;
    }
    public int itemsincart() {
        int total_items = items.size();
        return total_items;
    }
    public double cartsum() {
        double total = 0.0;
        for (Towar item : items) {
            total += item.price;
        }
        return total;
    }
    public void sortItems() { //Segregowanie w koszyku, malejaco wedlug ceny, potem wedlug alfabetu.
        items.sort((item1, item2) -> {
            int priceComparison = Double.compare(item2.price, item1.price);
            if (priceComparison == 0) {
                return item1.name.compareTo(item2.name);
            }
            return priceComparison;
        });
    }
    public void displayCartItems() {
        for (Towar item : items) {
            System.out.println("Code: " + item.code +
                    ", Name: " + item.name +
                    ", Price: " + item.price +
                    ", Discount Price: " + item.discountPrice);
        }
    }

}