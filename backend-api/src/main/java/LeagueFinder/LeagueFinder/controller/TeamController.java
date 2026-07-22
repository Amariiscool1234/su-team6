package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.dto.TeamRequest;
import LeagueFinder.LeagueFinder.entity.Team;
import LeagueFinder.LeagueFinder.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/provider/{providerId}")
    public List<Team> getTeamsByProvider(
        @PathVariable Long providerId) {return teamService.getTeamsByProvider(providerId);  
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
    @GetMapping("/records")
    public List<Map<String, Object>> getTeamRecords() {
    return teamService.getAllTeams()
            .stream()
            .map(team -> {
                Map<String, Object> record = new HashMap<>();

                record.put("id", team.getId());
                record.put("name", team.getName());
                record.put("sport", team.getSport());
                record.put("wins", team.getWins());
                record.put("losses", team.getLosses());
                record.put("ties", team.getTies());

                return record;
            })
            .toList();
}

    @PostMapping("/provider/{providerId}")
    public ResponseEntity<?> createTeam(@RequestBody TeamRequest request, @PathVariable Long providerId) {
        try {
            Team team = teamService.createTeam(request, providerId);
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
    @PutMapping("/{id}/record")
public ResponseEntity<?> updateTeamRecord(
        @PathVariable Long id,
        @RequestBody Team recordRequest) {

    try {
        Team team = teamService.getTeamById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (recordRequest.getWins() < 0
                || recordRequest.getLosses() < 0
                || recordRequest.getTies() < 0) {

            return ResponseEntity.badRequest()
                    .body("Team records cannot be negative.");
        }

        team.setWins(recordRequest.getWins());
        team.setLosses(recordRequest.getLosses());
        team.setTies(recordRequest.getTies());

        Team updatedTeam = teamService.saveTeam(team);

        return ResponseEntity.ok(updatedTeam);

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
