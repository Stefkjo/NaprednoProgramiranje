package Auditoriska_3.zad1;

public abstract class Account {

    private String name;
    private int accNumber;
    private int balance;

    public Account(String name){
        this.name = name;
        accNumber = (int) (Math.random() * 23) - 32;
        balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void addToBalance(int addAmount){
        balance += addAmount;
    }

    public void withdrawFromBalance(int withdrawAmount){
        balance -= withdrawAmount;
    }


}
