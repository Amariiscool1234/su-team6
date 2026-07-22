package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.entity.league;
import LeagueFinder.LeagueFinder.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RestController
@RequestMapping("/leagues")
@CrossOrigin(origins = "https://amariiscool1234.github.io")
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @GetMapping
    public List<league> getAllLeagues() {
        return leagueService.getAllLeagues();
    }

    @GetMapping("/sport/{sport}")
    public List<league> getLeaguesBySport(@PathVariable String sport) {
        return leagueService.getLeaguesBySport(sport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeagueById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(leagueService.getLeagueById(id));
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public league createLeague(@RequestBody league league) {
        return leagueService.createLeague(league);
    }

    @PutMapping("/{id}")
    public league updateLeague(@PathVariable Long id, @RequestBody league updatedLeague) {
        return leagueService.updateLeague(id, updatedLeague);
    }

    @DeleteMapping("/{id}")
    public String deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return "League deleted successfully";
    }

}
