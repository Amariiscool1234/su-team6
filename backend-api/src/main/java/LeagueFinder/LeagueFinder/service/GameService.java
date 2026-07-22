package LeagueFinder.LeagueFinder.service;

import LeagueFinder.LeagueFinder.dto.GameRequest;
import LeagueFinder.LeagueFinder.entity.Game;
import LeagueFinder.LeagueFinder.entity.league;
import LeagueFinder.LeagueFinder.repository.GameRepository;
import LeagueFinder.LeagueFinder.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;

    public GameService(GameRepository gameRepository, LeagueRepository leagueRepository) {
        this.gameRepository = gameRepository;
        this.leagueRepository = leagueRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getGamesByLeague(Long leagueId) {
        return gameRepository.findByLeagueIdOrderByGameDateAscGameTimeAsc(leagueId);
    }

    public List<Game> getUpcomingGames() {
        return gameRepository.findByGameDateGreaterThanEqualOrderByGameDateAscGameTimeAsc(LocalDate.now());
    }

    public Game createGame(Long leagueId, GameRequest request) {
        league selectedLeague = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found."));
        Game game = new Game();
        game.setLeague(selectedLeague);
        return saveGame(game, request);
    }

    public Game updateGame(Long id, GameRequest request) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found."));
        return saveGame(game, request);
    }

    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new RuntimeException("Game not found.");
        }
        gameRepository.deleteById(id);
    }

    private Game saveGame(Game game, GameRequest request) {
        game.setGameDate(request.getGameDate());
        game.setGameTime(request.getGameTime());
        game.setHomeTeam(request.getHomeTeam());
        game.setAwayTeam(request.getAwayTeam());
        game.setVenue(request.getVenue());
        game.setCourt(request.getCourt());
        return gameRepository.save(game);
    }
}
