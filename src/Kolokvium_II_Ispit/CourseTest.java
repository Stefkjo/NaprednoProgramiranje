package Kolokvium_II_Ispit;

import java.util.*;
import java.util.stream.Collectors;

class Student {
    String index;
    String name;
    int midTerm1Points;
    int midTerm2Points;
    int labPoints;

    public Student(String index, String name) {
        this.index = index;
        this.name = name;
        this.midTerm1Points = 0;
        this.midTerm2Points = 0;
        this.labPoints = 0;
    }

    public double getTotalPoints() {
        return midTerm2Points * 0.45 + midTerm1Points * 0.45 + labPoints;
    }

    public int getGrade() {
        if(getTotalPoints()<50){
            return 5;
        }else if(getTotalPoints()>=50 && getTotalPoints()<=59.99){
            return 6;
        }else if(getTotalPoints()>=60 && getTotalPoints()<=69.99){
            return 7;
        }else if(getTotalPoints()>=70 && getTotalPoints()<=79.99){
            return 8;
        }else if(getTotalPoints()>=80 && getTotalPoints()<=89.99){
            return 9;
        }else if(getTotalPoints()>=90 && getTotalPoints()<=100){
            return 10;
        }else{
            return 0;
        }

    }

    public void updatePoints(String activity, int points) {
        activity = activity.toLowerCase();
        if (points < 0 || points > 100) {
            System.out.println("Invalid points");
        }
        if (activity.equals("midterm1")) {
            this.midTerm1Points = points;
        } else if (activity.equals("midterm2")) {
            this.midTerm2Points = points;
        } else if (activity.equals("labs")){
            this.labPoints = points;
        }

    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d"
                , index, name, midTerm1Points, midTerm2Points, labPoints, getTotalPoints(), getGrade());
    }
}

class AdvancedProgrammingCourse {
    Map<String, Student> students;

    public AdvancedProgrammingCourse() {
        students = new HashMap<>();
    }

    public void addStudent(Student s) {
        students.put(s.index, s);
    }

    public void updateStudent(String index, String activity, int points) {
        Student picked = students.get(index);
        picked.updatePoints(activity, points);
    }

    private final Comparator<Student> compareByTotalPoint
            = Comparator.comparing(Student::getTotalPoints).reversed();

    public List<Student> getFirstNStudents(int n) {
        return students.values()
                .stream()
                .sorted(compareByTotalPoint)
                .limit(n)
                .collect(Collectors.toUnmodifiableList());
    }

    public Map<Integer, Integer> getGradeDistribution() {

        Map<Integer, Integer> groupByGrades = new HashMap<>();
        for(int i = 5;i<=10;i++){
            groupByGrades.put(i, 0);
        }
        students.values()
                .stream()
                .mapToInt(Student::getGrade)
                .forEach(grade ->{
                        groupByGrades.computeIfPresent(grade, (k,v) -> v+1);
                });

        return groupByGrades;


//        return students.values()
//                .stream().collect(Collectors.groupingBy(
//                        Student::getGrade,
//                        TreeMap::new,
//                        Collectors.summingInt(s -> 1)
//                ));
    }

    public void printStatistics() {
        DoubleSummaryStatistics results = students.values()
                .stream()
                .filter(student -> student.getGrade() > 5)
                .mapToDouble(Student::getTotalPoints)
                .summaryStatistics();

        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f\n", results.getCount(), results.getMin(), results.getAverage(), results.getMax());
    }

}

public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}
