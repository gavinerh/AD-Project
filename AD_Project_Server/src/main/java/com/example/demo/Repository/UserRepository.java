package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserCredential;

@Repository
public interface UserRepository extends CrudRepository<UserCredential, Long> {
	List<UserCredential> findAll();
	
	UserCredential getById(Long id);
	
	@Query("SELECT u FROM UserCredential u WHERE u.email = :email")
	UserCredential findUserByEmail(@Param("email") String email);
	

	void delete(UserCredential user);
	
}
