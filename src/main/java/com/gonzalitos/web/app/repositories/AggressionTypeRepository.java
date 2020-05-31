package com.gonzalitos.web.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gonzalitos.web.app.entities.AggressionType;

@Repository
public interface AggressionTypeRepository extends JpaRepository<AggressionType, String>, PagingAndSortingRepository<AggressionType, String>  {

	@Query("SELECT a from AggressionType a WHERE a.remove IS NOT NULL")
	public Page<AggressionType> searchRemoved(Pageable pageable);

	@Query("SELECT a from AggressionType a")
	public Page<AggressionType> searchAll(Pageable pageable);

	@Query("SELECT a from AggressionType a WHERE a.remove IS NULL AND a.name LIKE :name")
	public Page<AggressionType> searchActivesByName(Pageable pageable, @Param("name") String name);
	
	@Query("SELECT a from AggressionType a WHERE a.remove IS NULL")
	public List<AggressionType> searchActivesModel();
	
	@Query("SELECT a from AggressionType a WHERE a.remove IS NULL")
	public Page<AggressionType> searchActives(Pageable pageable);

	@Query("SELECT a from AggressionType a WHERE a.remove IS NULL ORDER BY a.name")
	public List<AggressionType> searchActives();
	
	@Query("SELECT a from AggressionType a WHERE a.remove IS NULL AND a.name LIKE :name")
	public Page<AggressionType> searchActives(Pageable pageable, @Param("name") String name);
	
}
