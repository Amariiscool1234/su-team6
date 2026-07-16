package LeagueFinder.LeagueFinder.dto;

public class TeamStatisticsResponse {

    private Long teamId;
    private String teamName;
    private long totalRequests;
    private long pending;
    private long approved;
    private long rejected;
    private long waitlisted;

    public TeamStatisticsResponse(
            Long teamId,
            String teamName,
            long totalRequests,
            long pending,
            long approved,
            long rejected,
            long waitlisted
    ) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.totalRequests = totalRequests;
        this.pending = pending;
        this.approved = approved;
        this.rejected = rejected;
        this.waitlisted = waitlisted;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getPending() {
        return pending;
    }

    public long getApproved() {
        return approved;
    }

    public long getRejected() {
        return rejected;
    }

    public long getWaitlisted() {
        return waitlisted;
    }
}