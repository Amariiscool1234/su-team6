package LeagueFinder.LeagueFinder.service;

import LeagueFinder.LeagueFinder.dto.TeamRequest;
import LeagueFinder.LeagueFinder.entity.Team;
import LeagueFinder.LeagueFinder.entity.league;
import LeagueFinder.LeagueFinder.repository.LeagueRepository;
import LeagueFinder.LeagueFinder.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public TeamService(
            TeamRepository teamRepository,
            LeagueRepository leagueRepository
    ) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> getTeamsByLeague(Long leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }

    public Team createTeam(TeamRequest request) {
        league selectedLeague = leagueRepository.findById(request.getLeagueId())
                .orElseThrow(() -> new RuntimeException("League not found"));

        Team team = new Team();
        team.setName(request.getName());
        team.setSport(request.getSport());
        team.setSkillLevel(request.getSkillLevel());
        team.setMaxPlayers(request.getMaxPlayers());
        team.setLeague(selectedLeague);

        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, TeamRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        league selectedLeague = leagueRepository.findById(request.getLeagueId())
                .orElseThrow(() -> new RuntimeException("League not found"));

        team.setName(request.getName());
        team.setSport(request.getSport());
        team.setSkillLevel(request.getSkillLevel());
        team.setMaxPlayers(request.getMaxPlayers());
        team.setLeague(selectedLeague);

        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found");
        }

        teamRepository.deleteById(id);
    }
}
