package Auditoriska_3.zad1;

public class InterestCheckingAccount extends Account implements InterestBearingAccount{

    public InterestCheckingAccount(String name) {
        super(name);
    }

    static float INTEREST_RATE = 3;
    @Override
    public void addInterest() {
    addToBalance((int) ((float)getBalance()/(INTEREST_RATE/100)));
    }

    public static void changeInterestRate(float newIR){
        INTEREST_RATE = newIR;
    }

    public static float getIR(){
        return INTEREST_RATE;
    }

}
