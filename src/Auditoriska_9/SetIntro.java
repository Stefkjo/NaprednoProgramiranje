package Auditoriska_9;

import java.util.*;

public class SetIntro {
    public static void main(String[] args) {

        //unikatni elementi koi se sortirani
        Set<Integer> treeIntSet = new TreeSet<>();
        for(int i =1;i<=10;i++){
            treeIntSet.add(i);
        }
        System.out.println(treeIntSet);


        Set<Integer> hashIntSet = new HashSet<>();
        for(int i =1;i<=10;i++){
            hashIntSet.add(i);
        }
        System.out.println(hashIntSet);

        Map<Integer,String> intMap = new HashMap<>();
        for(int i =0;i<10;i++){
            intMap.putIfAbsent(i, "t"+i);
        }
        intMap.forEach((k,v) -> System.out.println(String.format("Key: %d, Value: %s", k,v)));
        intMap.computeIfPresent(3,(k,v) -> v+v);
        System.out.println(intMap.get(3));
    }
}
