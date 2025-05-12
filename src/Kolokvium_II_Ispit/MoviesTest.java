package Kolokvium_II_Ispit;

import java.util.*;
import java.util.stream.Collectors;

class Movie {
    private String title;
    private List<Integer> ratings;
    private int maxRating;


    public Movie(String title, List<Integer> ratings) {
        this.title = title;
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public double getAverageRating(){
        return ratings.stream()
                .mapToInt(i->i)
                .average().orElse(0.0);
    }

    public void updateMaxRating(int newMax){
        this.maxRating = newMax;
    }

    public double getMovieCoef(){
        return getAverageRating()*ratings.size();
    }

    @Override
    public String toString(){
        return String.format("%s (%.2f) of %d ratings", title, getAverageRating(), ratings.size());
    }
}

class MoviesList{
    private List<Movie> movies;
    private int maxRating;

    public MoviesList(){
        movies = new ArrayList<>();
        this.maxRating = 0;
    }

    public void addMovie(String title, int[] rating){
        if(rating.length>this.maxRating){
            this.maxRating = rating.length;
        }
        List<Integer> ratings = new ArrayList<>();
        for (int i : rating) {
            ratings.add(i);
        }
        movies.add(new Movie(title, ratings));
    }

    public List<Movie> top10ByAvgRating() {
        return movies.stream()
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed()
                        .thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Movie> top10ByRatingCoef() {
        return movies.stream()
                .sorted(Comparator.comparingDouble(Movie::getMovieCoef).reversed()
                        .thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }


}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
