package Lab2;

import java.util.*;
import java.util.stream.Collectors;

class Account {
    private String name;
    private long id;
    private double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.id = (long) Math.floor(Math.random() * 1000000);
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nBalance:%.2f\n", name, balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account acc = (Account) o;
        return id == acc.id && Double.compare(balance, acc.balance) == 0 && name.compareTo(acc.name) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, balance);
    }
}

abstract class Transaction {

    private long idFrom;
    private long idTo;
    private String description;
    private double amount;

    public Transaction(long idFrom, long idTo, String description, double amount) {
        this.amount = amount;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.description = description;
    }

    public long getIdFrom() {
        return idFrom;
    }

    public long getIdTo() {
        return idTo;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction tr = (Transaction) o;
        return idFrom == tr.idFrom && idTo == tr.idTo && Double.compare(amount, tr.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFrom, idTo, amount);
    }

    public String getDescription() {
        return description;
    }
}

class FlatAmountProvisionTransaction extends Transaction {

    private final double FlatAmount;

    public FlatAmountProvisionTransaction(long idFrom, long idTo, double amount, double flatProvision) {
        super(idFrom, idTo, ".", amount);
        this.FlatAmount = flatProvision;
    }

    public double getFlatAmount() {
        return FlatAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FlatAmountProvisionTransaction transaction = (FlatAmountProvisionTransaction) o;
        return FlatAmount == transaction.FlatAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFlatAmount());
    }
}

class FlatPercentProvisionTransaction extends Transaction {

    private final int FlatPercent;

    public FlatPercentProvisionTransaction(long idFrom, long idTo, double amount, int centsPerDollar) {
        super(idFrom, idTo, ".", amount);
        this.FlatPercent = centsPerDollar;
    }

    public int getPercent() {
        return FlatPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FlatPercentProvisionTransaction tr = (FlatPercentProvisionTransaction) o;
        return FlatPercent == tr.FlatPercent;
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), FlatPercent);
    }
}

class Bank {
    private List<Account> accounts;
    private String name;
    private double totalTransfers;
    private double totalProvisions;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = Arrays.stream(accounts).collect(Collectors.toUnmodifiableList());
    }

    public boolean makeTransaction(Transaction t) {
        Account from = findAccountById(t.getIdFrom());
        Account to = findAccountById(t.getIdTo());

        if(from == null || to == null || from.getBalance() < t.getAmount()){
            return false;
        }

        double provision = 0;
        if(t instanceof FlatAmountProvisionTransaction){
            provision = ((FlatAmountProvisionTransaction) t).getFlatAmount();
        }else if(t instanceof FlatPercentProvisionTransaction){
            provision = t.getAmount() * ((FlatPercentProvisionTransaction) t).getPercent()/100.0;
        }

        double totalDeduction = t.getAmount() + provision;
        if(from.getBalance() < totalDeduction){
            return false;
        }

        from.setBalance(from.getBalance() - totalDeduction);
        to.setBalance(to.getBalance()+ t.getAmount());
        totalTransfers += t.getAmount();
        totalProvisions += provision;
        return true;
    }

    public double totalTransfers(){
        return totalTransfers;
    }

    public double totalProvision(){
        return totalProvisions;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(name).append("\n\n");
        for (Account account : accounts) {
            builder.append(account.toString()).append("\n");
        }

        return builder.toString();
    }

    private Account findAccountById(long id){
        return accounts.stream().filter(acc -> acc.getId()==id).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Bank bank = (Bank) o;
        return name.compareTo(bank.name)==0 && accounts.equals(bank.accounts);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, accounts);
    }

    public Account[] getAccounts() {
        return (Account[]) accounts.toArray();
    }
}

public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static double parseAmount(String amount) {
        return Double.parseDouble(amount.replace("$", ""));
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", 20.0);
        Account a2 = new Account("Andrej", 20.0);
        Account a3 = new Account("Andrej", 30.0);
        Account a4 = new Account("Gajduk", 20.0);
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1) && !a1.equals(a2) && !a2.equals(a1) && !a3.equals(a1)
                && !a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, 20.0, 10.0);
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, 20.0, 10.0);
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, 20.0, 10.0);
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, 50.0, 50.0);
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, 20.0, 10.0);
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, 20.0, 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, 20.0, 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, 20.0, 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, 50.0, 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, 20.0, 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, 20.0, 10);
        if (fa1.equals(fa1) &&
                !fa2.equals(null) &&
                fa2.equals(fa1) &&
                fa1.equals(fa2) &&
                fa1.equals(fa3) &&
                !fa1.equals(fa4) &&
                !fa1.equals(fa5) &&
                !fa1.equals(fp1) &&
                fp1.equals(fp1) &&
                !fp2.equals(null) &&
                fp2.equals(fp1) &&
                fp1.equals(fp2) &&
                fp1.equals(fp3) &&
                !fp1.equals(fp4) &&
                !fp1.equals(fp5) &&
                !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) &&
                !b1.equals(null) &&
                !b1.equals(b2) &&
                !b2.equals(b1) &&
                !b1.equals(b3) &&
                !b3.equals(b1) &&
                !b1.equals(b4) &&
                b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        long from_id = a2.getId();
        long to_id = a3.getId();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, 3.0, 3.0);
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(), parseAmount(jin.nextLine()));
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    double amount = parseAmount(jin.nextLine());
                    double parameter = parseAmount(jin.nextLine());
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + String.format("%.2f$", t.getAmount()));
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions: " + String.format("%.2f$", bank.totalProvision()));
                    System.out.println("Total transfers: " + String.format("%.2f$", bank.totalTransfers()));
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, double amount, double o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, (int) o);
        }
        return null;
    }


}

