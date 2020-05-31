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
public interface HelpRequestRepository
		extends JpaRepository<HelpRequest, String>, PagingAndSortingRepository<HelpRequest, String> {

	@Query("SELECT a from HelpRequest a WHERE a.remove IS NOT NULL")
	public Page<HelpRequest> searchRemoved(Pageable pageable);

	@Query("SELECT a from HelpRequest a")
	public Page<HelpRequest> searchAll(Pageable pageable);

	@Query("SELECT a from HelpRequest a WHERE a.remove IS NULL")
	public Page<HelpRequest> searchActives(Pageable pageable);

	@Query("SELECT a from HelpRequest a WHERE a.remove IS NULL ORDER BY a.factTime")
	public List<HelpRequest> searchActives();

	@Query("SELECT h from HelpRequest h, IN(h.victim) v, IN(h.aggressor) a "
			+ "WHERE h.remove IS NULL "
			+ "AND v.name LIKE :q "
			+ "OR v.lastName LIKE :q "
			+ "OR v.dni LIKE :q "
			+ "OR v.phone LIKE :q "
			+ "OR v.email LIKE :q "
			+ "OR a.name LIKE :q "
			+ "OR a.secondName LIKE :q "
			+ "OR a.lastName LIKE :q "
			+ "OR a.dni LIKE :q "
			+ "OR h.factTime LIKE :q "
			+ "ORDER BY h.factTime ")
	public Page<HelpRequest> searchActives(Pageable pageable, @Param("q") String q);

}
