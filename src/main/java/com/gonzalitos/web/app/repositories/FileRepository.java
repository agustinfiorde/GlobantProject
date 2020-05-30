package com.gonzalitos.web.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gonzalitos.web.app.entities.File;

public interface FileRepository extends JpaRepository<File, String>, PagingAndSortingRepository<File, String>  {

}
