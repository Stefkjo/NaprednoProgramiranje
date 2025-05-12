package Kolokvium_I;


import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Triple<K extends Number & Comparable<K>>{

    private K num1;
    private K num2;
    private K num3;

    public Triple(K num1, K num2, K num3){
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
    }

    public double max(){
        return Math.max(num1.doubleValue(), Math.max(num2.doubleValue(), num3.doubleValue()));
    }

    public double avarage(){
        return (num1.doubleValue()+num2.doubleValue()+num3.doubleValue())/3.0;
    }

    public void sort(){
        List<K> list = new ArrayList<>();
        list.add(num1);
        list.add(num2);
        list.add(num3);
        Collections.sort(list);
        num1 = list.get(0);
        num2 = list.get(1);
        num3 = list.get(2);
    }

    @Override
    public String toString(){
        return String.format("%2f %2f %2f", num1.doubleValue(), num2.doubleValue(), num3.doubleValue());
    }
}


public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple


