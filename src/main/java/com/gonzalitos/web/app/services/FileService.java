package com.gonzalitos.web.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gonzalitos.web.app.entities.File;
import com.gonzalitos.web.app.entities.HelpRequest;
import com.gonzalitos.web.app.repositories.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;
	
	public File save(byte[] photo, HelpRequest helpRequest) {
		File file = new File();
		
		file.setPhoto(photo);
		file.setHelpRequest(helpRequest);
		
		return fileRepository.save(file);
		
	}
}
