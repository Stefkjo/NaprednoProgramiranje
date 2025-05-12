package Kolokvium_II_Ispit;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class StudentOfFINKI{
    String index;
    Map<String, Integer> expenses;
    public StudentOfFINKI(String index){
        this.index = index;
        expenses = new TreeMap<>(Comparator.reverseOrder());
    }

    public void addExpense(String expense, int price){
        expenses.putIfAbsent(expense, price);
    }

    public int getSumCost(){
        return expenses.values()
                .stream()
                .mapToInt(i -> i)
                .sum();
    }

    public int getFee(){
        if( (int) Math.round((1.14/100)*getSumCost()) > 300){
            return 300;
        }
        return Math.max((int) Math.round((1.14 / 100) * getSumCost()), 3);
    }


    @Override
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append(String.format("Student: %s Net: %d Fee: %d Total: %d\n",index, getSumCost(), getFee(), getSumCost()+getFee()));
        sb.append("Items:\n");
        AtomicInteger counter = new AtomicInteger(1);
        expenses.entrySet()
                .stream()
                .sorted((a,b) -> b.getValue()-a.getValue())
                .forEach(entry -> sb.append(String.format("%d. %s %d\n",counter.getAndIncrement(),entry.getKey(), entry.getValue())));

        return sb.toString();
    }

}

class OnlinePayments{
    Map<String, StudentOfFINKI> students;

    public OnlinePayments(){
        students = new HashMap<>();
    }

    public void readItems(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines()
                .forEach(line->{
                    String []parts = line.split(";");
                    if(!students.containsKey(parts[0])){
                        StudentOfFINKI student = new StudentOfFINKI(parts[0]);
                        student.addExpense(parts[1], Integer.parseInt(parts[2]));
                        students.put(parts[0], student);
                    }else{
                        StudentOfFINKI existing = students.get(parts[0]);
                        existing.addExpense(parts[1], Integer.parseInt(parts[2]));
                    }
                });
    }

    public void printStudentReport(String index, OutputStream os){
        PrintWriter pw = new PrintWriter(os);

        if(!students.containsKey(index)){
            pw.write(String.format("Student %s not found!\n", index));
            pw.flush();
            return;
        }

        pw.write(students.get(index).toString());
        pw.flush();
    }
}

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}
