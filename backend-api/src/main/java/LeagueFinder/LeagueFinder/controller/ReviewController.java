package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.dto.ReviewRequest;
import LeagueFinder.LeagueFinder.dto.ReviewResponseRequest;
import LeagueFinder.LeagueFinder.entity.Review;
import LeagueFinder.LeagueFinder.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/team/{teamId}")
    public List<Review> getReviewsByTeam(
            @PathVariable Long teamId
    ) {
        return reviewService.getReviewsByTeam(teamId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Review> getReviewsByCustomer(
            @PathVariable Long customerId
    ) {
        return reviewService.getReviewsByCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestBody ReviewRequest request
    ) {
        try {
            Review review = reviewService.createReview(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(review);
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }

    @PutMapping("/{id}/response")
    public ResponseEntity<?> replyToReview(
            @PathVariable Long id,
            @RequestBody ReviewResponseRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    reviewService.replyToReview(
                            id,
                            request.getResponse()
                    )
            );
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}