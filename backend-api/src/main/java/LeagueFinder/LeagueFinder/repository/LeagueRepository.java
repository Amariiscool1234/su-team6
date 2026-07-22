package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.league;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<league, Long> {
    List<league> findBySportIgnoreCase(String sport);
}
