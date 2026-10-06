package com.joshi.ecommerce.customer;

import com.joshi.ecommerce.customer.api.CreateCustomerRequest;
import com.joshi.ecommerce.customer.api.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() throws Exception {

        CustomerResponse response = new CustomerResponse(
                1L,
                "Sandeep",
                "sandeep@example.com",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(customerService.getAllCustomers())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Sandeep"))
                .andExpect(jsonPath("$[0].email")
                        .value("sandeep@example.com"));
    }

    @Test
    void shouldReturnCustomer() throws Exception {

        CustomerResponse response = new CustomerResponse(
                1L,
                "Sandeep",
                "sandeep@example.com",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(customerService.getCustomer(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sandeep"))
                .andExpect(jsonPath("$.email")
                        .value("sandeep@example.com"));
    }

    @Test
    void shouldCreateCustomer() throws Exception {

        CustomerResponse response = new CustomerResponse(
                1L,
                "Sandeep",
                "sandeep@example.com",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Sandeep",
                          "email": "sandeep@example.com"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sandeep"))
                .andExpect(jsonPath("$.email")
                        .value("sandeep@example.com"));
    }

    @Test
    void shouldRejectInvalidCustomer() throws Exception {

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "",
                          "email": "invalid-email"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }
}
