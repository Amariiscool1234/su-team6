package LeagueFinder.LeagueFinder.dto;

public class TeamRecordDTO {

    private Long teamId;
    private String teamName;
    private String sport;
    private int wins;
    private int losses;
    private int ties;

    public TeamRecordDTO() {
    }

    public TeamRecordDTO(
            Long teamId,
            String teamName,
            String sport,
            int wins,
            int losses,
            int ties) {

        this.teamId = teamId;
        this.teamName = teamName;
        this.sport = sport;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }
}
