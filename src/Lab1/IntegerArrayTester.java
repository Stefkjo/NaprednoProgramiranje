package Lab1;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

final class IntegerArray {
    private int[] array;

    public IntegerArray(int[] a) {
        array = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            this.array[i] = a[i];
        }
    }

    public int length() {
        return this.array.length;
    }

    public int getElementAt(int i) {
        return array[i];
    }

    public int sum() {
        int s = 0;
        for (int i = 0; i < array.length; i++) {
            s += array[i];
        }
        return s;
    }

    public double average() {
        return (double) this.sum() / array.length;
    }

    public IntegerArray getSorted() {
        IntegerArray sorted = new IntegerArray(this.array);
        Arrays.sort(sorted.array);
        return sorted;
    }

    public IntegerArray concat(IntegerArray ia) {
        int[] sorted = new int[this.array.length + ia.array.length];
        for (int i = 0; i < array.length; i++) {
            sorted[i] = array[i];
        }
        for (int i = 0; i < ia.array.length; i++) {
            sorted[array.length + i] = ia.array[i];
        }

        return new IntegerArray(sorted);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < array.length - 1; i++) {
            builder.append(i).append(", ");
        }
        builder.append(this.array[array.length - 1]).append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof IntegerArray)) return false;
        IntegerArray other = (IntegerArray) obj;
        if(this.array.length!=other.array.length) return false;
        for(int i = 0;i<array.length;i++){
            if (this.array[i]!=other.array[i]){
                return false;
            }
        }
        return true;
    }
}


public class IntegerArrayTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(scanner.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcat":
                testConcat(scanner);
                break;
            case "testEquals":
                testEquals(scanner);
                break;
            case "testSorting":
                testSorting(scanner);
                break;
            case "testReading":
                testReading(new ByteArrayInputStream(scanner.nextLine().getBytes()));
                break;
            case "testImmutability":
                int a[] = generateRandomArray(scanner.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
                //ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArrayToString(ia).getBytes()));
                testSimpleMethods(ia);
                break;
        }
        scanner.close();
    }

    static void testReading(InputStream in) {
        //IntegerArray read = ArrayReader.readIntegerArray(in);
        //System.out.println(read);
    }

    static void testSorting(Scanner scanner) {
        int[] a = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        System.out.println(ia.getSorted());
    }

    static void testEquals(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        int[] c = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        IntegerArray ib = new IntegerArray(b);
        IntegerArray ic = new IntegerArray(c);
        System.out.println(ia.equals(ib));
        System.out.println(ia.equals(ic));
        System.out.println(ib.equals(ic));
    }

    static void testConcat(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        IntegerArray array1 = new IntegerArray(a);
        IntegerArray array2 = new IntegerArray(b);
        IntegerArray concatenated = array1.concat(array2);
        System.out.println(concatenated);
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArrayToString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }


    static String integerArrayToString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i)
            sb.append(ia.getElementAt(i)).append(' ');
        sb.append('\n');
        return sb.toString();
    }

    static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = scanner.nextInt();
        }
        return a;
    }


    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }

}


