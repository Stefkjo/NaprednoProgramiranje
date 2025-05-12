package Kolokvium_I;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Line {

    private int number;
    private int timeFrom;
    private int timeTo;
    private String text;

    public Line(int number, String time, String text) {
        this.number = number;
        String [] parts = time.split("-->");
        this.timeFrom = stringToTime(parts[0].trim());
        this.timeTo = stringToTime(parts[1].trim());
        this.text = text;
    }

    public static int stringToTime(String line) {
        String[] parts = line.split(",");
        String[] hmsFormat = parts[0].split(":");
        int hours = Integer.parseInt(hmsFormat[0]) * 60 * 60 * 1000;
        int minutes = Integer.parseInt(hmsFormat[1]) * 60 * 1000;
        int seconds = Integer.parseInt(hmsFormat[2]) * 1000;
        int ms = Integer.parseInt(parts[1]);
        return hours + minutes + seconds + ms;
    }

    public static String timeToString(int time) {
        int hours = time / (60 * 60 * 1000);
        time = time % (60 * 60 * 1000);
        int minutes = time / (60 * 1000);
        time = time % (60 * 1000);
        int seconds = time / 1000;
        int ms = time % 1000;

        return String.format("%02d:%02d:%02d,%03d",hours, minutes, seconds, ms);
    }
    public void makeShift(int shift){
        this.timeFrom+=shift;
        this.timeTo+=shift;
    }

    @Override
    public String toString(){
        return this.number+"\n"+timeToString(this.timeFrom)+" --> "+timeToString(this.timeTo)+"\n"+this.text;
    }
}

class Subtitles {

    List<Line> lines = new ArrayList<>();

    public Subtitles() {
    }

    public int loadSubtitles(InputStream inputStream) {

        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            int number = Integer.parseInt(line);
            String time = scanner.nextLine();
            StringBuilder text = new StringBuilder();
            while(true){
                if(!scanner.hasNext()) break;
                line = scanner.nextLine();
                if(line.trim().isEmpty()) break;
                text.append(line).append("\n");
            }
            Line l = new Line(number, time, text.toString());
            lines.add(l);
        }

        return lines.size();

    }

    public void print() {
        for(Line line:lines){
            System.out.println(line.toString());
        }
    }

    public void shift(int ms) {

        for(Line line:lines){
            line.makeShift(ms);
            System.out.println(line);
        }
    }
}


public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        //subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}
