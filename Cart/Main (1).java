public class Main {
    public static void main(String[] args) {
        Product product = new Product();
        Cart cart = new Cart();


        cart.addtocart(product.towar[7]);
        cart.addtocart(product.towar[7]);
        cart.addtocart(product.towar[7]);
        cart.addtocart(product.towar[7]);
        cart.addtocart(product.towar[7]);



        System.out.println("Cart contents:");
        cart.displayCartItems();


        double totalAfterDiscounts = product.applyDiscounts(cart);
        System.out.println("Total cart sum: " + totalAfterDiscounts);

        System.out.println("Cart contents after applying discounts:");
        cart.displayCartItems();
    }
}