package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.entity.Customer;
import LeagueFinder.LeagueFinder.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // GET /customers
    // Retrieves all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // GET /customers/{id}
    // Retrieves one customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id
    ) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer.get());
    }

    // POST /customers
    // Creates a new customer profile
    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @RequestBody Customer customer
    ) {
        customer.setId(null);

        Customer savedCustomer = customerRepository.save(customer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCustomer);
    }

    // PUT /customers/{id}
    // Updates an existing customer profile
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer updatedCustomer
    ) {
        Optional<Customer> existingCustomer =
                customerRepository.findById(id);

        if (existingCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = existingCustomer.get();

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setLocation(updatedCustomer.getLocation());
        customer.setFavoriteSport(
                updatedCustomer.getFavoriteSport()
        );

        Customer savedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(savedCustomer);
    }
}