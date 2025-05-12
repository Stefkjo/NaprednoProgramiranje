//package Kolokvium_II_Ispit;
//
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class OperationNotAllowedException extends Exception {
//    public OperationNotAllowedException(String message) {
//        super(message);
//    }
//}
//
//class InvalidInputException extends Exception {
//
//}
//
//abstract class StudentOfStudies {
//    String index;
//    Map<Integer, List<Integer>> grades;
//    Set<String> courses;
//
//    public StudentOfStudies(String index) {
//        this.index = index;
//        grades = new TreeMap<>();
//        courses = new TreeSet<>();
//    }
//
//    public double getAverageGrade() {
//        return grades.values()
//                .stream()
//                .flatMap(Collection::stream)
//                .mapToInt(i -> i)
//                .average()
//                .orElse(5.0);
//    }
//
//    public double getAverageGradeForTerm(int term) {
//        return grades.get(term)
//                .stream()
//                .mapToInt(i -> i)
//                .average()
//                .orElse(5.0);
//    }
//
//    public abstract boolean addGrade(int term, String courseName, int grade) throws InvalidInputException, OperationNotAllowedException;
//
//    public void validateTerm(int term) throws OperationNotAllowedException {
//        if (!grades.containsKey(term)) {
//            throw new OperationNotAllowedException(String.format("Term %d is not possible for student with ID %s", term, index));
//        }
//        if (grades.get(term).size() == 3) {
//            throw new OperationNotAllowedException(String.format("Student %s already has 3 grades for this term %d", index, term));
//        }
//    }
//
//    public int coursesPassed() {
//        return grades.values()
//                .stream()
//                .mapToInt(List::size)
//                .sum();
//    }
//
//    public String getGraduationLog() {
//        return String.format("Student with ID %s graduated with average grade %.2f", index, getAverageGrade());
//    }
//
//    public String getTermReport(int term) {
//        return String.format("Term %d\nCourse: %d\nAverage: %.2f", term, grades.get(term).size(), getAverageGradeForTerm(term));
//    }
//
//    public String getDetailedReport() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("Student: %s\n", index));
//        grades.keySet()
//                .forEach(term -> {
//                    sb.append(getTermReport(term)).append("\n");
//                });
//        sb.append(String.format("Average grade: %.2f\nCourses: attended: %s", getAverageGrade(), String.join(",", courses)));
//
//        return sb.toString();
//    }
//
//    public String getShortReport() {
//        return String.format("Student: %s Courses passed: %d Average grade: %.2f", index, coursesPassed(), getAverageGrade());
//    }
//
//    public String getIndex() {
//        return index;
//    }
//
//}
//
//class StudentOfFourYearsStudies extends StudentOfStudies {
//
//    public StudentOfFourYearsStudies(String id) {
//        super(id);
//        IntStream.range(1, 9)
//                .forEach(i -> grades.putIfAbsent(i, new ArrayList<>()));
//    }
//
//    @Override
//    public boolean addGrade(int term, String courseName, int grade) throws OperationNotAllowedException {
//        validateTerm(term);
//        grades.get(term).add(grade);
//        courses.add(courseName);
//        return courses.size() == 24;
//    }
//
//    @Override
//    public String getGraduationLog() {
//        return super.getGraduationLog() + " in 4 years";
//    }
//
//}
//
//class StudentOfThreeYearsStudies extends StudentOfStudies {
//
//    public StudentOfThreeYearsStudies(String id) {
//        super(id);
//        IntStream.range(1, 7)
//                .forEach(i -> grades.putIfAbsent(i, new ArrayList<>()));
//    }
//
//    @Override
//    public boolean addGrade(int term, String courseName, int grade) throws OperationNotAllowedException {
//        validateTerm(term);
//        grades.get(term).add(grade);
//        courses.add(courseName);
//        return courses.size() == 18;
//    }
//
//    @Override
//    public String getGraduationLog() {
//        return super.getGraduationLog() + " in 3 years";
//    }
//}
//
//class Faculty {
//
//    public Faculty() {
//
//    }
//
//    void addStudent(String id, int yearsOfStudies) {
//    }
//
//    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
//
//    }
//
//    String getFacultyLogs() {
//        return "";
//    }
//
//    String getDetailedReportForStudent(String id) {
//        return "";
//    }
//
//    void printFirstNStudents(int n) {
//
//    }
//
//    void printCourses() {
//
//    }
//}
//
//public class FacultyTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int testCase = sc.nextInt();
//
//        if (testCase == 1) {
//            System.out.println("TESTING addStudent AND printFirstNStudents");
//            Faculty faculty = new Faculty();
//            for (int i = 0; i < 10; i++) {
//                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
//            }
//            faculty.printFirstNStudents(10);
//
//        } else if (testCase == 2) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            try {
//                faculty.addGradeToStudent("123", 7, "NP", 10);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//            try {
//                faculty.addGradeToStudent("1234", 9, "NP", 8);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        } else if (testCase == 3) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        } else if (testCase == 4) {
//            System.out.println("Testing addGrade for graduation");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            int counter = 1;
//            for (int i = 1; i <= 6; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            counter = 1;
//            for (int i = 1; i <= 8; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
//            faculty.printFirstNStudents(2);
//        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
//            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            if (testCase == 5)
//                faculty.printFirstNStudents(10);
//            else if (testCase == 6)
//                faculty.printFirstNStudents(3);
//            else
//                faculty.printFirstNStudents(20);
//        } else if (testCase == 8 || testCase == 9) {
//            System.out.println("TESTING DETAILED REPORT");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
//            int grade = 6;
//            int counterCounter = 1;
//            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
//                for (int j = 1; j < 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
//                    } catch (OperationNotAllowedException e) {
//                        e.printStackTrace();
//                    }
//                    grade++;
//                    if (grade == 10)
//                        grade = 5;
//                    ++counterCounter;
//                }
//            }
//            System.out.println(faculty.getDetailedReportForStudent("student1"));
//        } else if (testCase == 10) {
//            System.out.println("TESTING PRINT COURSES");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            faculty.printCourses();
//        } else if (testCase == 11) {
//            System.out.println("INTEGRATION TEST");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//
//            }
//
//            for (int i = 11; i < 15; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= 3; k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("DETAILED REPORT FOR STUDENT");
//            System.out.println(faculty.getDetailedReportForStudent("student2"));
//            try {
//                System.out.println(faculty.getDetailedReportForStudent("student11"));
//                System.out.println("The graduated students should be deleted!!!");
//            } catch (NullPointerException e) {
//                System.out.println("The graduated students are really deleted");
//            }
//            System.out.println("FIRST N STUDENTS");
//            faculty.printFirstNStudents(10);
//            System.out.println("COURSES");
//            faculty.printCourses();
//        }
//    }
//}
