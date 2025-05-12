package Auditoriska_8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ReverseListTest {
    public static <T> void reversePrint_1(Collection<T> collection){
        List<T> list = new ArrayList<>(collection);

        for(int i = list.size()-1;i>0;i--){
            System.out.println(list.get(i));
        }
    }

    public static <T> void reversePrint_2(Collection<T> collection){
        List<T> list = new ArrayList<>(collection);
        Collections.reverse(list);
        System.out.println(list);
    }
    public static void main(String[] args) {

        List<Integer> ints = List.of(1,2,3,4,5,6,7);
        reversePrint_1(ints);

        List<String> strings = List.of("123","123","321","456","678");
        reversePrint_2(strings);
    }
}
