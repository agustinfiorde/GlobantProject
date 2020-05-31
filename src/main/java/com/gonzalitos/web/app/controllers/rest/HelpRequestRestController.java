package com.gonzalitos.web.app.controllers.rest;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.AggressionTypeModel;
import com.gonzalitos.web.app.models.AggressorModel;
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.models.RelationshipModel;
import com.gonzalitos.web.app.models.VictimModel;
import com.gonzalitos.web.app.services.AggressionTypeService;
import com.gonzalitos.web.app.services.HelpRequestService;
import com.gonzalitos.web.app.services.RelationshipService;

@RestController
@RequestMapping("/api/helprequest")
public class HelpRequestRestController {
	
	@Autowired
	private HelpRequestService helpRequestService;

	@Autowired
	private AggressionTypeService aggressionTypeService; 

	@Autowired
	private RelationshipService relationshipService; 

	@PostMapping("/save")
	public ResponseEntity<HelpRequestModel> saveHelRequest(
			@ModelAttribute("helpRequest") JSONObject helpRequestModel,
			@ModelAttribute("victim") JSONObject victimModel,
			@ModelAttribute("agressor") JSONObject aggressorModel) throws WebException{
		HelpRequestModel helpRequest = null;
		return new ResponseEntity<HelpRequestModel>(helpRequest, HttpStatus.OK);
	}
	
	@GetMapping("/serchaggressionTypes")
	public ResponseEntity<List<AggressionTypeModel>> serchAggressionTypes(){
		List<AggressionTypeModel> listOfAggression = aggressionTypeService.toListModel();
		return new ResponseEntity<List<AggressionTypeModel>>(listOfAggression, HttpStatus.OK);
	}
	
	@GetMapping("/serchrelationship")
	public ResponseEntity<List<RelationshipModel>> relationshipSearch(){
		List<RelationshipModel> listRelationships = relationshipService.toListModel();
		return new ResponseEntity<List<RelationshipModel>>(listRelationships, HttpStatus.OK);
	}
}
