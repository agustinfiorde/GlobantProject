package com.gonzalitos.web.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gonzalitos.web.app.entities.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, String>, PagingAndSortingRepository<Relationship, String>  {

	@Query("SELECT a from Relationship a WHERE a.remove IS NOT NULL")
	public Page<Relationship> searchRemoved(Pageable pageable);

	@Query("SELECT a from Relationship a")
	public Page<Relationship> searchAll(Pageable pageable);

	@Query("SELECT a from Relationship a WHERE a.remove IS NULL AND a.name LIKE :name")
	public Page<Relationship> searchActivesByName(Pageable pageable, @Param("name") String name);
	
	@Query("SELECT a from Relationship a WHERE a.remove IS NULL")
	public Page<Relationship> searchActives(Pageable pageable);

	@Query("SELECT a from Relationship a WHERE a.remove IS NULL ORDER BY a.name")
	public List<Relationship> searchActives();
	
}

