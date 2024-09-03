package com.example.desafiocrmbackend.entity.dto;

import com.example.desafiocrmbackend.entity.Client;
import com.example.desafiocrmbackend.entity.Product;
import com.example.desafiocrmbackend.enums.QuotationStatus;

import java.sql.Timestamp;
import java.util.List;

public class QuotationDTO {

    private Long id;
    private ClientDTO client;
    private QuotationStatus status;
    private List<ProductDTO> products;
    private Timestamp createdAt;
    private Timestamp finishedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public QuotationStatus getStatus() {
        return status;
    }

    public void setStatus(QuotationStatus status) {
        this.status = status;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(Timestamp finishedOn) {
        this.finishedOn = finishedOn;
    }
}
