package Kolokvium_I;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

class Runner implements Comparable<Runner> {

    private int id;
    private String startTime;
    private String endTime;
    private int finishTime;

    public Runner(String line) {
        //ID START_TIME END_TIME (пр. 1234 08:00:05 08:31:26)
        String[] parts = line.split(" ");
        this.id = Integer.parseInt(parts[0]);
        this.startTime = parts[1];
        this.endTime = parts[2];

        int startHours = Integer.parseInt(startTime.split(":")[0]);
        int startMinutes = Integer.parseInt(startTime.split(":")[1]);
        int startSeconds = Integer.parseInt(startTime.split(":")[2]);

        int finishHours = Integer.parseInt(endTime.split(":")[0]);
        int finishMinutes = Integer.parseInt(endTime.split(":")[1]);
        int finishSeconds = Integer.parseInt(endTime.split(":")[2]);

        this.finishTime = (startHours * 3600 + startMinutes * 60 + startSeconds) - (finishHours * 3600 + finishMinutes * 60 + finishSeconds);

    }

    public int getFinishTime() {
        return this.finishTime;
    }

    @Override
    public int compareTo(Runner other) {
        return Integer.compare(other.getFinishTime(), this.getFinishTime());
    }

    @Override
    public String toString() {
        return String.format("%d %02d:%02d:%02d", id, (finishTime / 3600),Math.abs((finishTime % 3600) / 60),Math.abs(finishTime % 60));
    }
}

class TeamRace {

    public static void findBestTeam(InputStream is, OutputStream os) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter writer = new PrintWriter(os);
        List<Runner> runners = br.lines()
                .map(line -> new Runner(line))
                .sorted()
                .collect(Collectors.toUnmodifiableList());

        int totalTime = 0;
        for(int i = 0;i<4;i++){
            totalTime+=runners.get(i).getFinishTime();
            writer.println(runners.get(i));
        }

        writer.println(String.format("%02d:%02d:%02d",Math.abs((totalTime / 3600)),Math.abs((totalTime % 3600) / 60),Math.abs(totalTime % 60)));

        writer.flush();
        writer.close();
    }

}

public class RaceTest {
    public static void main(String[] args) {
        try {
            TeamRace.findBestTeam(System.in, System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}