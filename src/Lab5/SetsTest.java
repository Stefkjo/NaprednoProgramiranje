package Lab5;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class IndexAlreadyExistsException extends Exception {
    public IndexAlreadyExistsException(String message) {
        super(message);
    }
}

class Student {
    private String id;
    private List<Integer> grades;
    private int maxGrade;

    public Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    public String getId() {
        return id;
    }

    public int getMaxGrade() {
        return this.maxGrade;
    }

    public void addGrade(int grade) {
        grades.add(grade);
        this.maxGrade = grades.stream()
                .mapToInt(i -> i)
                .summaryStatistics()
                .getMax();
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public int passesCourses() {
        return grades.stream()
                .mapToInt(i -> i)
                .filter(i -> i >= 6)
                .sum();
    }

    public double avgGrade() {
        return grades.stream()
                .mapToInt(i -> i)
                .average()
                .orElse(5.0);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", grades=" + grades +
                ", maxGrade=" + maxGrade +
                '}';
    }
}

class Faculty {
    private Map<String, Student> students;

    public Faculty() {
        students = new TreeMap<>();
    }

    public void addStudent(String id, List<Integer> grades) throws IndexAlreadyExistsException {
        if (!students.containsKey(id)) {
            students.put(id, new Student(id, grades));
        } else {
            throw new IndexAlreadyExistsException(String.format("Student with ID %s already exists", id));
        }
    }

    public void addGrade(String id, int grade) {
        students.get(id).addGrade(grade);
    }

    public Set<Student> getStudentsSortedByAverageGrade() {

        Comparator<Student> compareByAvgGradeAndPassedCoursesAndId =
                Comparator.comparing(Student::avgGrade)
                        .thenComparing(Student::passesCourses)
                        .thenComparing(Student::getId);

        return students.values()
                .stream()
                .sorted(compareByAvgGradeAndPassedCoursesAndId.reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<Student> getStudentsSortedByCoursesPassed() {

        Comparator<Student> compareByPassedCoursesAndAvgGradeAndId =
                Comparator.comparing(Student::passesCourses)
                        .thenComparing(Student::avgGrade)
                        .thenComparing(Student::getId);

        return students.values()
                .stream()
                .sorted(compareByPassedCoursesAndAvgGradeAndId.reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));

    }

    public Set<Student> getStudentsSortedByMaxGrade() {
        Comparator<Student> compareByMaxGradeAvgGradeId =
                Comparator.comparing(Student::getMaxGrade)
                        .thenComparing(Student::avgGrade)
                        .thenComparing(Student::getId);

        return students.values()
                .stream()
                .sorted(compareByMaxGradeAvgGradeId.reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}

public class SetsTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] tokens = input.split("\\s+");
            String command = tokens[0];

            switch (command) {
                case "addStudent":
                    String id = tokens[1];
                    List<Integer> grades = new ArrayList<>();
                    for (int i = 2; i < tokens.length; i++) {
                        grades.add(Integer.parseInt(tokens[i]));
                    }
                    try {
                        faculty.addStudent(id, grades);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "addGrade":
                    String studentId = tokens[1];
                    int grade = Integer.parseInt(tokens[2]);
                    faculty.addGrade(studentId, grade);
                    break;

                case "getStudentsSortedByAverageGrade":
                    System.out.println("Sorting students by average grade");
                    Set<Student> sortedByAverage = faculty.getStudentsSortedByAverageGrade();
                    for (Student student : sortedByAverage) {
                        System.out.println(student);
                    }
                    break;

                case "getStudentsSortedByCoursesPassed":
                    System.out.println("Sorting students by courses passed");
                    Set<Student> sortedByCourses = faculty.getStudentsSortedByCoursesPassed();
                    for (Student student : sortedByCourses) {
                        System.out.println(student);
                    }
                    break;

                default:
                    break;
            }
        }

        scanner.close();
    }
}

