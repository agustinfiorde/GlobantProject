package com.gonzalitos.web.app.controllers.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.EmergencyModel;
import com.gonzalitos.web.app.services.EmergencyService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/api/emergency")
public class EmergencyRestController {

	@Autowired
	private EmergencyService emergencyService;
	
	@PostMapping("/save")
	public ResponseEntity<String> saveEmergency(
			@RequestBody EmergencyModel emergencyModel, HttpServletRequest request) throws WebException{
		try {
			String ipAddress = request.getHeader("X-FORWARDED-FOR");  
			if (ipAddress == null) {  
			    ipAddress = request.getRemoteAddr();  
			}
			 emergencyService.save(emergencyModel, ipAddress);
			return new ResponseEntity<>("", HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
