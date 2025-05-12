package Lab2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

enum OPERATOR {
    VIP,
    ONE,
    TMOBILE
}

abstract class Contact {

    private int year;
    private int month;
    private int day;

    public Contact(String date) {
        this.year = Integer.parseInt(date.split("-")[0]);
        this.month = Integer.parseInt(date.split("-")[1]);
        this.day = Integer.parseInt(date.split("-")[2]);
    }

    public boolean isNewerThan(Contact c) {
        if (this.year > c.year) {
            return true;
        } else if (this.year < c.year) {
            return false;
        } else if (this.month > c.month) {
            return true;
        } else if (this.month < c.month) {
            return false;
        } else if (this.day > c.day) {
            return true;
        } else if (this.day < c.day) {
            return false;
        } else {
            return false;
        }
    }

    public String getType() {
        return "Unknown";
    }
}

class EmailContact extends Contact {
    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getType() {
        return "Email";
    }
}

class PhoneContact extends Contact {
    private String phone;
    private OPERATOR operator;

    public PhoneContact(String date, String phone) {
        super(date);
        if (phone.split("/")[0].equals("070") || phone.split("/")[0].equals("071") || phone.split("/")[0].equals("072")) {
            this.operator = OPERATOR.TMOBILE;
        }
        if (phone.split("/")[0].equals("075") || phone.split("/")[0].equals("076")) {
            this.operator = OPERATOR.ONE;
        }
        if (phone.split("/")[0].equals("077") || phone.split("/")[0].equals("078")) {
            this.operator = OPERATOR.VIP;
        }
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public OPERATOR getOperator() {
        return operator;
    }

    @Override
    public String getType() {
        return "Phone";
    }

}

class Student {
    private List<Contact> contacts;
    private String firstName;
    private String lastName;
    private String city;
    private int age;
    private long index;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.index = index;
        this.city = city;
        contacts = new ArrayList<>();
    }

    public void addEmailContact(String date, String email) {
        EmailContact ec = new EmailContact(date, email);
        contacts.add(ec);
    }

    public void addPhoneContact(String date, String phone) {
        PhoneContact pc = new PhoneContact(date, phone);
        contacts.add(pc);
    }

    public int countPhones() {
        int counter = 0;
        for (Contact c : contacts) {
            if (c.getType().equals("Phone")) {
                counter++;
            }
        }
        return counter;
    }

    public int countEmails() {
        int counter = 0;
        for (Contact c : contacts) {
            if (c.getType().equals("Email")) {
                counter++;
            }
        }
        return counter;
    }

    public List<Contact> getEmailContacts() {
        List<Contact> eContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (Objects.equals(contact.getType(), "Email")) {
                eContacts.add(contact);
            }
        }
        return eContacts;
    }

    public List<Contact> getPhoneContacts() {
        List<Contact> pContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (Objects.equals(contact.getType(), "Phone")) {
                pContacts.add(contact);
            }
        }
        return pContacts;
    }

    public String getCity() {
        return city;
    }

    public long getIndex() {
        return index;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Contact getLatestContact() {
        if (contacts.isEmpty()){
            return null;
        }

        Contact newest = contacts.get(0);
        for(Contact contact:contacts){
            if(contact.isNewerThan(newest)){
                newest = contact;
            }
        }

        return newest;
    }

    @Override
    public String toString() {
        StringBuilder phoneContacts = new StringBuilder("[");
        for (Contact c : getPhoneContacts()) {
            phoneContacts.append("\"").append(((PhoneContact) c).getPhone()).append("\", ");
        }
        if (phoneContacts.length() > 1) {
            phoneContacts.setLength(phoneContacts.length() - 2);
        }
        phoneContacts.append("]");

        StringBuilder emailContacts = new StringBuilder("[");
        for (Contact c : getEmailContacts()) {
            emailContacts.append("\"").append(((EmailContact) c).getEmail()).append("\", ");
        }
        if (emailContacts.length() > 1) {
            emailContacts.setLength(emailContacts.length() - 2);
        }
        emailContacts.append("]");

        return String.format(
                "{\"ime\":\"%s\", \"prezime\":\"%s\", \"vozrast\":%d, \"grad\":\"%s\", \"indeks\":%d, \"telefonskiKontakti\":%s, \"emailKontakti\":%s}",
                firstName, lastName, age, city, index, phoneContacts, emailContacts
        );
    }

}

class Faculty {

    private String name;
    private List<Student> students;

    public Faculty(String name, Student[] students) {
        this.students = List.of(students);
        this.name = name;
    }

    public int countStudentsFromCity(String cityName) {
        int counter = 0;
        for (Student s : students) {
            if (s.getCity().equals(cityName)) {
                counter++;
            }
        }
        return counter;
    }

    public Student getStudent(long index) {
        Student s = null;
        for (Student student : students) {
            if (student.getIndex() == index) {
                s = student;
            }
        }
        return s;
    }

    public double getAverageNumberOfContacts() {
        double sum = 0;
        for (Student student : students) {
            sum += (student.countEmails() + student.countPhones());
        }
        return sum / students.size();
    }

    public Student getStudentWithMostContacts() {
        Student s = null;
        int maxContacts = 0;
        for (Student student : students) {
            if ((student.countPhones() + student.countEmails()) > maxContacts) {
                maxContacts = student.countPhones() + student.countEmails();
                s = student;
            }
            if((student.countPhones() + student.countEmails()) == maxContacts){
                if(s.getIndex()<student.getIndex()){
                    s = student;
                }
            }
        }
        return s;
    }

    @Override
    public String toString() {
        StringBuilder studentsJson = new StringBuilder("[");
        for (Student student : students) {
            studentsJson.append(student.toString()).append(", ");
        }
        if (studentsJson.length() > 1) {
            studentsJson.setLength(studentsJson.length() - 2);
        }
        studentsJson.append("]");

        return String.format(
                "{\"fakultet\":\"%s\", \"studenti\":%s}",
                name, studentsJson
        );
    }

}

public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (!faculty.getStudent(rindex).getEmailContacts().isEmpty()
                            && !faculty.getStudent(rindex).getPhoneContacts().isEmpty()) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().size()
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().size());

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().size();
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().size();

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts().get(posEmail).isNewerThan(faculty
                                        .getStudent(rindex).getPhoneContacts().get(posPhone)));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }
            }
        }
        scanner.close();
    }
}

