package com.gonzalitos.web.app.repositories;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gonzalitos.web.app.entities.Aggressor;

@Repository
public interface AggressorRepository extends JpaRepository<Aggressor, String>, PagingAndSortingRepository<Aggressor, String>  {

	@Query("SELECT a from Aggressor a WHERE a.remove IS NOT NULL")
	public Page<Aggressor> findRemoved(Pageable pageable);

	@Query("SELECT a from Aggressor a")
	public Page<Aggressor> findAll(Pageable pageable);

	@Query("SELECT a from Aggressor a WHERE a.remove IS NULL AND a.dni LIKE :dni")
	public Page<Aggressor> findActivesByDni(Pageable pageable, @Param("dni") String dni);
	
	@Query("SELECT a from Aggressor a WHERE a.remove IS NULL")
	public Page<Aggressor> findActives(Pageable pageable);

	@Query("SELECT a from Aggressor a WHERE a.remove IS NULL ORDER BY a.name")
	public List<Aggressor> findActives();
	

	
}
