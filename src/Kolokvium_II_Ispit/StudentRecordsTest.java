package Kolokvium_II_Ispit;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Stu {
    String code;
    String direction;
    List<Integer> grades;

    public Stu(String code, String direction) {
        this.code = code;
        this.direction = direction;
        this.grades = new ArrayList<>();
    }

    public void addGrades(List<Integer> grades) {
        this.grades = grades;
    }

    public double getAverageGrade() {
        return grades.stream()
                .mapToInt(i -> i)
                .average().orElse(0.0);
    }

    public String getCode() {
        return code;
    }

    public String getDirection() {
        return direction;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f\n", code, getAverageGrade());
    }
}

class StudentRecords {
    private Map<String, List<Stu>> directions;
    private Map<String, List<Integer>> grades;

    public StudentRecords() {
        directions = new TreeMap<>();
        grades = new HashMap<>();
    }

    private final Comparator<Stu> compareByAverageGradeAndIndex
            = Comparator.comparing(Stu::getAverageGrade, Comparator.reverseOrder())
            .thenComparing(Stu::getCode);

    public int readRecords(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        AtomicInteger counter = new AtomicInteger(0);
        br.lines()
                .forEach(line -> {
                    counter.getAndIncrement();
                    String[] parts = line.split("\\s+");
                    String index = parts[0];
                    String direction = parts[1];
                    Stu student = new Stu(index, direction);
                    List<Integer> studentGrades = new ArrayList<>();
                    for (int i = 2; i < parts.length; i++) {
                        studentGrades.add(Integer.parseInt(parts[i]));
                    }
                    student.addGrades(studentGrades);
                    if (!directions.containsKey(direction)) {
                        directions.put(direction, new ArrayList<>());
                        directions.get(direction).add(student);
                    } else {
                        directions.get(direction).add(student);
                    }
                    if (!grades.containsKey(direction)) {
                        grades.put(direction, new ArrayList<>());
                        grades.get(direction).addAll(studentGrades);
                    } else {
                        grades.get(direction).addAll(studentGrades);
                    }
                });
        return counter.get();
    }

    public void writeTable(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        StringBuilder sb = new StringBuilder();

        directions.entrySet()
                .stream()
                .forEach(entry -> {
                    sb.append(entry.getKey());
                    sb.append("\n");
                    List<Stu> students = new ArrayList<>(entry.getValue());
                    students.stream()
                            .sorted(compareByAverageGradeAndIndex)
                            .forEach(sb::append);
                });

        pw.write(sb.toString());
        pw.flush();
    }

    private LinkedHashMap<String, List<Integer>> sortByGrades(){
        return grades.entrySet()
                .stream()
                .sorted((a,b)->{
                    long count1= a.getValue().stream().filter(grade -> grade==10).count();
                    long count2 = b.getValue().stream().filter(grade -> grade==10).count();
                    return Long.compare(count2, count1);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
    public void writeDistribution(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);


        Map<String, List<Integer>> sorted = sortByGrades();
        sorted.entrySet()
                .forEach(entry -> {
                    pw.write(entry.getKey());
                    pw.write("\n");
                    Map<Integer, Integer> groupByGrades = new HashMap<>();
                    for (int i = 6; i <= 10; i++) {
                        groupByGrades.put(i, 0);
                    }
                    List<Integer> dirGrades = entry.getValue();
                    dirGrades.forEach(grade ->{
                        groupByGrades.computeIfPresent(grade, (k,v) -> v+1);
                    });
                    groupByGrades.entrySet()
                                    .stream()
                                            .forEach(entryGrade->{
                                                int numOfStars;
                                                if(entryGrade.getValue()%10!=0){
                                                        numOfStars = entryGrade.getValue()/10+1;
                                                }else{
                                                        numOfStars = entryGrade.getValue()/10;
                                                }

                                                pw.write(String.format("%2d | ", entryGrade.getKey()));
                                                for(int i = 0;i<numOfStars;i++){
                                                    pw.write("*");
                                                }
                                                pw.write(String.format("(%d)\n", entryGrade.getValue()));
                                            });
                });
        pw.flush();
    }
}

public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}
