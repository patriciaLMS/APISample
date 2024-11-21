package br.edu.atitus.apisample.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.apisample.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>{
	// Query Method
	boolean existsByEmail(String email);

}