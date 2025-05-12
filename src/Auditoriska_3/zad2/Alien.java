package Auditoriska_3.zad2;

public abstract class Alien {

    private int health;
    private String name;

    public Alien(String name, int health) {
        this.health = health;
        this.name = name;
    }

    public abstract int getDamage();

}
