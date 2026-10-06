package com.joshi.ecommerce.customer;

import com.joshi.ecommerce.customer.api.CreateCustomerRequest;
import com.joshi.ecommerce.customer.api.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnCustomerWhenCustomerExists() {
        Customer customer = new Customer(
                "Sandeep",
                "sandeep@example.com"
        );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        CustomerResponse result = customerService.getCustomer(1L);

        assertThat(result.name()).isEqualTo("Sandeep");
        assertThat(result.email()).isEqualTo("sandeep@example.com");
    }

    @Test
    void shouldReturnAllCustomers() {
        Customer customer1 = new Customer(
                "Sandeep",
                "sandeep@example.com"
        );

        Customer customer2 = new Customer(
                "Rahul",
                "rahul@example.com"
        );

        when(customerRepository.findAll())
                .thenReturn(List.of(customer1, customer2));

        List<CustomerResponse> result =
                customerService.getAllCustomers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Sandeep");
        assertThat(result.get(1).name()).isEqualTo("Rahul");
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist() {
        when(customerRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomer(999L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer not found: 999");
    }

    @Test
    void shouldCreateCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Sandeep",
                "sandeep@example.com"
        );

        Customer savedCustomer = new Customer(
                "Sandeep",
                "sandeep@example.com"
        );

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(savedCustomer);

        CustomerResponse result =
                customerService.createCustomer(request);

        assertThat(result.name()).isEqualTo("Sandeep");
        assertThat(result.email()).isEqualTo("sandeep@example.com");
    }
}
