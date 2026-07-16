package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByLeagueId(Long leagueId);

    List<Team> findBySportIgnoreCase(String sport);

    List<Team> findByNameContainingIgnoreCase(String name);

    List<Team> findByProviderId(Long providerId);
}