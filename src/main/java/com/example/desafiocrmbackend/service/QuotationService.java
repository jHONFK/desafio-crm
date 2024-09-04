package com.example.desafiocrmbackend.service;

import com.example.desafiocrmbackend.entity.Product;
import com.example.desafiocrmbackend.entity.Quotation;
import com.example.desafiocrmbackend.entity.dto.ClientDTO;
import com.example.desafiocrmbackend.entity.dto.ProductDTO;
import com.example.desafiocrmbackend.entity.dto.QuotationDTO;
import com.example.desafiocrmbackend.repository.ClientRepository;
import com.example.desafiocrmbackend.repository.ProductRepository;
import com.example.desafiocrmbackend.repository.QuotationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuotationService {

    @Autowired
    private final QuotationRepository quotationRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final ClientService clientService;

    @Autowired
    private final ProductService productService;

    public QuotationService(QuotationRepository quotationRepository, ProductRepository productRepository, ClientRepository clientRepository,
                            ClientService clientService,
                            ProductService productService) {
        this.quotationRepository = quotationRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.productService = productService;
    }

    public List<QuotationDTO> findAll(){
        List<Quotation> quotations = quotationRepository.findAll();
        List<QuotationDTO> quotationDTOs = new ArrayList<>();
        for (Quotation quotation : quotations) {
            QuotationDTO quotationDTO = mapToQuotationDTO(quotation);
            quotationDTOs.add(quotationDTO);
        }
        return quotationDTOs;
    }

    public QuotationDTO findById(Long id) {
        Quotation quotation = quotationRepository.findById(id).orElseThrow(() -> new RuntimeException("Quotation not found"));
        return mapToQuotationDTO(quotation);
    }

    @Transactional
    public QuotationDTO addProductToQuotation(Long id, ProductDTO productToAdd) {
        Quotation quotation = mapToQuotationEntity(findById(id));
        quotation.getProducts().add(productService.mapToProductEntity(productToAdd));
        return mapToQuotationDTO(quotationRepository.save(quotation));
    }

    @Transactional
    public QuotationDTO removeProductFromQuotation(Long id, ProductDTO productToRemove) {
        Quotation quotation = mapToQuotationEntity(findById(id));
        Product productEntityToRemove = productService.mapToProductEntity(productToRemove);
        List<Product> products = quotation.getProducts();

        for (Product product : products) {
            if (product.getId().equals(productEntityToRemove.getId())) {
                products.remove(product);
                break;
            }
        }

        return mapToQuotationDTO(quotationRepository.save(quotation));
    }

    @Transactional
    public QuotationDTO save(QuotationDTO quotationDTO) {
        validateQuotationDTO(quotationDTO);
        return mapToQuotationDTO(quotationRepository.save(mapToQuotationEntity(quotationDTO)));
    }

    private void validateQuotationDTO(QuotationDTO quotationDTO) {
        validateProductsOfQuotation(quotationDTO.getProducts());
        validateClientOfQuotation(quotationDTO.getClient());
    }

    private void validateProductsOfQuotation(List<ProductDTO> products){
        for(ProductDTO product : products){
            Long id = product.getId();
            if(!productRepository.existsById(id)){
                throw new RuntimeException("Quotation not found");
            }
        }
    }

    private void validateClientOfQuotation(ClientDTO client){
        if(!clientRepository.existsById(client.getId())){
            throw new RuntimeException("Client not found");
        }
    }

    @Transactional
    public QuotationDTO update(Long id, QuotationDTO quotationDTO) {
        QuotationDTO quotationToUpdate = findById(id);

        quotationToUpdate.setClient(quotationDTO.getClient());

        if(quotationDTO.getStatus() != null){
            quotationToUpdate.setStatus(quotationDTO.getStatus());
        }

        quotationToUpdate.setProducts(quotationDTO.getProducts());

        if(quotationDTO.getFinishedOn() != null){
            quotationToUpdate.setFinishedOn(quotationDTO.getFinishedOn());
        }

        return save(quotationToUpdate);
    }

    public void deleteById(Long id) {
        quotationRepository.deleteById(id);
    }

    private QuotationDTO mapToQuotationDTO(Quotation quotation){
        QuotationDTO quotationDTO = new QuotationDTO();
        quotationDTO.setId(quotation.getId());
        quotationDTO.setClient(clientService.mapToClientDTO(quotation.getClient()));
        quotationDTO.setStatus(quotation.getStatus());
        quotationDTO.setProducts(productService.mapToProductsDTOs(quotation.getProducts()));
        quotationDTO.setCreatedAt(quotation.getCreatedAt());
        quotationDTO.setFinishedOn(quotation.getFinishedOn());
        return quotationDTO;
    }

    private Quotation mapToQuotationEntity(QuotationDTO quotationDTO){
        Quotation quotation = new Quotation();
        quotation.setId(quotationDTO.getId());
        quotation.setClient(clientService.mapToClientEntity(quotationDTO.getClient()));
        quotation.setStatus(quotationDTO.getStatus());
        quotation.setProducts(productService.mapToProductsEntities(quotationDTO.getProducts()));
        quotation.setCreatedAt(quotationDTO.getCreatedAt());
        quotation.setFinishedOn(quotationDTO.getFinishedOn());
        return quotation;
    }
}
