package Auditoriska_8;

import java.util.Collection;

public class CountTest {

    public static int count_1(Collection<Collection<String>> c, String str){
        int counter = 0;
        for(Collection<String> collection:c){
            for(String element:collection){
                if(element.equals(str)){
                    counter++;
                }
            }
        }
        return counter;
    }

    public static int count2 (Collection<Collection<String>> c, String str){
        return (int) c.stream()
                .flatMap(Collection::stream)
                .filter(string -> string.equals(str))
                .count();
    }

    public static void main(String[] args) {


    }
}
