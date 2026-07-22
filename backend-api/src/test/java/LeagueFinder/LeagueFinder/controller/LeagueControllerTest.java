package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.entity.league;
import LeagueFinder.LeagueFinder.service.LeagueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueControllerTest {

    @Mock
    private LeagueService leagueService;

    @InjectMocks
    private LeagueController leagueController;

    @Test
    void getLeaguesBySportReturnsMatchingLeagues() {
        league basketballLeague = new league(
                "Greensboro Summer Hoops",
                "Basketball",
                "Greensboro, NC",
                "Summer basketball league"
        );
        when(leagueService.getLeaguesBySport("Basketball"))
                .thenReturn(List.of(basketballLeague));

        List<league> result = leagueController.getLeaguesBySport("Basketball");

        assertEquals(1, result.size());
        assertEquals("Basketball", result.get(0).getSport());
        verify(leagueService).getLeaguesBySport("Basketball");
    }
}
