package com.joshi.ecommerce.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldSaveAndFindCustomer(){
        String email = "sandeep+" + UUID.randomUUID() + "@example.com";
        Customer customer = new Customer(
                "Sandeep",
                email
        );

        Customer saved = customerRepository.save(customer);

        Optional<Customer> result = customerRepository.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Sandeep");
        assertThat(result.get().getEmail()).isEqualTo(email);
    }
}
