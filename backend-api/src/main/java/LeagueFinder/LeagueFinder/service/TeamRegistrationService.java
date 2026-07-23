package LeagueFinder.LeagueFinder.service;

import LeagueFinder.LeagueFinder.dto.TeamRegistrationRequest;
import LeagueFinder.LeagueFinder.entity.Customer;
import LeagueFinder.LeagueFinder.entity.Team;
import LeagueFinder.LeagueFinder.entity.TeamRegistration;
import LeagueFinder.LeagueFinder.repository.CustomerRepository;
import LeagueFinder.LeagueFinder.repository.TeamRegistrationRepository;
import LeagueFinder.LeagueFinder.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeamRegistrationService {

    private static final Set<String> VALID_STATUSES =
            Set.of("PENDING", "APPROVED", "REJECTED", "WAITLISTED");

    private final TeamRegistrationRepository registrationRepository;
    private final CustomerRepository customerRepository;
    private final TeamRepository teamRepository;

    public TeamRegistrationService(
            TeamRegistrationRepository registrationRepository,
            CustomerRepository customerRepository,
            TeamRepository teamRepository
    ) {
        this.registrationRepository = registrationRepository;
        this.customerRepository = customerRepository;
        this.teamRepository = teamRepository;
    }

    public List<TeamRegistration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public TeamRegistration getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
    }

    public List<TeamRegistration> getRegistrationsByCustomer(Long customerId) {
        return registrationRepository.findRegistrationsByCustomerId(customerId);
    }

    public List<TeamRegistration> getRegistrationsByTeam(Long teamId) {
        return registrationRepository.findRegistrationsByTeamId(teamId);
    }

    public TeamRegistration registerForTeam(TeamRegistrationRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        boolean alreadyRegistered =
                registrationRepository.existsByCustomerIdAndTeamId(
                        customer.getCustomerId(),
                        team.getId()
                );

        if (alreadyRegistered) {
            throw new RuntimeException(
                    "Customer is already registered for this team"
            );
        }

        long approvedPlayers =
                registrationRepository.countByTeamIdAndStatus(
                        team.getId(),
                        "APPROVED"
                );

        String status = "PENDING";

        if (
                team.getMaxPlayers() != null
                && approvedPlayers >= team.getMaxPlayers()
        ) {
            status = "WAITLISTED";
        }

        TeamRegistration registration = new TeamRegistration();
        registration.setCustomer(customer);
        registration.setTeam(team);
        registration.setStatus(status);

        return registrationRepository.save(registration);
    }

    public TeamRegistration updateStatus(Long id, String status) {
        TeamRegistration registration = getRegistrationById(id);

        String formattedStatus = status.toUpperCase();

        if (!VALID_STATUSES.contains(formattedStatus)) {
            throw new RuntimeException("Invalid registration status");
        }

        if (formattedStatus.equals("APPROVED")) {
            Team team = registration.getTeam();

            long approvedPlayers =
                    registrationRepository.countByTeamIdAndStatus(
                            team.getId(),
                            "APPROVED"
                    );

            if (
                    team.getMaxPlayers() != null
                    && approvedPlayers >= team.getMaxPlayers()
            ) {
                throw new RuntimeException("This team is full");
            }
        }

        registration.setStatus(formattedStatus);
        return registrationRepository.save(registration);
    }

    public void cancelRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new RuntimeException("Registration not found");
        }

        registrationRepository.deleteById(id);
    }
}