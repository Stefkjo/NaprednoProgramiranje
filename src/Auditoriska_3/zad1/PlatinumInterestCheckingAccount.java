package Auditoriska_3.zad1;

public class PlatinumInterestCheckingAccount extends InterestCheckingAccount{

    public PlatinumInterestCheckingAccount(String name) {
        super(name);
    }

    public void addInterest(){
        addToBalance((int) (getBalance()/((INTEREST_RATE)*2)/100));
    }

}
