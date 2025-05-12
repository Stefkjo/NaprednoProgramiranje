package Kolokvium_II_Ispit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Item {
    private int regularPrice;
    private int discountedPrice;

    public Item(int regularPrice, int discountedPrice) {
        this.regularPrice = regularPrice;
        this.discountedPrice = discountedPrice;
    }

    public int getDiscountDifference() {
        return regularPrice - discountedPrice;
    }

    public double getDiscountPercent() {
        return ((double) discountedPrice / regularPrice) * 100.0;
    }

    public int getRegularPrice() {
        return regularPrice;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    @Override
    public String toString() {
        return String.format("%d%% %d/%d",100 - Math.round(getDiscountPercent()), this.discountedPrice, this.regularPrice);
    }
}


class Store {
    private String name;
    private List<Item> items;

    public Store(String name) {
        items = new ArrayList<>();
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public double getAverageDiscount() {
        return 100 - items.stream()
                .mapToDouble(Item::getDiscountPercent)
                .sum() / items.size();
    }

    public int getTotalDiscount() {
        return items.stream()
                .mapToInt(Item::getDiscountDifference)
                .sum();
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append(String.format("Average discount: %.1f%%\n", getAverageDiscount()));
        sb.append(String.format("Total discount: %d\n", getTotalDiscount()));
        Comparator<Item> compareByDiscountPercent
                = Comparator.comparing(Item::getDiscountPercent);
        items.sort(compareByDiscountPercent);
        for(int i = 0;i<items.size();i++){
            if(i!=items.size()-1){
                sb.append(items.get(i)).append("\n");
            }else{
                sb.append(items.get(i));
            }
        }
        return sb.toString();
    }
}

class Discounts {
    private List<Store> stores;

    public Discounts() {
        stores = new ArrayList<>();
    }

    public int readStores(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        AtomicInteger at = new AtomicInteger(0);
        br.lines().forEach(line -> {
            at.incrementAndGet();
            String[] parts = line.split("\\s+");
            String name = parts[0];
            Store store = new Store(name);
            for (int i = 1; i < parts.length; i++) {
                String [] prices = parts[i].split(":");
                store.addItem(new Item(Integer.parseInt(prices[1]), Integer.parseInt(prices[0])));
            }
            stores.add(store);
        });
        return at.get();
    }

    public List<Store> byAverageDiscount(){
        Comparator<Store> compareByAverageDiscountAndName
                = Comparator.comparing(Store::getAverageDiscount).reversed()
                .thenComparing(Store::getName);

        return stores.stream()
                .sorted(compareByAverageDiscountAndName)
                .limit(3)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Store> byTotalDiscount(){
        Comparator<Store> compareByAbsoluteDiscountAndName
                = Comparator.comparing(Store::getTotalDiscount)
                .thenComparing(Store::getName);

        return stores.stream()
                .sorted(compareByAbsoluteDiscountAndName)
                .limit(3)
                .collect(Collectors.toUnmodifiableList());
    }

}

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

