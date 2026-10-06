package com.joshi.ecommerce.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndRetrieveProduct() {
        Product product = new Product(
                "Mechanical Keyboard",
                "A mechanical keyboard for testing",
                new BigDecimal("4999.99")
        );

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Mechanical Keyboard");
        assertThat(savedProduct.getPrice())
                .isEqualByComparingTo("4999.99");
    }
}
