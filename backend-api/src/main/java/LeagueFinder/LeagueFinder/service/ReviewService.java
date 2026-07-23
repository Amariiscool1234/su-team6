package LeagueFinder.LeagueFinder.service;

import LeagueFinder.LeagueFinder.dto.ReviewRequest;
import LeagueFinder.LeagueFinder.entity.Customer;
import LeagueFinder.LeagueFinder.entity.Review;
import LeagueFinder.LeagueFinder.entity.Team;
import LeagueFinder.LeagueFinder.repository.CustomerRepository;
import LeagueFinder.LeagueFinder.repository.ReviewRepository;
import LeagueFinder.LeagueFinder.repository.TeamRegistrationRepository;
import LeagueFinder.LeagueFinder.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final TeamRepository teamRepository;
    private final TeamRegistrationRepository registrationRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            CustomerRepository customerRepository,
            TeamRepository teamRepository,
            TeamRegistrationRepository registrationRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.teamRepository = teamRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByTeam(Long teamId) {
        return reviewRepository.findByTeamId(teamId);
    }

    public List<Review> getReviewsByCustomer(Long customerId) {
        return reviewRepository.findReviewsByCustomerId(customerId);
    }

    public Review createReview(ReviewRequest request) {
        if (
                request.getRating() == null
                || request.getRating() < 1
                || request.getRating() > 5
        ) {
            throw new RuntimeException(
                    "Rating must be between 1 and 5"
            );
        }

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found")
                );

        Team team = teamRepository
                .findById(request.getTeamId())
                .orElseThrow(() ->
                        new RuntimeException("Team not found")
                );

        boolean approved =
                registrationRepository
                        .existsByCustomerIdAndTeamIdAndStatus(
                                customer.getCustomerId(),
                                team.getId(),
                                "APPROVED"
                        );

        if (!approved) {
            throw new RuntimeException(
                    "Customer must be approved for the team before reviewing it"
            );
        }

        if (
                reviewRepository.existsByCustomerIdAndTeamId(
                        customer.getCustomerId(),
                        team.getId()
                )
        ) {
            throw new RuntimeException(
                    "Customer has already reviewed this team"
            );
        }

        Review review = new Review();
        review.setCustomer(customer);
        review.setTeam(team);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return reviewRepository.save(review);
    }

    public Review replyToReview(Long id, String response) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Review not found")
                );

        review.setProviderResponse(response);
        review.setResponseDate(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found");
        }

        reviewRepository.deleteById(id);
    }
}