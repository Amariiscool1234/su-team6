package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.TeamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRegistrationRepository
        extends JpaRepository<TeamRegistration, Long> {

    List<TeamRegistration> findByCustomerId(Long customerId);

    List<TeamRegistration> findByTeamId(Long teamId);

    boolean existsByCustomerIdAndTeamId(Long customerId, Long teamId);

    long countByTeamIdAndStatus(Long teamId, String status);
}