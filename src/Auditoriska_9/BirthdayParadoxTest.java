package Auditoriska_9;

import java.util.HashSet;
import java.util.Random;

class BirthdayParadox{
    int maxPerson;
    static int trials = 5000;
    public BirthdayParadox(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    public void conductExperimnet() {
        for(int i = 2;i<=maxPerson;i++){
            System.out.println(String.format("%d --> %.5f", i, runSimulation(i)));
        }
    }

    public float runSimulation(int people){
        int counter = 0;
        Random random = new Random();
        for(int i = 0;i<trials;i++){
            if(runTrial(people, random)){
                ++counter;
            }
        }
        return (float) counter /trials;
    }

    public boolean runTrial(int people, Random random){
        HashSet<Integer> birthdays = new HashSet<>();
        for(int i = 0;i<people;i++){
            int birthday = random.nextInt(0,365)+1;
            if (birthdays.contains(birthday)){
                return true;
            }else{
                birthdays.add(birthday);
            }
        }
        return false;
    }
}

public class BirthdayParadoxTest {
    public static void main(String[] args) {
        BirthdayParadox bp = new BirthdayParadox(100);
        bp.conductExperimnet();

    }
}
