package LeagueFinder.LeagueFinder.repository;

import LeagueFinder.LeagueFinder.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
