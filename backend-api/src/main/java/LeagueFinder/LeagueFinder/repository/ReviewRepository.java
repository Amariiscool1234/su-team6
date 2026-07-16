package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTeamId(Long teamId);

    List<Review> findByCustomerId(Long customerId);

    boolean existsByCustomerIdAndTeamId(
            Long customerId,
            Long teamId
    );
}