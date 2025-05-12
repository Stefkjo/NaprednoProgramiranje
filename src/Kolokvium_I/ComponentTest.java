package Kolokvium_I;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if (what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        //window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде
class Component implements Comparable<Component> {

    private String color;
    private Integer weight;
    private List<Component> components;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        this.components = new ArrayList<>();
    }

    public void addComponent(Component component) {
        components.add(component);
        components.sort((a, b) -> a.compareTo(b));
    }

    public int getWeight(){
        return this.weight;
    }
    public List<Component> getList(){
        return components;
    }

    public void setColor(String color){
        this.color = color;
    }
    @Override
    public int compareTo(Component other) {
        if (this.weight.compareTo(other.weight) == 0) {
            return (color.compareTo(other.color));
        }
        return this.weight.compareTo(other.weight);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(weight).append(":").append(color);
        return "";
    }
}

class Window {

    private String name;
    private List<Component> componentList;

    public Window(String name) {
        this.name = name;
    }

    public void addComponent(int position, Component component) throws InvalidPositionException {

        if (componentList.get(position) != null) {
            componentList.add(position, component);
        } else {
            throw new InvalidPositionException(String.format("Invalid position %d, already taken!", position));
        }
    }

    public void changeColor(int weight, String newColor){


    }

    void switchComponents(int pos1, int pos2){

        if (pos1 < 0 || pos2 < 0 || pos1 >= componentList.size() || pos2 >= componentList.size()) {
            throw new IllegalArgumentException("Invalid positions");
        }
        Component temp = componentList.get(pos1);
        componentList.set(pos1, componentList.get(pos2));
        componentList.set(pos2, temp);
    }

}














class InvalidPositionException extends Exception {

    public InvalidPositionException(String message) {
        super(message);
    }
}

























