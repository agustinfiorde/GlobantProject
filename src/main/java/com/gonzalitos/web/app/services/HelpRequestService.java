package com.gonzalitos.web.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gonzalitos.web.app.converters.HelpRequestConverter;
import com.gonzalitos.web.app.entities.Aggressor;
import com.gonzalitos.web.app.entities.HelpRequest;
import com.gonzalitos.web.app.entities.Victim;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.AggressionTypeModel;
import com.gonzalitos.web.app.models.AggressorModel;
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.models.RelationshipModel;
import com.gonzalitos.web.app.models.VictimModel;
import com.gonzalitos.web.app.repositories.HelpRequestRepository;

@Service
public class HelpRequestService {

	@Autowired
	private HelpRequestConverter helpRequestConverter;

	@Autowired
	private HelpRequestRepository helpRequestRepository;
	
	@Autowired
	private AggressionTypeService aggressionTypeService;
	
	@Autowired
	private VictimService victimService;
	
	@Autowired
	private AggressorService aggressorService;
	
	@Autowired
	private RelationshipService relationshipService;
	
	public HelpRequestModel saveHelpRequest(JSONObject json) throws WebException, JSONException {
		 HelpRequestModel requestModel = new HelpRequestModel();
		 
		 requestModel.setAddress(json.getString("address"));
		 requestModel.setFactTimeString(json.getString("factTimeString"));
		 requestModel.setDescription(json.getString("description"));
		 
		 List<AggressionTypeModel> aggression = new ArrayList<>();
		 
		 JSONArray aggressions = json.getJSONArray("typesOfViolences");
		 
		 for (int i = 0; i < aggressions.length(); i++) {
			AggressionTypeModel model = aggressionTypeService.search(aggressions.get(i).toString());
			aggression.add(model);
		 }
		 
		 requestModel.setTypesOfViolences(aggression);
		 
		 VictimModel victimModel = victimService.searchByDni(json.getString("dni"));
		 
		 if (victimModel.getDni() != null) {
			 requestModel.setVictim(victimModel);
		 } else {
			 VictimModel model = new VictimModel();
			 model.setName(json.getString("name"));
			 model.setLastName(json.getString("lastName"));
			 model.setDateBornString(json.getString("dateBornString"));
			 model.setDni(json.getString("dni"));
			 model.setPhone(json.getString("phone"));
			 model.setEmail(json.getString("email"));
			 model.setChildren(json.getInt("children"));
			 model.setWhistleblower(json.getBoolean("whistleblower"));
			 model.setIpAddress(null);
			 victimService.save(model);
			 requestModel.setVictim(model);
		 }
		 
		 AggressorModel aggressorModel = aggressorService.searchByDni(json.getString("dniAggressor"));
		 
		 if (aggressorModel.getDni() != null) {
			requestModel.setAggressor(aggressorModel);
		 } else {
			 AggressorModel aggressor = new AggressorModel();
			 aggressor.setDni(json.getString("dniAggressor"));
			 aggressor.setName(json.getString("nameAggressor"));
			 aggressor.setLastName(json.getString("lastNameAggressor"));
			 aggressor.setSecondName(json.getString("secondNameAggressor"));
			 aggressorService.save(aggressor);
			 requestModel.setAggressor(aggressor);
		 }
		 
		 RelationshipModel relationshipModel = relationshipService.search(json.getString("relationship"));
		 
		 requestModel.setRelationship(relationshipModel);
		 
		 save(requestModel);
		 return requestModel;
	}
	
	@Transactional
	public HelpRequest save(HelpRequestModel model) throws WebException {
		HelpRequest helpRequest = helpRequestConverter.modelToEntity(model);
		if (helpRequest.getRemove() != null) {
			throw new WebException("El agresor que intenta modificar se encuentra dada de baja.");
		}
		return helpRequestRepository.save(helpRequest);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public HelpRequest delete(String id) throws WebException {
		HelpRequest financiamiento = helpRequestRepository.getOne(id);
		if (financiamiento.getRemove() == null) {
			financiamiento.setRemove(new Date());
			financiamiento = helpRequestRepository.save(financiamiento);
		} else {
			throw new WebException("El agresor que intenta eliminar ya se encuentra dado de baja.");
		}
		return financiamiento;
	}
	
	public HelpRequestModel search(String id) {
		HelpRequest helpRequest = helpRequestRepository.getOne(id);
		return helpRequestConverter.entityToModel(helpRequest);
	}
		
}