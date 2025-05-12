package Zbirka_Zadaci;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Component implements Comparable<Component> {
    String color;
    int weight;
    Set<Component> inner;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        this.inner = new TreeSet<>();
    }

    public void addComponent(Component component) {
        inner.add(component);
    }

    public void changeColor(int weight, String color) {
        if (this.weight < weight) {
            this.color = color;
        }
        for (Component component : inner) {
            change(component, weight, color);
        }
    }

    public void change(Component component, int weight, String color) {
        if (component.weight < weight) {
            component.weight = weight;
        }
        for (Component c : component.inner) {
            change(c, weight, color);
        }
    }

    @Override
    public int compareTo(Component o) {
        int res = this.weight - o.weight;
        if (res == 0) {
            return this.color.compareTo(o.color);
        }
        return res;
    }

    private static void createString(StringBuilder sb, Component c, int level) {
        for (int i = 0; i < level; i++) {
            sb.append("---");
            sb.append(String.format("%d:%s\n", c.weight, c.color));
            for (Component component : c.inner) {
                createString(sb, component, level + 1);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Component.createString(sb, this, 0);
        return sb.toString();
    }
}

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Component prev = null;
        while (true) {
            int what = scanner.nextInt();
            scanner.nextLine();
            if (what == 1) {
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
            scanner.nextLine();
        }
    }
}