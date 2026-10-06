package com.joshi.ecommerce.common.exception;

import com.joshi.ecommerce.customer.CustomerNotFoundException;
import com.joshi.ecommerce.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleProductNotFound(ProductNotFoundException exception){
        return Map.of(
                "error", "PRODUCT_NOT_FOUND",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleCustomerNotFound(CustomerNotFoundException exception){
        return Map.of(
                "error", "CUSTOMER_NOT_FOUND",
                "message", exception.getMessage()
        );
    }


}
