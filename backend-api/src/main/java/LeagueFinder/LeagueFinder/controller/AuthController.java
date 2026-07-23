package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.dto.RegisterRequest;
import LeagueFinder.LeagueFinder.entity.Customer;
import LeagueFinder.LeagueFinder.repository.CustomerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        if (request.getName() == null
                || request.getName().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Name is required.");
        }

        if (request.getEmail() == null
                || request.getEmail().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Email is required.");
        }

        if (request.getPassword() == null
                || request.getPassword().length() < 8) {

            return ResponseEntity.badRequest()
                    .body("Password must be at least 8 characters.");
        }

        String normalizedEmail =
                request.getEmail().trim().toLowerCase();

        if (customerRepository.existsByEmail(normalizedEmail)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("An account with this email already exists.");
        }

        Customer customer = new Customer();

        customer.setName(request.getName().trim());
        customer.setEmail(normalizedEmail);
        customer.setPassword(
                passwordEncoder.encode(request.getPassword()));
        customer.setLocation(request.getLocation());
        customer.setFavoriteSport(request.getFavoriteSport());
        customer.setSkillLevel(request.getSkillLevel());
        String requestedRole = request.getRole();

        if ("PROVIDER".equalsIgnoreCase(requestedRole)) {
         customer.setRole("PROVIDER");
        } else {
          customer.setRole("CUSTOMER");
        }
        Customer savedCustomer =
                customerRepository.save(customer);

        Map<String, Object> response = new HashMap<>();

        response.put("message", "Account created successfully.");
        response.put("customerId", savedCustomer.getCustomerId());
        response.put("email", savedCustomer.getEmail());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentCustomer(
            Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Not logged in.");
        }

        Customer customer = customerRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Map<String, Object> response = new HashMap<>();

        response.put("customerId", customer.getCustomerId());
        response.put("name", customer.getName());
        response.put("email", customer.getEmail());
        response.put("location", customer.getLocation());
        response.put("favoriteSport", customer.getFavoriteSport());
        response.put("skillLevel", customer.getSkillLevel());
        response.put("role", customer.getRole());

        return ResponseEntity.ok(response);
    }
}