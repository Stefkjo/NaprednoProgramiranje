//package Kolokvium_II_Ispit;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class Student {
//    private String index;
//    List<Integer> labPoints;
//    static final int COUNT_OF_LAB_EX = 10;
//
//    public Student(String index, List<Integer> labPoints) {
//        this.index = index;
//        this.labPoints = labPoints;
//    }
//
//    public String getIndex() {
//        return index;
//    }
//
//    public List<Integer> getLabPoints() {
//        return labPoints;
//    }
//
//    public double getAveragePoints() {
//        return labPoints.stream()
//                .mapToInt(point -> Integer.parseInt(point.toString()))
//                .sum() / (double) COUNT_OF_LAB_EX;
//    }
//
//    public boolean hasSignature() {
//        return labPoints.size() >= 8;
//    }
//
//    public int getYearOfStudies(){
//       return 2021 - Integer.parseInt(index.substring(0,2));
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %s %.2f", index, hasSignature() ? "YES" : "NO", getAveragePoints());
//    }
//}
//
//class LabExercises {
//    private List<Student> students;
//
//    public LabExercises() {
//        students = new ArrayList<>();
//    }
//
//    public void addStudent(Student student) {
//        students.add(student);
//    }
//
//    public void printByAveragePoints(boolean ascending, int n) {
//        Comparator<Student> compareByAvgAndIndex =
//                Comparator.comparing(Student::getAveragePoints)
//                .thenComparing(Student::getIndex);
//
//        if (!ascending) {
//            compareByAvgAndIndex = compareByAvgAndIndex.reversed();
//        }
//        students.stream()
//                .sorted(compareByAvgAndIndex)
//                .limit(n)
//                .forEach(System.out::println);
//    }
//
//    public List<Student> failedStudents(){
//        Comparator<Student> compareByIndexAndAvg =
//                Comparator.comparing(Student::getIndex)
//                .thenComparing(Student::getAveragePoints);
//
//        return students.stream()
//                .filter(s -> !s.hasSignature())
//                .sorted(compareByIndexAndAvg)
//                .collect(Collectors.toUnmodifiableList());
//    }
//
//    public Map<Integer, Double> getStatisticsByYear(){
//       return students.stream()
//               .filter(Student::hasSignature)
//                .collect(Collectors.groupingBy(
//                        Student::getYearOfStudies,
//                        Collectors.averagingDouble(Student::getAveragePoints)
//                ));
//    }
//}
//
//public class LabExercisesTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        LabExercises labExercises = new LabExercises();
//        while (sc.hasNextLine()) {
//            String input = sc.nextLine();
//            String[] parts = input.split("\\s+");
//            String index = parts[0];
//            List<Integer> points = Arrays.stream(parts).skip(1)
//                    .mapToInt(Integer::parseInt)
//                    .boxed()
//                    .collect(Collectors.toList());
//
//            labExercises.addStudent(new Student(index, points));
//        }
//
//        System.out.println("===printByAveragePoints (ascending)===");
//        labExercises.printByAveragePoints(true, 100);
//        System.out.println("===printByAveragePoints (descending)===");
//        labExercises.printByAveragePoints(false, 100);
//        System.out.println("===failed students===");
//        labExercises.failedStudents().forEach(System.out::println);
//        System.out.println("===statistics by year");
//        labExercises.getStatisticsByYear().entrySet().stream()
//                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
//                .forEach(System.out::println);
//
//    }
//}
//
