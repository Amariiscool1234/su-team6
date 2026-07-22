package LeagueFinder.LeagueFinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    @JsonIgnoreProperties({"teams"})
    private Provider provider;

    @Column(nullable = false)
    private String name;
    private String sport;
    private String skillLevel;
    private Integer maxPlayers;
    private int wins = 0;
    private int losses = 0;
    private int ties = 0;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    @JsonIgnoreProperties({"teams"})
    private league league;

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public league getLeague() {
        return league;
    }

    public void setLeague(league league) {
        this.league = league;
    }
    public int getWins() {
    return wins;
}

public void setWins(int wins) {
    this.wins = wins;
}

public int getLosses() {
    return losses;
}

public void setLosses(int losses) {
    this.losses = losses;
}

public int getTies() {
    return ties;
}

public void setTies(int ties) {
    this.ties = ties;
}
}