package Kolokvium_II_Ispit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class DateUtil {
    public static long durationBetween(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMinutes();
    }
}

class CarS extends DateUtil{
    private String registration;
    private LocalDateTime timeEnteredParking;
    private LocalDateTime timeLeftParking;

    public CarS(String registration) {
        this.registration = registration;
    }

    public void setTimeEnteredParking(LocalDateTime timeEnteredParking) {
        this.timeEnteredParking = timeEnteredParking;
    }

    public void setTimeLeftParking(LocalDateTime timeLeftParking) {
        this.timeLeftParking = timeLeftParking;
    }

    public String getRegistration() {
        return registration;
    }

    public LocalDateTime getTimeEnteredParking() {
        return timeEnteredParking;
    }

    public LocalDateTime getTimeLeftParking() {
        return timeLeftParking;
    }

    public long durationParked(){
        return durationBetween(timeEnteredParking, timeLeftParking);
    }
}

class Parking{
    private int capacity;
    private Map<String, CarS> parkingSpots;
    private Map<String, CarS> parkingHistory;
    private Map<String, Integer> carParked;
    public Parking(int capacity){
        this.capacity = capacity;
        parkingSpots = new TreeMap<>(Comparator.reverseOrder());
        parkingHistory = new HashMap<>();
        carParked = new TreeMap<>();
    }

    public void update(String registration, String spot, LocalDateTime timestamp, boolean entry){
        if(entry){
            CarS car = new CarS(registration);
            car.setTimeEnteredParking(timestamp);
            parkingSpots.putIfAbsent(spot, car);
            if(!carParked.containsKey(registration)){
                carParked.put(registration, 1);
            }else{
                carParked.computeIfPresent(registration, (k,v) -> v+1);
            }
        }else{
            CarS car = parkingSpots.get(spot);
            car.setTimeLeftParking(timestamp);
            parkingHistory.putIfAbsent(spot, car);
            parkingSpots.remove(spot);
        }
    }

    public void currentState(){
        double percentCapacity = ((double)parkingSpots.size()/capacity)*100.0;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Capacity filled: %.2f%%\n", percentCapacity));
        parkingSpots.entrySet()
                .stream()
                .sorted((a,b) -> b.getValue().getTimeEnteredParking().compareTo(a.getValue().getTimeEnteredParking()))
                .forEach(entry -> sb.append(String.format("Registration number: %s Spot: %s Start timestamp: %s\n",
                        entry.getValue().getRegistration(), entry.getKey(), entry.getValue().getTimeEnteredParking())));
        System.out.println(sb);
    }

    public void history(){
        StringBuilder sb = new StringBuilder();
        parkingHistory.entrySet()
                .stream()
                .sorted((a,b)-> Math.toIntExact(b.getValue().durationParked() - a.getValue().durationParked()))
                .forEach(entry -> sb.append(String.format("Registration number: %s Spot: %s Start timestamp: %s End timestamp: %s Duration in minutes: %d\n",
                        entry.getValue().getRegistration(), entry.getKey(), entry.getValue().getTimeEnteredParking(), entry.getValue().getTimeLeftParking(), entry.getValue().durationParked())));
        System.out.println(sb);
    }

    public Map<String, Integer> carStatistics(){
        return carParked;
    }

    Map<String, Double> spotOccupancy(LocalDateTime start, LocalDateTime end) {
        long totalTime = Duration.between(start, end).toMinutes();
        Map<String, Double> occupancyMap = new HashMap<>();

        for (Map.Entry<String, CarS> entry : parkingHistory.entrySet()) {
            String spot = entry.getKey();
            CarS car = entry.getValue();
            LocalDateTime entered = car.getTimeEnteredParking();
            LocalDateTime left = car.getTimeLeftParking();

            LocalDateTime actualStart = (entered.isBefore(start)) ? start : entered;
            LocalDateTime actualEnd = (left == null || left.isAfter(end)) ? end : left;

            if (!actualEnd.isBefore(actualStart)) {
                long occupiedTime = Duration.between(actualStart, actualEnd).toMinutes();
                double occupancyPercentage = (occupiedTime * 100.0) / totalTime;
                occupancyMap.put(spot, occupancyPercentage);
            } else {
                occupancyMap.put(spot, 0.0);
            }
        }

        return occupancyMap;
    }

}


public class ParkingTesting {

    public static <K, V extends Comparable<V>> void printMapSortedByValue(Map<K, V> map) {
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s -> %s", entry.getKey().toString(), entry.getValue().toString())));

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int capacity = Integer.parseInt(sc.nextLine());

        Parking parking = new Parking(capacity);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equals("update")) {
                String registration = parts[1];
                String spot = parts[2];
                LocalDateTime timestamp = LocalDateTime.parse(parts[3]);
                boolean entrance = Boolean.parseBoolean(parts[4]);
                parking.update(registration, spot, timestamp, entrance);
            } else if (parts[0].equals("currentState")) {
                System.out.println("PARKING CURRENT STATE");
                parking.currentState();
            } else if (parts[0].equals("history")) {
                System.out.println("PARKING HISTORY");
                parking.history();
            } else if (parts[0].equals("carStatistics")) {
                System.out.println("CAR STATISTICS");
                printMapSortedByValue(parking.carStatistics());
            } else if (parts[0].equals("spotOccupancy")) {
                LocalDateTime start = LocalDateTime.parse(parts[1]);
                LocalDateTime end = LocalDateTime.parse(parts[2]);
                printMapSortedByValue(parking.spotOccupancy(start, end));
            }
        }
    }
}
