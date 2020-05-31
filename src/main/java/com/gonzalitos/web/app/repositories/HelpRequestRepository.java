package com.gonzalitos.web.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gonzalitos.web.app.entities.HelpRequest;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, String>, PagingAndSortingRepository<HelpRequest, String>  {

	@Query("SELECT a from HelpRequest a WHERE a.remove IS NOT NULL")
	public Page<HelpRequest> searchRemoved(Pageable pageable);

	@Query("SELECT a from HelpRequest a")
	public Page<HelpRequest> searchAll(Pageable pageable);
	
	@Query("SELECT a from HelpRequest a WHERE a.remove IS NULL")
	public Page<HelpRequest> searchActives(Pageable pageable);

	@Query("SELECT a from HelpRequest a WHERE a.remove IS NULL ORDER BY a.factTime")
	public List<HelpRequest> searchActives();
	
	@Query("SELECT a from HelpRequest a WHERE a.remove IS NULL AND a.victim LIKE :victim_id")
	public Page<HelpRequest> searchActives(Pageable pageable, @Param("victim_id") String victim_id);
	
//	@Query("SELECT a from HelpRequest a WHERE a.remove IS NULL AND a.victim LIKE :victim_id")
//	public Page<HelpRequest> searchActives(Pageable pageable, @Param("victim_id") String victim_id);
	
}
