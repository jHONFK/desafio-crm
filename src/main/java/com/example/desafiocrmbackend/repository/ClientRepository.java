package com.example.desafiocrmbackend.repository;

import com.example.desafiocrmbackend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
