package Lab4;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

class SuperString {
    private LinkedList<String> string;

    public SuperString() {
        this.string = new LinkedList<>();
    }

    public void append(String s) {
        string.add(s);
    }

    public void insert(String s) {
        string.addFirst(s);
    }

    public boolean contains(String s) {
        StringBuilder sb = new StringBuilder();
        string.forEach(sb::append);
        String res = sb.toString();
        return res.contains(s);
    }

    public void reverse() {
        LinkedList<String> reversed = new LinkedList<>();
        for (int i = string.size() - 1; i >= 0; i--) {
            reversed.add(reverseWord(string.get(i)));
        }

        string = reversed;
    }

    public void removeLast(int k) {
        for (int i = 0; i < k; i++) {
            string.removeLast();
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        string.forEach(sb::append);
        return sb.toString();
    }

    private String reverseWord(String word) {
        StringBuilder res = new StringBuilder();
        for (int i = word.length() - 1; i >= 0; i--) {
            res.append(word.charAt(i));
        }
        return res.toString();
    }


}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            SuperString s = new SuperString();
            while (true) {
                int command = jin.nextInt();
                if (command == 0) {//append(String s)
                    s.append(jin.next());
                }
                if (command == 1) {//insert(String s)
                    s.insert(jin.next());
                }
                if (command == 2) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if (command == 3) {//reverse()
                    s.reverse();
                }
                if (command == 4) {//toString()
                    System.out.println(s);
                }
                if (command == 5) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if (command == 6) {//end
                    break;
                }
            }
        }
    }

}
