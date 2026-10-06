package com.joshi.ecommerce.customer;

import com.joshi.ecommerce.customer.api.CreateCustomerRequest;
import com.joshi.ecommerce.customer.api.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id){
        return customerService.getCustomer(id);
    }

    @GetMapping
    public List<CustomerResponse> getCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return customerService.createCustomer(request);
    }
}
