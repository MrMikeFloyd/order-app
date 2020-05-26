package de.maik.store.web.repository;

import de.maik.store.web.domain.Customer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select customer from Customer customer where customer.user.login = ?#{principal.preferredUsername}")
    List<Customer> findByUserIsCurrentUser();
}
