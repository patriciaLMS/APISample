package br.edu.atitus.apisample.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.apisample.entities.RegisterEntity;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity, UUID>{

}