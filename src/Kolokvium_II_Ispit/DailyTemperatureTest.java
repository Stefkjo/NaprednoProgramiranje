package Kolokvium_II_Ispit;

import java.io.*;
import java.util.*;

class Measurement {
    private double temp;
    private char scale;

    public Measurement(double temp, char scale) {
        this.temp = temp;
        this.scale = scale;
    }

    public double getTemp() {
        return temp;
    }

    public char getScale() {
        return scale;
    }

    public double convertScale(char changeScale) {
        if(scale==changeScale){
            return temp;
        }else if(scale=='C' && changeScale=='F'){
            return ((temp*9)/5)+32;
        }else if(scale=='F' && changeScale=='C'){
            return ((temp-32)*5)/9;
        }
        return 0.0;
    }
}

class DailyTemperatures {
    private Map<Integer, List<Measurement>> dailyTemperatures;

    public DailyTemperatures() {
        this.dailyTemperatures = new TreeMap<>();
    }

    public void readTemperatures(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            Integer date = Integer.parseInt(parts[0]);
            char scale = parts[1].charAt(parts[1].length() - 1);
            List<Measurement> measurements = new ArrayList<>();
            for (int i = 1; i < parts.length; i++) {
                measurements.add(new Measurement(Integer.parseInt(parts[i].substring(0, parts[i].length() - 1)), scale));
            }
            dailyTemperatures.put(date, measurements);
        });
    }

   public void writeDailyStats(OutputStream outputStream, char scale) {
        PrintWriter pw = new PrintWriter(outputStream);

        dailyTemperatures.entrySet()
                .stream()
                .forEach(entry -> {
                    pw.write(String.format("%02d: ", entry.getKey()));
                    List<Measurement> measurements = entry.getValue();
                    DoubleSummaryStatistics results = measurements.stream()
                            .mapToDouble(measurement -> measurement.convertScale(scale))
                            .summaryStatistics();
                    if (scale == 'C') {
                        pw.write(String.format("Count: %3d Min: %6.2fC Max: %6.2fC Avg: %6.2fC\n", results.getCount(), results.getMin(), results.getMax(), results.getAverage()));
                    }
                    if (scale == 'F') {
                        pw.write(String.format("Count: %3d Min: %6.2fF Max: %6.2fF Avg: %6.2fF\n", results.getCount(), results.getMin(), results.getMax(), results.getAverage()));
                    }
                });

        pw.flush();
    }
}

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

