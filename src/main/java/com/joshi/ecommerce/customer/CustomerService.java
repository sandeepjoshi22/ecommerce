package com.joshi.ecommerce.customer;

import com.joshi.ecommerce.customer.api.CreateCustomerRequest;
import com.joshi.ecommerce.customer.api.CustomerResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerResponse> getAllCustomers(){
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    public CustomerResponse getCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request){
        Customer customer = new Customer(
                request.name(),
                request.email()
        );

        Customer saved = customerRepository.save(customer);
        return CustomerResponse.from(saved);
    }
}
