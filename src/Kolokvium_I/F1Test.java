package Kolokvium_I;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
class Lap{

    private int minutes;
    private int seconds;
    private int miliseconds;

    public Lap(String hours, String minutes, String second){
        this.minutes = Integer.parseInt(hours);
        this.miliseconds = Integer.parseInt(second);
        this.seconds = Integer.parseInt(minutes);
    }

    @Override
    public String toString(){
        return String.format("%d:%02d:%03d", minutes, seconds, miliseconds);
    }

    public int getTotalTime(){
        return minutes * 60000 + seconds * 1000 + miliseconds;
    }
}

class Racer implements Comparable<Racer>{
    private String name;
    private List<Lap> laps = new ArrayList<>();
    private Lap fastestLap;
    public Racer(String line){
        String [] parts = line.split("\\s+");
        this.name = parts[0];
        this.laps = Arrays.stream(parts).skip(1)
                        .map(part -> new Lap(part.split(":")[0],part.split(":")[1], part.split(":")[2]))
                .collect(Collectors.toUnmodifiableList());

    }

    public String getName() {
        return name;
    }

    public Lap getFastestLap(){
        return fastestLap;
    }

    public int getFastestTime(){
        int fastest = 999999;
        for(int i = laps.size()-1;i>laps.size()-4;i--){
            if(laps.get(i).getTotalTime()<fastest){
                fastest = laps.get(i).getTotalTime();
                fastestLap = laps.get(i);
            }
        }
        return fastest;
    }


    @Override
    public String toString(){
        return this.name+" "+laps+"\n";
    }

    @Override
    public int compareTo(Racer other) {
        return this.getFastestTime()-other.getFastestTime();
    }
}

class F1Race {

    List<Racer> racers = new ArrayList<>();

    public void readResults(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        br.lines().forEach(line -> racers.add(new Racer(line)));

    }

    public void printSorted(OutputStream outputStream){

        PrintWriter wr = new PrintWriter(outputStream);

        racers.forEach(Racer::getFastestTime);
        racers.sort(Comparator.naturalOrder());

        for(int i = 0;i<racers.size();i++){
            wr.write(String.format("%d.  %-10s %s\n",i+1, racers.get(i).getName(), racers.get(i).getFastestLap()));
        }

        wr.flush();
    }
}
