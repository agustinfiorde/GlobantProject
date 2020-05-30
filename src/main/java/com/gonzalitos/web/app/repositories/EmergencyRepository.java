package com.gonzalitos.web.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.gonzalitos.web.app.entities.Emergency;


public interface EmergencyRepository extends JpaRepository<Emergency, String>, PagingAndSortingRepository<Emergency, String> {

	
	@Query("SELECT a from Emergency a WHERE a.remove IS NOT NULL")
	public Page<Emergency> searchRemoved(Pageable pageable);

	@Query("SELECT a from Emergency a")
	public Page<Emergency> searchAll(Pageable pageable);

	@Query("SELECT a from Emergency a WHERE a.remove IS NULL")
	public Page<Emergency> searchActives(Pageable pageable);
	
	@Query("SELECT a from Emergency a WHERE a.remove IS NULL AND a.registered LIKE :registered")
	public Page<Emergency> searchActives(Pageable pageable, @Param("registered") String registered);
	
	@Query("SELECT a from Emergency a WHERE a.remove IS NULL")
	public List<Emergency> searchActives();
	
}
