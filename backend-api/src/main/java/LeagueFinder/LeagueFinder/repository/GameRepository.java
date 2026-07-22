package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByLeagueIdOrderByGameDateAscGameTimeAsc(Long leagueId);

    List<Game> findByGameDateGreaterThanEqualOrderByGameDateAscGameTimeAsc(LocalDate date);
}
