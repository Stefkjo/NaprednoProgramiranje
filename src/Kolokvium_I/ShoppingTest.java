package Kolokvium_I;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class InvalidOperationException extends Exception {

    public InvalidOperationException(String message) {
        super(message);
    }
}

class Product implements Comparable<Product> {
    private String type;
    private String ID;
    private String name;
    private double price;
    private double quantity;
    private double totalPrice;

    public Product(String type, String ID, String name, String price, String quantity) {
        this.type = type;
        this.ID = ID;
        this.name = name;
        this.price = Double.parseDouble(price);
        this.quantity = Double.parseDouble(quantity);

    }

    public Double getTotalPrice() {
        if (this.type.startsWith("P")) {
            this.totalPrice = (quantity * price)/1000;
        } else {
            this.totalPrice = quantity * price;
        }
        return totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public Integer getID(){
        return Integer.parseInt(this.ID);
    }

    public void applyDiscount(){
        this.price = price - (price*0.1);
    }

    @Override
    public int compareTo(Product other) {
        return Double.compare(other.totalPrice, this.totalPrice);
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f\n", ID,totalPrice);
    }
}

class ShoppingCart {

    private List<Product> products = new ArrayList<>();

    public void addItem(String line) throws InvalidOperationException {

        String[] parts = line.split(";");
        if (parts[4].compareTo("0") == 0) throw new InvalidOperationException(String.format("The quantity of the product with id %s can not be 0.", parts[1]));

        Product product = new Product(parts[0], parts[1], parts[2], parts[3], parts[4]);

        products.add(product);

    }

    public void printShoppingCart(OutputStream os) {

        PrintWriter wr = new PrintWriter(os);
        products.forEach(Product::getTotalPrice);
        products.sort(Comparator.naturalOrder());

        for(Product product:products){
            wr.write(product.toString());
        }

        wr.flush();
    }

    public void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {

        if(discountItems.isEmpty()) throw new InvalidOperationException("There are no products with discount.");

        PrintWriter wr = new PrintWriter(os);
        List<Integer> discountProducts = new ArrayList<>();
        List<Double> profit = new ArrayList<>();


        for(Product product : products){
            for (Integer discountItem : discountItems) {
                if (product.getID().compareTo(discountItem) == 0) {
                    double originalTotalPrice = product.getTotalPrice();
                    product.applyDiscount();
                    double discountedTotalPrice = product.getTotalPrice();

                    profit.add(originalTotalPrice-discountedTotalPrice);
                    discountProducts.add(product.getID());
                }
            }
        }

        for(int i = 0;i<discountProducts.size();i++){
            wr.write(String.format("%d - %.2f\n",discountProducts.get(i),profit.get(i)));
        }

        wr.flush();

    }
}

public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}