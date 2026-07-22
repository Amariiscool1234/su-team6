package LeagueFinder.LeagueFinder.service;

import LeagueFinder.LeagueFinder.entity.league;
import LeagueFinder.LeagueFinder.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    public List<league> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public List<league> getLeaguesBySport(String sport) {
        return leagueRepository.findBySportIgnoreCase(sport);
    }

    public league createLeague(league league) {
        return leagueRepository.save(league);
    }

    public league updateLeague(Long id, league updatedLeague) {
        league existingLeague = leagueRepository.findById(id).orElse(null);

        if (existingLeague != null) {
            existingLeague.setName(updatedLeague.getName());
            existingLeague.setSport(updatedLeague.getSport());
            existingLeague.setLocation(updatedLeague.getLocation());
            existingLeague.setDescription(updatedLeague.getDescription());

            return leagueRepository.save(existingLeague);
        }

        return null;
    }

    public void deleteLeague(Long id) {
        leagueRepository.deleteById(id);
    }
}
