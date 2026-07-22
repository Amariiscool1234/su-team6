package LeagueFinder.LeagueFinder.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class GameRequest {
    private LocalDate gameDate;
    private LocalTime gameTime;
    private String homeTeam;
    private String awayTeam;
    private String venue;
    private String court;

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public LocalTime getGameTime() {
        return gameTime;
    }

    public void setGameTime(LocalTime gameTime) {
        this.gameTime = gameTime;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

}
