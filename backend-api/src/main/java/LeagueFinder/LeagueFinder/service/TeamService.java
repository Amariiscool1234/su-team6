package LeagueFinder.LeagueFinder.service;

import LeagueFinder.LeagueFinder.dto.TeamRequest;
import LeagueFinder.LeagueFinder.entity.Team;
import LeagueFinder.LeagueFinder.entity.league;
import LeagueFinder.LeagueFinder.repository.LeagueRepository;
import LeagueFinder.LeagueFinder.repository.ProviderRepository;
import LeagueFinder.LeagueFinder.repository.TeamRepository;
import LeagueFinder.LeagueFinder.entity.Provider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final ProviderRepository providerRepository;

    public TeamService(
            TeamRepository teamRepository,
            LeagueRepository leagueRepository,
            ProviderRepository providerRepository
    ) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.providerRepository = providerRepository;
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
    public List<Team> getTeamsByProvider(Long providerId) {
    return teamRepository.findByProviderId(providerId);
}

    public Team createTeam(TeamRequest request, Long providerId) {
        league selectedLeague = leagueRepository.findById(request.getLeagueId())
                .orElseThrow(() -> new RuntimeException("League not found"));
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        Team team = new Team();
        team.setName(request.getName());
        team.setSport(request.getSport());
        team.setSkillLevel(request.getSkillLevel());
        team.setMaxPlayers(request.getMaxPlayers());
        team.setLeague(selectedLeague);
        team.setProvider(provider);

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
    public Team saveTeam(Team team) {
    return teamRepository.save(team);
}
}
