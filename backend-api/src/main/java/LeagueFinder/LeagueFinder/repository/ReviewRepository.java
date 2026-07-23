package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
        SELECT review
        FROM Review review
        WHERE review.customer.customerId = :customerId
    """)
    List<Review> findReviewsByCustomerId(
            @Param("customerId") Long customerId
    );
      @Query("""
        SELECT review
        FROM Review review
        WHERE review.team.id = :teamId
    """)
    List<Review> findByTeamId(
            @Param("teamId") Long teamId
    );
     @Query("""
        SELECT CASE WHEN COUNT(review) > 0 THEN true ELSE false END
        FROM Review review
        WHERE review.customer.customerId = :customerId
          AND review.team.id = :teamId
    """)
    boolean existsByCustomerIdAndTeamId(
            @Param("customerId") Long customerId,
            @Param("teamId") Long teamId
    );
}