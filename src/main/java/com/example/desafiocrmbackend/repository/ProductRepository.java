package com.example.desafiocrmbackend.repository;

import com.example.desafiocrmbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
