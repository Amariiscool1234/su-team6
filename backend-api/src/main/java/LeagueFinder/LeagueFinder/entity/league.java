package LeagueFinder.LeagueFinder.entity;

public class league {
    private Long id;
    private String name;
    private String sport;
    private String location;
    private String description;

    public league() {
    }

    public league(String name, String sport, String location, String description) {
        this.name = name;
        this.sport = sport;
        this.location = location;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
