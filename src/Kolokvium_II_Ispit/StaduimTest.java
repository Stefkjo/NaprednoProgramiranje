package Kolokvium_II_Ispit;

import java.util.*;

class SeatTakenException extends Exception {
    public SeatTakenException(String message) {
        super(message);
    }
}

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException(String message) {
        super(message);
    }
}

class Sector {
    private String code;
    private int count;
    private Map<Integer, Boolean> seats;
    private int sectorType;

    public Sector(String code, int count) {
        this.code = code;
        this.count = count;
        this.seats = new HashMap<>();
        this.sectorType = 0;
    }

    public void takeSeat(int seatNumber, int type) throws SeatTakenException, SeatNotAllowedException {
        if (seats.getOrDefault(seatNumber, false)) {
            throw new SeatTakenException("SeatTakenException");
        }
        if (sectorType == 0) {
            sectorType = type;
        } else if ((sectorType == 1 && type == 2) || (sectorType == 2 && type == 1)) {
            throw new SeatNotAllowedException("SeatNotAllowedException");
        }
        seats.put(seatNumber, true);
    }


    public String getCode() {
        return code;
    }

    public int getCount() {
        return count;
    }

    public int getFreeSeats() {
        return count - seats.size();
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%\n", code, count - seats.size(), count, (((double) seats.size() / count) * 100.0));
    }
}

class Stadium {
    private String name;
    private Map<String, Sector> sectors;

    private final Comparator<Sector> compareByFreeSeats
            = Comparator.comparing(Sector::getFreeSeats).reversed();

    public Stadium(String name) {
        this.name = name;
        sectors = new TreeMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes) {
        for (int i = 0; i < sectorNames.length; i++) {
            Sector sector = new Sector(sectorNames[i], sizes[i]);
            sectors.put(sectorNames[i], sector);
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        sectors.get(sectorName).takeSeat(seat, type);
    }

    public String getName() {
        return name;
    }

    public void showSectors() {
        Collection<Sector> allSectors = sectors.values();
        StringBuilder sb = new StringBuilder();
        allSectors.stream()
                        .sorted(compareByFreeSeats)
                                .forEach(sb::append);
        System.out.println(sb);
    }
}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
