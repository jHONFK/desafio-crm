package com.example.desafiocrmbackend.controller;

import com.example.desafiocrmbackend.entity.dto.ProductDTO;
import com.example.desafiocrmbackend.entity.dto.QuotationDTO;
import com.example.desafiocrmbackend.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quotations")
public class QuotationController {

    @Autowired
    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService){
        this.quotationService = quotationService;
    }

    @GetMapping
    public ResponseEntity<List<QuotationDTO>> findAll(){
        return ResponseEntity.ok(quotationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuotationDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(quotationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<QuotationDTO> save(@RequestBody QuotationDTO quotationDTO){
        return ResponseEntity.ok(quotationService.save(quotationDTO));
    }

    @PostMapping("/{id}/add-product")
    public ResponseEntity<QuotationDTO> addProductToQuotation(@PathVariable Long id, @RequestBody ProductDTO productToAdd){
        return ResponseEntity.ok(quotationService.addProductToQuotation(id, productToAdd));
    }

    @PostMapping("/{id}/remove-product")
    public ResponseEntity<QuotationDTO> removeProductFromQuotation(@PathVariable Long id, @RequestBody ProductDTO productToRemove){
        return ResponseEntity.ok(quotationService.removeProductFromQuotation(id, productToRemove));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuotationDTO> update(@PathVariable Long id, @RequestBody QuotationDTO quotationDTO){
        return ResponseEntity.ok(quotationService.update(id, quotationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        quotationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
