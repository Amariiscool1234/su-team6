package LeagueFinder.LeagueFinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long customerId;


    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String location;

    private String favoriteSport;

    private String skillLevel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "CUSTOMER";
    public Customer() {
    }

    public Customer(
            String name,
            String email,
            String location,
            String favoriteSport
    ) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.favoriteSport = favoriteSport;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFavoriteSport() {
        return favoriteSport;
    }

    public void setFavoriteSport(String favoriteSport) {
        this.favoriteSport = favoriteSport;
    }
    public String getPassword() {
    return password;
    }

    public void setPassword(String password) {
    this.password = password;
    }

    public String getRole() {
    return role;
    }

    public void setRole(String role) {
    this.role = role;
    }
    public String getSkillLevel() {
    return skillLevel;
}

public void setSkillLevel(String skillLevel) {
    this.skillLevel = skillLevel;
}
}