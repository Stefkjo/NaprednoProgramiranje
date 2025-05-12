package Kolokvium_II_Ispit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


class QuizProcessor {
    public static Map<String, Double> processAnswers(InputStream is) {
        Map<String, Double> students = new LinkedHashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(line -> {
            String[] parts = line.split(";");
            String index = parts[0];
            String[] questions = parts[1].split(", ");
            String[] answers = parts[2].split(", ");
            if(answers.length!=questions.length){
                System.out.println("A quiz must have same number of correct and selected answers");
                return;
            }
            int correct = 0;
            int incorrect = 0;
            for (int i = 0; i < questions.length; i++) {
                if(questions[i].equals(answers[i])){
                    correct++;
                }else{
                    incorrect++;
                }
            }

            double points = correct - incorrect*0.25;
            students.put(index, points);
        });
        return students;
    }
}

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}