package com.gonzalitos.web.app.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.gonzalitos.web.app.entities.File;
import com.gonzalitos.web.app.repositories.FileRepository;

public class FileService {

	@Autowired
	private FileRepository fileRepository;
	
	public void save(byte[] photo) {
		File file = new File();
		
		file.setPhoto(photo);
		fileRepository.save(file);
		
	}
}
