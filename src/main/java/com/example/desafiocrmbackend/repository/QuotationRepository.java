package com.example.desafiocrmbackend.repository;

import com.example.desafiocrmbackend.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {
}
