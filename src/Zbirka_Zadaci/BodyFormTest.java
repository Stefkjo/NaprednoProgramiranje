package Zbirka_Zadaci;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Person{
    String name;
    List<Float> measurements;
    float max;
    float sum;
    float avg;

    public float getMax() {
        return max;
    }

    public float getSum() {
        return sum;
    }

    public float getAvg() {
        return avg;
    }



    public Person(String name){
         this.name = name;
         measurements = new ArrayList<>();
         max = Float.MIN_VALUE;
         sum = 0;
         avg = 0;
    }

    public void addMeasurement(float newM){
        measurements.add(newM);
        sum+=newM;
        if(newM>max){
            max = newM;
        }
        avg = sum / measurements.size();
    }

    @Override
    public String toString(){
        return String.format("%s Max: %.1f kg, Avg: %.1f kg\n", name, max, avg);
    }
}

class BodyForm{
    List<Person> persons;

    public BodyForm(){
        persons = new ArrayList<>();
    }

    final Comparator<Person> maxWeight = Comparator.comparing(Person::getMax);
    final Comparator<Person> avgWeight = Comparator.comparing(Person::getAvg).reversed();


    void readData(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()){
            String line  = scanner.nextLine();
            String [] parts = line.split("\\s+");
            String name = parts[0];
            Person person = new Person(name);
            for(int i = 1;i<parts.length;i++){
                person.addMeasurement(Float.parseFloat(parts[i]));
            }
            persons.add(person);
        }
    }

    public void printByWeight(OutputStream outputStream, int type){
        PrintWriter pw = new PrintWriter(outputStream);
        if(type==1){
            persons.sort(maxWeight);
        }else{
            persons.sort(avgWeight);
        }

        for (Person person : persons) {
            pw.write(person.toString());
        }

        pw.flush();
    }

}

public class BodyFormTest {
    public static void main(String[] args) {
        BodyForm bodyForm = new BodyForm();
        bodyForm.readData(System.in);
        System.out.println("BY MAX");
        bodyForm.printByWeight(System.out, 1);
        System.out.println("BY AVG");
        bodyForm.printByWeight(System.out,2);

    }
}
