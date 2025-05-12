package Lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;


class IntegerList {
    private List<Integer> numbers;

    public IntegerList() {
        this.numbers = new ArrayList<>();
    }

    public IntegerList(Integer[] numbers) {
        this.numbers = new ArrayList<>(List.of(numbers));
    }

    private boolean checkIdx(int idx){
        return idx <= numbers.size() && idx >= 0;
    }

    public void add(int el, int idx) {
        if (idx > numbers.size()) {
            int n = idx - numbers.size();
            for (int i = 0; i < n; i++) {
                numbers.add(0);
            }
        }
        numbers.add(idx, el);
    }

    public int remove(int idx){
        if (!checkIdx(idx)){
            throw new ArrayIndexOutOfBoundsException("Out of bounds");
        }
        return numbers.remove(idx);
    }

    public void set(int el, int idx){
        if (!checkIdx(idx)){
            throw new ArrayIndexOutOfBoundsException("Out of bounds");
        }
        numbers.set(idx, el);
    }

    public int get(int idx){
        if (!checkIdx(idx)){
            throw new ArrayIndexOutOfBoundsException("Out of bounds");
        }
        return numbers.get(idx);
    }

    public int size(){
        return numbers.size();
    }

    public int count(int el){
        return (int) numbers.stream().mapToInt(i -> i)
                .filter(i -> i==el)
                .count();
    }

    public void removeDuplicates(){
        for(int i = numbers.size()-1;i>=0;i--){
            for(int j = 0;j<numbers.size();j++){
                if(Objects.equals(numbers.get(i), numbers.get(j))){
                    numbers.remove(j);
                }
            }
        }
    }

    public int sumFirst(int k){
        return numbers.stream()
                .mapToInt(i ->i)
                .limit(k)
                .sum();
    }

    public int sumLast(int k){
        int sum = 0;
        for(int i = numbers.size()-1;i>=k;i--){
            sum+=numbers.get(i);
        }
        return sum;
    }

    public void shiftRight(int idx, int k){
        if (!checkIdx(idx)){
            throw new ArrayIndexOutOfBoundsException("Out of bounds");
        }
        int element = numbers.remove(idx);
        int nexIdx = (idx+k)%numbers.size();
        numbers.add(nexIdx, element);
    }

    public void shiftLeft(int idx, int k){
        if (!checkIdx(idx)){
            throw new ArrayIndexOutOfBoundsException("Out of bounds");
        }
        int element = numbers.remove(idx);
        int nexIdx = ((idx-k)%numbers.size()+numbers.size())%numbers.size();
        numbers.add(nexIdx, element);
    }

    public IntegerList addValue(int value){

        return new IntegerList(numbers.stream()
                .mapToInt(i -> i + value)
                .boxed()
                .collect(Collectors.toUnmodifiableList())
                .toArray(new Integer[0]));

    }

}

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test standard methods
            int subtest = jin.nextInt();
            if (subtest == 0) {
                IntegerList list = new IntegerList();
                while (true) {
                    int num = jin.nextInt();
                    if (num == 0) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if (num == 1) {
                        list.remove(jin.nextInt());
                    }
                    if (num == 2) {
                        print(list);
                    }
                    if (num == 3) {
                        break;
                    }
                }
            }
            if (subtest == 1) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for (int i = 0; i < n; ++i) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if (k == 1) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if (num == 1) {
                    list.removeDuplicates();
                }
                if (num == 2) {
                    print(list.addValue(jin.nextInt()));
                }
                if (num == 3) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
        if (k == 2) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if (num == 1) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if (num == 2) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if (num == 3) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if (il.size() == 0) System.out.print("EMPTY");
        for (int i = 0; i < il.size(); ++i) {
            if (i > 0) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}