package com.gonzalitos.web.app.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*", methods= {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/helprequest")
public class HelpRequestRestController {

	@PostMapping("/caca")
	public ResponseEntity<Integer> ordenFormulario(@RequestParam(required = true) String id) {
		Integer orden = 123;
		return new ResponseEntity<>(orden, HttpStatus.OK);
	}
	
}
