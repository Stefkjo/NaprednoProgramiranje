package Kolokvium_II_Ispit;

import java.util.*;

class Names{
    private Map<String, Integer> names;

    public Names(){
        names = new TreeMap<>();
    }

    public void addName(String name){
        if(!names.containsKey(name)){
            names.put(name, 1);
        }else{
            names.computeIfPresent(name,(k,v) -> v + 1);
        }
    }

    public void printN(int n){
        names.entrySet().stream()
                .filter(entry -> entry.getValue()>=n)
                .forEach(entry -> System.out.printf("%s (%d) %d\n", entry.getKey(), entry.getValue(), distinctLetters(entry.getKey())));
    }

    public String findName(int len, int x){

        names.entrySet().removeIf(entry -> entry.getKey().length()>=len);
        List<String> leftNames = new ArrayList<>(names.keySet());

        int position = x%leftNames.size();
        return leftNames.get(position);

    }

    public int distinctLetters(String name){
        List<Character> letters = new ArrayList<>();
        name = name.toLowerCase();
        for(int i = 0;i<name.length();i++){
            if(!letters.contains(name.charAt(i))){
                letters.add(name.charAt(i));
            }
        }
        return letters.size();
    }


}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

