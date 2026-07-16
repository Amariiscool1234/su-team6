package LeagueFinder.LeagueFinder.dto;

public class TeamRequest {

    private String name;
    private String sport;
    private String skillLevel;
    private Integer maxPlayers;
    private Long leagueId;
    private Long providerId;

    public TeamRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }
    public Long getProviderId(){
        return providerId;
    }
    public void setProviderId(Long providerId){
        this.providerId = providerId;
    }
}
