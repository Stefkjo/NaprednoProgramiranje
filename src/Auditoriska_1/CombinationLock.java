package Auditoriska_1;

public class CombinationLock {
    private int combination;
    boolean locked;

    public CombinationLock(int combination){
        this.combination = combination;
        this.locked = true;
    }

    public void open(int number){
        if(number==combination){
            this.locked = false;
        }
    }

    public void changeCombo(int oldC, int newC){
        if(oldC == combination){
            this.combination = newC;
        }
        else {
            System.out.println("Wrong combination.");
        }
    }

    public static void main(String[] args) {

        CombinationLock a = new CombinationLock(123);
        System.out.println(a.locked);
        a.open(123);
        System.out.println(a.locked);
        a.changeCombo(123, 321);
        System.out.println(a.combination);
    }
}
