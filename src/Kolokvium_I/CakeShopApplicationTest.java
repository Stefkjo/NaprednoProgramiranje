package Kolokvium_I;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Item {
    private String name;
    private double price;

    public Item(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }


}

class Order implements Comparable<Order> {

    private String ID;
    private List<Item> items = new ArrayList<>();

    public Order(String ID, List<Item> items){
        this.items = items;
        this.ID = ID;
    }
    public Order(String id){
        this.ID = id;
    }

    public Order() {

    }

    public static Order createOrder(String line){
        String [] parts = line.split("\\s+");
        String orderID = parts[0];
        List<Item> items = new ArrayList<>();
        Item item = null;
        for(int i = 1;i< parts.length;i++){
            if(i%2==1){
                item = new Item(parts[i]);
            }else{
                item.setPrice(Double.parseDouble(parts[i]));
                items.add(item);
            }
        }

        return new Order(orderID, items);
    }

    public int getNumberOfItems(){
        return items.size();
    }


    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.getNumberOfItems(), other.getNumberOfItems());
    }
}


class CakeApplication {

    List<Order> orders = new ArrayList<>();

    public int readCakeOrders(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        orders = br.lines()
                .map(Order::createOrder)
                .collect(Collectors.toUnmodifiableList());

        return orders.stream().mapToInt(Order::getNumberOfItems).sum();

    }

    public void printLongestOrder(OutputStream os){

        PrintWriter wr = new PrintWriter(os);

        Order longestOrder = orders.stream().max(Comparator.naturalOrder()).orElseGet(Order::new);


    }

}


public class CakeShopApplicationTest {


}
