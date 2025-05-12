package Lab3.zad6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InvalidType extends Exception {

    public InvalidType(String message) {
        super(message);
    }
}

class InvalidPizzaException extends InvalidType {

    public InvalidPizzaException(String message) {
        super(message);
    }
}

class InvalidExtraTypeException extends InvalidType {

    public InvalidExtraTypeException(String message) {
        super(message);
    }
}

class ItemOutOfStockException extends Exception {

    public ItemOutOfStockException(Item item) {

    }
}

interface Item {
    int getPrice();

    String getName();
}

class ExtraItem implements Item {

    String name;
    int price;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if (type.compareTo("Coke") != 0 || type.compareTo("Ketchup") != 0) {
            throw new InvalidExtraTypeException("InvalidExtraTypeException");
        } else {
            this.name = type;
            if (type.compareTo("Coke") == 0) {
                this.price = 5;
            }
            if (type.compareTo("Ketchup") == 0) {
                this.price = 3;
            }
        }
    }

    public int getPrice() {
        return price;
    }

    public String getName(){
        return name;
    }
}

class PizzaItem implements Item {

    String name;
    int price;

    public PizzaItem(String type) throws InvalidExtraTypeException {
        if (type.compareTo("Standard") != 0 || type.compareTo("Vegetarian") != 0 || type.compareTo("Pepperoni") != 0) {
            throw new InvalidExtraTypeException("InvalidExtraTypeException");
        } else {
            this.name = type;
            if (type.compareTo("Standard") == 0) {
                this.price = 10;
            }
            if (type.compareTo("Vegetarian") == 0) {
                this.price = 8;
            }
            if (type.compareTo("Pepperoni") == 0) {
                this.price = 12;
            }
        }
    }

    public String getName(){
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class Order {

    List<Item> orderedItems;
    List<Integer> itemsCount;
    boolean isLocked = false;

    public Order() {
        orderedItems = new ArrayList<>();
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException {
        if(count>10) throw new ItemOutOfStockException(item);

        if(!orderedItems.contains(item)){
            orderedItems.add(item);
            itemsCount.add(count);
        }else{
            itemsCount.set(orderedItems.indexOf(item), count);
        }
    }

    public int getPrice(){
        int totalPrice = 0;

        for(int i = 0;i<orderedItems.size();i++){
            totalPrice+=orderedItems.get(i).getPrice()*itemsCount.get(i);
        }

        return totalPrice;
    }

    public void displayOrder(){

        StringBuilder builder = new StringBuilder();

        for(int i = 0;i<itemsCount.size();i++){
            int itemTotal = orderedItems.get(i).getPrice();
            builder.append(String.format("%3d.%-15s x%2d %5d$\n", i+1, orderedItems.get(i).getName(),itemsCount.get(i),itemTotal));
        }

        builder.append(String.format("%-22s %5d\n","Total",this.getPrice()));

        System.out.println(builder);
    }
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    //order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                //order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                //order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                //order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}