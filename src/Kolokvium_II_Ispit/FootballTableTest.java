package Kolokvium_II_Ispit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Team{
    private String teamName;
    private int goalsScored;
    private int goalsConceded;
    private int points;
    private int wins;
    private int draws;
    private int loses;
    private int gamesPlayed;

    public Team(String teamName) {
        this.teamName = teamName;
        this.points = 0;
        this.wins = 0;
        this.loses = 0;
        this.draws = 0;
        this.gamesPlayed = 0;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLoses() {
        return loses;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void addGame(int goalsScored, int goalsConceded){
        if(goalsScored>goalsConceded){
            points+=3;
            wins++;
        }else if(goalsScored==goalsConceded){
            points+=1;
            draws++;
        }
        if(goalsConceded>goalsScored){
            loses++;
        }
        this.goalsScored+=goalsScored;
        this.goalsConceded+=goalsConceded;
        this.gamesPlayed++;
    }

    public int getGoalDifference(){
        return goalsScored - goalsConceded;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public int getPoints() {
        return points;
    }
}

class FootballTable{
    private Map<String,Team> teams;

    public FootballTable(){
        teams = new TreeMap<>();
    }

    public final Comparator<Team> compareByTotalPointsAndGoalDiffAndName
            = Comparator.comparing(Team::getPoints).reversed()
            .thenComparing(Team::getGoalDifference, Comparator.reverseOrder())
            .thenComparing(Team::getTeamName);

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Team home = teams.getOrDefault(homeTeam, new Team(homeTeam));
        home.addGame(homeGoals, awayGoals);
        teams.put(homeTeam, home);

        Team away = teams.getOrDefault(awayTeam, new Team(awayTeam));
        away.addGame(awayGoals, homeGoals);
        teams.put(awayTeam, away);
    }


    public void printTable(){
        StringBuilder sb = new StringBuilder();
        AtomicInteger counter = new AtomicInteger(1);
        teams.values()
                .stream()
                .sorted(compareByTotalPointsAndGoalDiffAndName)
                .forEach(team -> sb.append(String.format("%2d. %-18s%4d%5d%5d%5d%5d\n",
                        counter.getAndIncrement(), team.getTeamName(), team.getGamesPlayed(), team.getWins(), team.getDraws(), team.getLoses(), team.getPoints())));

        System.out.println(sb);
    }
}

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}
