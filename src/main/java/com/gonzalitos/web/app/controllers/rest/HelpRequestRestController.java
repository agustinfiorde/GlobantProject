package com.gonzalitos.web.app.controllers.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.AggressionTypeModel;
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.models.RelationshipModel;
import com.gonzalitos.web.app.services.AggressionTypeService;
import com.gonzalitos.web.app.services.HelpRequestService;
import com.gonzalitos.web.app.services.RelationshipService;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
	public ResponseEntity<HelpRequestModel> saveHelRequest(HttpServletRequest request) throws WebException, JSONException, IOException{
		String body = IOUtils.toString(request.getReader());
		JSONObject json = new JSONObject(body);
		HelpRequestModel helpRequest = helpRequestService.saveHelpRequest(json);
		return new ResponseEntity<HelpRequestModel>(helpRequest, HttpStatus.OK);
	}
	
	@GetMapping("/searchaggressiontypes")
	public ResponseEntity<List<AggressionTypeModel>> serchAggressionTypes(){
		List<AggressionTypeModel> listOfAggression = aggressionTypeService.toListModel();
		return new ResponseEntity<List<AggressionTypeModel>>(listOfAggression, HttpStatus.OK);
	}
	
	@GetMapping("/searchrelationship")
	public ResponseEntity<List<RelationshipModel>> relationshipSearch(){
		List<RelationshipModel> listRelationships = relationshipService.toListModel();
		return new ResponseEntity<List<RelationshipModel>>(listRelationships, HttpStatus.OK);
	}
}
