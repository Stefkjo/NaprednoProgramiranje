package Kolokvium_I;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Attack {

    List<Integer> attacker = new ArrayList<>();
    List<Integer> defender = new ArrayList<>();

    public int getDefendersSurvived() {
        return defendersSurvived;
    }

    public int getAttackersSurvived() {
        return attackersSurvived;
    }

    private int attackersSurvived = 0;
    private int defendersSurvived = 0;

    public Attack(String line) {
        String[] parts = line.split(";");
        String[] attackerRolls = parts[0].split("\\s+");
        String[] defenderRolls = parts[1].split("\\s+");

        attacker.add(Integer.parseInt(attackerRolls[0]));
        attacker.add(Integer.parseInt(attackerRolls[1]));
        attacker.add(Integer.parseInt(attackerRolls[2]));

        defender.add(Integer.parseInt(defenderRolls[0]));
        defender.add(Integer.parseInt(defenderRolls[1]));
        defender.add(Integer.parseInt(defenderRolls[2]));


    }

    public boolean isSuccsesful(){
        boolean success = true;
        attacker.sort(Comparator.naturalOrder());
        defender.sort(Comparator.naturalOrder());
        for (int i = 0;i<attacker.size();i++){
            if(attacker.get(i)<=defender.get(i)){
                success=false;
                break;
            }
        }
        return success;
    }
    public void calculateSurvivors() {

        attacker.sort(Comparator.comparingInt(a -> (int) a).reversed());
        defender.sort(Comparator.comparingInt(a -> (int) a).reversed());

        for (int i = 0; i < attacker.size(); i++) {
            if (attacker.get(i) > defender.get(i)) {
                attackersSurvived++;
            } else {
                defendersSurvived++;
            }
        }

    }

    @Override
    public String toString() {

        return attackersSurvived + " " + defendersSurvived;
    }
}

class Risk {

    public void processAttacksData(InputStream in) {

        List<Attack> attacks = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        attacks = br.lines()
                .map(line -> new Attack(line))
                .collect(Collectors.toUnmodifiableList());

        attacks.forEach(Attack::calculateSurvivors);

        for (Attack attack : attacks) {

            System.out.println(attack.getAttackersSurvived() + " "
                    + attack.getDefendersSurvived());

        }
    }

    public int proccessAttack(InputStream is){

        Integer succsesfulAttacks = 0;

        List<Attack> attacks = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        attacks = br.lines()
                .map(line -> new Attack(line))
                .collect(Collectors.toUnmodifiableList());

        for(Attack attack:attacks){
            if(attack.isSuccsesful()){
                succsesfulAttacks++;
            }
        }

        return succsesfulAttacks;
    }
}

public class RiskTester {
    public static void main(String[] args) {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}