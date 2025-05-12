package Kolokvium_II_Ispit;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}


class Product {
    String category;
    String id;
    String name;
    LocalDateTime date;
    double price;
    int sold;

    public Product(String category, String id, String name, LocalDateTime date, double price) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public int getSold() {
        return sold;
    }

    public double getCost() {
        return sold * price;
    }

    public void setSold(int quantity) {
        this.sold = quantity;
    }

    //Product{id='050be27b', name='product0', createdAt=2019-01-14T23:17:46.715710, price=2913.14, quantitySold=14}

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + date +
                ", price=" + price +
                ", quantitySold=" + sold +
                '}';
    }
}


class OnlineShop {
    Map<String, Product> products;

    OnlineShop() {
        products = new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price) {
        Product product = new Product(category, id, name, createdAt, price);
        products.put(id, product);
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException {
        if (!products.containsKey(id)) {
            throw new ProductNotFoundException(String.format("Product with id %s does not exist in the online shop!", id));
        }
        products.get(id).setSold(quantity);
        return products.get(id).getPrice() * quantity;
    }

//    NEWEST_FIRST,
//    OLDEST_FIRST,
//    LOWEST_PRICE_FIRST,
//    HIGHEST_PRICE_FIRST,
//    MOST_SOLD_FIRST,
//    LEAST_SOLD_FIRST

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<List<Product>> result = new ArrayList<>();

        List<Product> productsList = new ArrayList<>(products.values()
                .stream()
                .filter(product -> category == null || product.getCategory().equals(category))
                .collect(Collectors.toUnmodifiableList()));

        Comparator<Product> compareByDate = Comparator.comparing(Product::getDate);
        Comparator<Product> compareByPrice = Comparator.comparing(Product::getPrice);
        Comparator<Product> compareByQuantity = Comparator.comparing(Product::getSold);

        if (comparatorType == COMPARATOR_TYPE.NEWEST_FIRST) {
            compareByDate = compareByDate.reversed();
            productsList.sort(compareByDate);
        }
        if (comparatorType == COMPARATOR_TYPE.OLDEST_FIRST) {
            productsList.sort(compareByDate);
        }
        if (comparatorType == COMPARATOR_TYPE.HIGHEST_PRICE_FIRST) {
            compareByPrice = compareByPrice.reversed();
            productsList.sort(compareByPrice);
        }
        if (comparatorType == COMPARATOR_TYPE.LOWEST_PRICE_FIRST) {
            productsList.sort(compareByPrice);
        }
        if (comparatorType == COMPARATOR_TYPE.MOST_SOLD_FIRST) {
            compareByQuantity = compareByQuantity.reversed();
            productsList.sort(compareByQuantity);
        }
        if (comparatorType == COMPARATOR_TYPE.LEAST_SOLD_FIRST) {
            productsList.sort(compareByQuantity);
        }

        for (int i = 0; i < productsList.size(); i += pageSize) {
            result.add(new ArrayList<>(productsList.subList(i, Math.min(i + pageSize, productsList.size()))));
        }

        return result;
    }

}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category = null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}

