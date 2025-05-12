package Auditoriska_4.zad1;

import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;

class LineConsumer implements Consumer<String> {

    int l = 0, w = 0, c = 0;

    @Override
    public void accept(String s) {
        l++;
        w+=s.split("\\s+").length;
        c+=s.length();
    }

    public static void count2(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        LineConsumer consumer = new LineConsumer();
        reader.lines().forEach(consumer);
        System.out.println(consumer);
    }

    @Override
    public String toString(){
        return String.format("Lines in file: %d \nWords in file: %d\nCharacters in file: %d ", l, w, c);
    }
}

class WordCount {
    public static void count1(InputStream is) {
        Scanner input = new Scanner(is);
        int lines = 0, words = 0, characters = 0;

        while (input.hasNextLine()) {
            String line = input.nextLine();
            ++lines;
            String[] word = line.split("\\s+");
            words += word.length;
            characters += line.length();
        }

        System.out.printf("Lines in file: %d \nWords in file: %d\nCharacters in file: %d ", lines, words, characters);
    }
}

public class WordCountTest {

    public static void main(String[] args) throws IOException {

        InputStream inputStream = new FileInputStream(new File("C:\\Users\\sstoj\\Desktop\\NaprednoProgramiranje\\src\\Auditoriska_4\\zad1\\word.txt.txt"));

        //WordCount.count1(inputStream);
        //System.out.println("\n");
        LineConsumer.count2(inputStream);
    }
}
