package com.joshi.ecommerce.product;

import com.joshi.ecommerce.product.api.CreateProductRequest;
import com.joshi.ecommerce.product.api.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnProductWhenProductExists() {
        Product product = new Product(
                "Mechanical Keyboard",
                "A mechanical keyboard",
                new BigDecimal("4999.99")
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse result = productService.getProductById(1L);

        assertThat(result.name())
                .isEqualTo("Mechanical Keyboard");

        assertThat(result.description())
                .isEqualTo("A mechanical keyboard");

        assertThat(result.price())
                .isEqualByComparingTo("4999.99");
    }

    @Test
    void shouldReturnAllProducts() {
        Product keyboard = new Product(
                "Mechanical Keyboard",
                "A mechanical keyboard",
                new BigDecimal("4999.99")
        );

        Product mouse = new Product(
                "Gaming Mouse",
                "A gaming mouse",
                new BigDecimal("2499.99")
        );

        when(productRepository.findAll())
                .thenReturn(List.of(keyboard, mouse));

        List<ProductResponse> result =
                productService.getAllProducts();

        assertThat(result).hasSize(2);

        assertThat(result)
                .extracting(ProductResponse::name)
                .containsExactly(
                        "Mechanical Keyboard",
                        "Gaming Mouse"
                );
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(999L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product not found: 999");
    }

    @Test
    void shouldCreateProduct() {
        CreateProductRequest request = new CreateProductRequest(
                "Mechanical Keyboard",
                "A mechanical keyboard",
                new BigDecimal("4999.99")
        );

        Product savedProduct = new Product(
                "Mechanical Keyboard",
                "A mechanical keyboard",
                new BigDecimal("4999.99")
        );

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponse result = productService.createProduct(request);

        assertThat(result.name())
                .isEqualTo("Mechanical Keyboard");

        assertThat(result.description())
                .isEqualTo("A mechanical keyboard");

        assertThat(result.price())
                .isEqualByComparingTo("4999.99");
    }
}