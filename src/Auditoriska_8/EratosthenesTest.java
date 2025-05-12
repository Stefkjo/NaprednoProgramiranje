package Auditoriska_8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EratosthenesTest {
    public static boolean isPrime(int num){
        for(int i = 2;i<num;i++){
            if(num%i==0){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {

        ArrayList<Integer> ints = (ArrayList<Integer>) IntStream.range(2,101)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        for(int i = 0;i<ints.size();i++){
            if(isPrime(ints.get(i))){
                for(int j = i+1;j<ints.size();j++){
                    if(ints.get(j)%ints.get(i)==0){
                        ints.remove(j);
                        --j;
                    }
                }
            }
        }

        System.out.println(ints);
    }
}
