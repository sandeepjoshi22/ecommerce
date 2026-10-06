package com.joshi.ecommerce.product;

import com.joshi.ecommerce.product.api.CreateProductRequest;
import com.joshi.ecommerce.product.api.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldReturnProducts() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        ProductResponse product = new ProductResponse(
                1L,
                "Mechanical Keyboard",
                "A mechanical keyboard",
                new BigDecimal("4999.99"),
                now,
                now
        );

        when(productService.getAllProducts())
                .thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Mechanical Keyboard"))
                .andExpect(jsonPath("$[0].description")
                        .value("A mechanical keyboard"))
                .andExpect(jsonPath("$[0].price")
                        .value(4999.99));
    }

    @Test
    void shouldReturnProduct() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        ProductResponse product = new ProductResponse(
                1L,
                "Mechanical Keyboard",
                "A mechanical keyboard",
                new BigDecimal("4999.99"),
                now,
                now
        );

        when(productService.getProductById(1L))
                .thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Mechanical Keyboard"))
                .andExpect(jsonPath("$.description")
                        .value("A mechanical keyboard"))
                .andExpect(jsonPath("$.price")
                        .value(4999.99));
    }

    @Test
    void shouldReturn404WhenProductDoesNotExist() throws Exception {

        when(productService.getProductById(999L))
                .thenThrow(new ProductNotFoundException(999L));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("PRODUCT_NOT_FOUND"))
                .andExpect(jsonPath("$.message")
                        .value("Product not found: 999"));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductResponse response = new ProductResponse(
                1L,
                "Mechanical Keyboard",
                "A mechanical keyboard",
                new BigDecimal("4999.99"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(productService.createProduct(any(CreateProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Mechanical Keyboard",
                              "description": "A mechanical keyboard",
                              "price": 4999.99
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Mechanical Keyboard"))
                .andExpect(jsonPath("$.price")
                        .value(4999.99));
    }

    @Test
    void shouldRejectInvalidProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "",
                              "price": -10
                            }
                            """))
                .andExpect(status().isBadRequest());
    }
}
