package Auditoriska_3.zad1;

import java.util.Arrays;

public class Bank {
    private Account[] accounts;
    private int totalAccounts;
    private int max;

    public Bank(int max) {
        this.totalAccounts = 0;
        this.max = max;
        accounts = new Account[max];
    }

    public void addAccount(Account acc) {
        if (totalAccounts == accounts.length) {
            accounts = Arrays.copyOf(accounts, max * 2);
        }
        accounts[totalAccounts++] = acc;
    }

    public int totalAssets() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++) {
            sum += accounts[i].getBalance();
        }
        return sum;
    }

    public void addInterest() {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] instanceof InterestBearingAccount) {
                InterestBearingAccount acc = (InterestBearingAccount) accounts[i];
                acc.addInterest();
            }
        }
    }
}
