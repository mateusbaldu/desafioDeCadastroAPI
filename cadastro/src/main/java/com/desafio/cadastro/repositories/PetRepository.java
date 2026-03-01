package com.desafio.cadastro.repositories;

import com.desafio.cadastro.models.Pets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pets, Integer>, JpaSpecificationExecutor<Pets> {
}
