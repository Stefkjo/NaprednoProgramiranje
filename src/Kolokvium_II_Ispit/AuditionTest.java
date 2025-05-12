package Kolokvium_II_Ispit;

import java.util.*;

class Participant {
    private String city;
    private String code;
    private String name;
    private int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString(){
        return String.format("%s %s %d", code, name, age);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}

class Audition {
    Map<String, Set<Participant>> auditions;

    public Audition() {
        auditions = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant p = new Participant(city, code, name, age);
        if (!auditions.containsKey(city)) {
            auditions.put(city, new HashSet<>());
        }

        auditions.get(city).add(p);
    }

    public void listByCity(String city) {

        Set<Participant> participantsByCity = new TreeSet<>(Comparator.comparing(Participant::getName)
                .thenComparingInt(Participant::getAge)
                .thenComparing(Participant::getCode));

        participantsByCity.addAll(auditions.get(city));

        participantsByCity.forEach(participant -> System.out.println(participant.toString()));

    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
