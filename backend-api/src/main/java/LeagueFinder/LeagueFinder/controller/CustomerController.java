package LeagueFinder.LeagueFinder.controller;

import LeagueFinder.LeagueFinder.entity.Customer;
import LeagueFinder.LeagueFinder.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // GET all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // GET customer by ID
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    // POST create a new customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // PUT update an existing customer
    @PutMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId,
                                   @RequestBody Customer customer) {
        return customerService.updateCustomer(customerId, customer);
    }

    // DELETE customer
    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return "Customer deleted successfully.";
    }
}