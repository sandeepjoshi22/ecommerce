package com.joshi.ecommerce.product;

import com.joshi.ecommerce.product.api.CreateProductRequest;
import com.joshi.ecommerce.product.api.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request){
        Product product = new Product(
                request.name(),
                request.description(),
                request.price()
        );

        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }
}
