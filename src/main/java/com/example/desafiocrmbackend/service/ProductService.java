package com.example.desafiocrmbackend.service;

import com.example.desafiocrmbackend.entity.Product;
import com.example.desafiocrmbackend.entity.dto.ProductDTO;
import com.example.desafiocrmbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAll(){
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = getProductDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public ProductDTO findById(Long id){
        return getProductDTO(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    }


    public ProductDTO save(ProductDTO productDTO){
        validateProductDTO(productDTO);
        return getProductDTO(productRepository.save(getProduct(productDTO)));
    }

    public ProductDTO update(Long id, ProductDTO productDTO){
        ProductDTO productToUpdate = findById(id);
        productToUpdate.setName(productDTO.getName());
        productToUpdate.setStockQuantity(productDTO.getStockQuantity());
        productToUpdate.setPrice(productDTO.getPrice());

        return save(productToUpdate);
    }

    private void validatePrice(BigDecimal price){
        if(price == null || price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    private void validateStockQuantity(Integer stockQuantity){
        if(stockQuantity < 0){
            throw new IllegalArgumentException("Stock quantity is invalid");
        }
    }

    private void validateName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name is invalid");
        }
    }

    private void validateProductDTO(ProductDTO productDTO){
        validateName(productDTO.getName());
        validateStockQuantity(productDTO.getStockQuantity());
        validatePrice(productDTO.getPrice());
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDTO getProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setCreatedAt(product.getCreatedAt());
        return productDTO;
    }

    private Product getProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setPrice(productDTO.getPrice());
        product.setCreatedAt(productDTO.getCreatedAt());
        return product;
    }

    public List<Product> getProductsFromDTOs(List<ProductDTO> productDTOs) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : productDTOs) {
            Product product = new Product();
            product.setId(productDTO.getId());
            product.setName(productDTO.getName());
            product.setStockQuantity(productDTO.getStockQuantity());
            product.setPrice(productDTO.getPrice());
            product.setCreatedAt(productDTO.getCreatedAt());
            products.add(product);
        }
        return products;
    }

    public List<ProductDTO> getProductsDTOFromProduct(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setStockQuantity(product.getStockQuantity());
            productDTO.setPrice(product.getPrice());
            productDTO.setCreatedAt(product.getCreatedAt());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }
}
