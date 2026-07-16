package LeagueFinder.LeagueFinder.dto;

public class TeamRegistrationRequest {

    private Long customerId;
    private Long teamId;

    public TeamRegistrationRequest() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}