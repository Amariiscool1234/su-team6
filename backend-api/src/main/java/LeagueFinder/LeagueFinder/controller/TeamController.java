package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.dto.TeamRequest;
import LeagueFinder.LeagueFinder.entity.Team;
import LeagueFinder.LeagueFinder.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = "*")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/league/{leagueId}")
    public List<Team> getTeamsByLeague(@PathVariable Long leagueId) {
        return teamService.getTeamsByLeague(leagueId);
    }

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamRequest request) {
        try {
            Team team = teamService.createTeam(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(team);
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(
            @PathVariable Long id,
            @RequestBody TeamRequest request
    ) {
        try {
            return ResponseEntity.ok(teamService.updateTeam(id, request));
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
