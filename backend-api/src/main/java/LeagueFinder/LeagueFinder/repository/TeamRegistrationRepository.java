package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.TeamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRegistrationRepository
        extends JpaRepository<TeamRegistration, Long> {

    @Query("""
        SELECT registration
        FROM TeamRegistration registration
        WHERE registration.customer.customerId = :customerId
    """)
    List<TeamRegistration> findRegistrationsByCustomerId(
            @Param("customerId") Long customerId
    );

    @Query("""
        SELECT registration
        FROM TeamRegistration registration
        WHERE registration.team.id = :teamId
    """)
    List<TeamRegistration> findRegistrationsByTeamId(
            @Param("teamId") Long teamId
    );
     @Query("""
        SELECT CASE WHEN COUNT(registration) > 0 THEN true ELSE false END
        FROM TeamRegistration registration
        WHERE registration.customer.customerId = :customerId
          AND registration.team.id = :teamId
    """)
    boolean existsByCustomerIdAndTeamId(
            @Param("customerId") Long customerId,
            @Param("teamId") Long teamId
    );

    @Query("""
        SELECT CASE WHEN COUNT(registration) > 0 THEN true ELSE false END
        FROM TeamRegistration registration
        WHERE registration.customer.customerId = :customerId
          AND registration.team.id = :teamId
          AND registration.status = :status
    """)
    boolean existsByCustomerIdAndTeamIdAndStatus(
            @Param("customerId") Long customerId,
            @Param("teamId") Long teamId,
            @Param("status") String status
    );

    @Query("""
        SELECT COUNT(registration)
        FROM TeamRegistration registration
        WHERE registration.team.id = :teamId
          AND registration.status = :status
    """)
    Long countByTeamIdAndStatus(
            @Param("teamId") Long teamId,
            @Param("status") String status
    );
}