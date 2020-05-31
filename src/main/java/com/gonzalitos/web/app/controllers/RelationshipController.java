package com.gonzalitos.web.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gonzalitos.web.app.entities.Relationship;
import com.gonzalitos.web.app.services.RelationshipService;

@Controller
@RequestMapping("/relationship")
public class RelationshipController {

	@Autowired
	private RelationshipService relationshipService;
	
	@GetMapping("/list")
	public String list(ModelMap modelo) {
//		Relationship relationship = relationshipService.findAll();
//		modelo.put("relationships", relationship);
		return "relationship-list.html";
	}
	
	@GetMapping("/form")
	public String form(ModelMap modelo) {
		Relationship relationship = new Relationship();
		modelo.put("relationship", relationship);
		return "relationship-form.html";
	}
	
}
