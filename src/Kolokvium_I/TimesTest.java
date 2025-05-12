package Kolokvium_I;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class UnsupportedFormatException extends Exception {

    public UnsupportedFormatException(String message) {
        super(message);
    }

}

class InvalidTimeException extends Exception {

    public InvalidTimeException(String message) {
        super(message);
    }
}

class Time implements Comparable<Time> {

    private int hours;
    private int minutes;
    private String timeFormat;

    public String getTimeFormat() {
        return timeFormat;
    }

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        this.timeFormat = "24";
    }

    public void convertToAMPM() {

        if (this.hours == 0) {
            this.hours = 12;
            this.timeFormat = "AM";
        } else if (this.hours == 12) {
            this.timeFormat = "PM";
        } else if (this.hours > 12) {
            this.hours -= 12;
            this.timeFormat = "PM";
        } else {
            this.timeFormat = "AM";
        }


    }

    public int getTime() {
        return hours * 60 + minutes;
    }

    @Override
    public int compareTo(Time other) {
        return Integer.compare(this.getTime(), other.getTime());
    }

    @Override
    public String toString() {
        if (timeFormat.compareTo("24") == 0) {
            return String.format("%2d:%02d\n", this.hours, this.minutes);
        }
        return String.format("%2d:%02d %s\n", this.hours, this.minutes, this.timeFormat);
    }

}

class TimeTable {

    List<Time> time;

    public TimeTable() {
        time = new ArrayList<>();
    }

    public void readTimes(InputStream inputStream) throws InvalidTimeException, UnsupportedFormatException {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        List<String> lines = br.lines().collect(Collectors.toUnmodifiableList());

        for (String l : lines) {
            String[] parts = l.split("\\s+");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].contains(":")) {
                    String[] part = parts[i].split(":");
                    if (Integer.parseInt(part[0]) > 23 || Integer.parseInt(part[1]) > 59) {
                        throw new InvalidTimeException("InvalidTimeException");
                    }
                    time.add(new Time(Integer.parseInt(part[0]), Integer.parseInt(part[1])));
                } else if (parts[i].contains(".")) {
                    String[] part = parts[i].split("\\.");
                    if (Integer.parseInt(part[0]) > 23 || Integer.parseInt(part[1]) > 59) {
                        throw new InvalidTimeException("InvalidTimeException");
                    }
                    time.add(new Time(Integer.parseInt(part[0]), Integer.parseInt(part[1])));
                } else {
                    throw new UnsupportedFormatException(String.format("%s", parts[i]));
                }
            }
        }
    }

    public void writeTimes(OutputStream outputStream, TimeFormat format) {
        PrintWriter wr = new PrintWriter(outputStream);

        if (format.toString().equals("FORMAT_AMPM")) {
            time.sort(Comparator.naturalOrder());
            time.forEach(Time::convertToAMPM);
            time.forEach(t -> wr.write(t.toString()));
        } else {
            time.sort(Comparator.naturalOrder());
            time.forEach(t -> wr.write(t.toString()));
        }

        wr.flush();
    }
}

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}
