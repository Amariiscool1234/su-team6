package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.dto.GameRequest;
import LeagueFinder.LeagueFinder.entity.Game;
import LeagueFinder.LeagueFinder.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "https://amariiscool1234.github.io")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/league/{leagueId}")
    public List<Game> getGamesByLeague(@PathVariable Long leagueId) {
        return gameService.getGamesByLeague(leagueId);
    }

    @GetMapping("/upcoming")
    public List<Game> getUpcomingGames() {
        return gameService.getUpcomingGames();
    }

    @PostMapping("/league/{leagueId}")
    public ResponseEntity<?> createGame(
            @PathVariable Long leagueId,
            @RequestBody GameRequest request
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(gameService.createGame(leagueId, request));
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Long id, @RequestBody GameRequest request) {
        try {
            return ResponseEntity.ok(gameService.updateGame(id, request));
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
