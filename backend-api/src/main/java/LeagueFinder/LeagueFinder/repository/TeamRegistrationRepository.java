package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.TeamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface TeamRegistrationRepository
        extends JpaRepository<TeamRegistration, Long> {

    List<TeamRegistration> findByCustomerId(Long customerId);

    List<TeamRegistration> findByTeamId(Long teamId);

    boolean existsByCustomerIdAndTeamId(Long customerId, Long teamId);

    long countByTeamIdAndStatus(Long teamId, String status);

    boolean existsByCustomerIdAndTeamIdAndStatus(
            Long customerId,
            Long teamId,
            String status
    );
    long countByTeamId(Long teamId);
}