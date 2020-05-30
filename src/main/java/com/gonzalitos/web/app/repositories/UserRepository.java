package com.gonzalitos.web.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gonzalitos.web.app.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, String>, PagingAndSortingRepository<User, String>  {

	@Query("SELECT a from User a WHERE a.remove IS NOT NULL")
	public Page<User> searchRemoved(Pageable pageable);

	@Query("SELECT a from User a")
	public Page<User> searchAll(Pageable pageable);

	@Query("SELECT a from User a WHERE a.remove IS NULL AND a.email LIKE :email")
	public User searchByEmail(@Param("email") String email);
	
	@Query("SELECT a from User a WHERE a.remove IS NULL AND a.name LIKE :name")
	public Page<User> searchActives(Pageable pageable, @Param("name") String name);
	
	@Query("SELECT a from User a WHERE a.remove IS NULL")
	public Page<User> searchActives(Pageable pageable);

	@Query("SELECT a from User a WHERE a.remove IS NULL ORDER BY a.name")
	public List<User> searchActives();
	
}
