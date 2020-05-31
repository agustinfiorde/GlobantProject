package com.gonzalitos.web.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gonzalitos.web.app.entities.Victim;

@Repository
public interface VictimRepository extends JpaRepository<Victim, String>, PagingAndSortingRepository<Victim, String>  {

	@Query("SELECT a from Victim a WHERE a.remove IS NOT NULL")
	public Page<Victim> searchRemoved(Pageable pageable);

	@Query("SELECT a from Victim a")
	public Page<Victim> searchAll(Pageable pageable);

	@Query("SELECT a from Victim a WHERE a.remove IS NULL AND a.name LIKE :name")
	public Page<Victim> searchActivesByName(Pageable pageable, @Param("name") String name);
	
	@Query("SELECT a from Victim a WHERE a.remove IS NULL")
	public Page<Victim> searchActives(Pageable pageable);

	@Query("SELECT a from Victim a WHERE a.remove IS NULL ORDER BY a.name")
	public List<Victim> searchActives();
	
	@Query("SELECT a from Victim a WHERE a.remove IS NULL "
			+ "AND a.name LIKE :q "
			+ "OR a.lastName LIKE :q "
			+ "OR a.dni LIKE :q "
			+ "OR a.phone LIKE :q ")
	public Page<Victim> searchActives(Pageable pageable, @Param("q") String q);
	
}
