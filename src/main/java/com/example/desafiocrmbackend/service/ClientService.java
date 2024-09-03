package com.example.desafiocrmbackend.service;

import com.example.desafiocrmbackend.entity.Client;
import com.example.desafiocrmbackend.entity.dto.ClientDTO;
import com.example.desafiocrmbackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDTO> findAll(){
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        for (Client client : clients) {
            ClientDTO clientDTO = getClientDTO(client);
            clientDTOs.add(clientDTO);
        }
        return clientDTOs;
    }

    public ClientDTO findById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        return getClientDTO(client);
    }

    public ClientDTO save(ClientDTO clientDTO){
        return getClientDTO(clientRepository.save(getClient(clientDTO)));
    }

    public ClientDTO update(Long id, ClientDTO clientDTO){
        ClientDTO clientToUpdate = findById(id);

        clientToUpdate.setName(clientDTO.getName());
        clientToUpdate.setEmail(clientDTO.getEmail());
        clientToUpdate.setCpf(clientDTO.getCpf());


        return save(clientToUpdate);
    }

    public void deleteById(Long id){
        clientRepository.deleteById(id);
    }

    public ClientDTO getClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setCpf(client.getCpf());
        clientDTO.setCreatedAt(client.getCreatedAt());
        return clientDTO;
    }

    public Client getClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setCpf(clientDTO.getCpf());
        client.setCreatedAt(clientDTO.getCreatedAt());
        return client;
    }


}
