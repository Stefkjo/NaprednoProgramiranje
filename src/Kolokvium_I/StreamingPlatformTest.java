package Kolokvium_I;

import java.util.*;
import java.util.stream.Collectors;

class Episode{
    String epName;
    List<Double> rating = new ArrayList<>();
}

class TvShow{

    String nameOfShow;
    List<String> genres;
    List<Episode> episodes = new ArrayList<>();

}

class Movie{

    String nameOfMovie;
    List<String> genres = new ArrayList<>();
    List<Double> ratings = new ArrayList<>();
}

class StreamingPlatform{

    public StreamingPlatform(){}


}

public class StreamingPlatformTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StreamingPlatform sp = new StreamingPlatform();
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String [] parts = line.split(" ");
            String method = parts[0];
            String data = Arrays.stream(parts).skip(1).collect(Collectors.joining(" "));
            if (method.equals("addItem")){
               // sp.addItem(data);
            }
            else if (method.equals("listAllItems")){
                //sp.listAllItems(System.out);
            } else if (method.equals("listFromGenre")){
                System.out.println(data);
                //sp.listFromGenre(data, System.out);
            }
        }

    }
}
