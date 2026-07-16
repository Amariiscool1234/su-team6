package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.dto.RegistrationStatusRequest;
import LeagueFinder.LeagueFinder.dto.TeamRegistrationRequest;
import LeagueFinder.LeagueFinder.entity.TeamRegistration;
import LeagueFinder.LeagueFinder.service.TeamRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team-registrations")
@CrossOrigin(origins = "*")
public class TeamRegistrationController {

    private final TeamRegistrationService registrationService;

    public TeamRegistrationController(
            TeamRegistrationService registrationService
    ) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public List<TeamRegistration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistrationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    registrationService.getRegistrationById(id)
            );
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public List<TeamRegistration> getByCustomer(
            @PathVariable Long customerId
    ) {
        return registrationService.getRegistrationsByCustomer(customerId);
    }

    @GetMapping("/team/{teamId}")
    public List<TeamRegistration> getByTeam(@PathVariable Long teamId) {
        return registrationService.getRegistrationsByTeam(teamId);
    }

    @PostMapping
    public ResponseEntity<?> registerForTeam(
            @RequestBody TeamRegistrationRequest request
    ) {
        try {
            TeamRegistration registration =
                    registrationService.registerForTeam(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(registration);
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody RegistrationStatusRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    registrationService.updateStatus(
                            id,
                            request.getStatus()
                    )
            );
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelRegistration(@PathVariable Long id) {
        try {
            registrationService.cancelRegistration(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}