package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.interval.AgrestiCoullInterval;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.AggressionType;
import com.gonzalitos.web.app.entities.Aggressor;
import com.gonzalitos.web.app.entities.HelpRequest;
import com.gonzalitos.web.app.entities.Relationship;
import com.gonzalitos.web.app.entities.Victim;
import com.gonzalitos.web.app.models.AggressionTypeModel;
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.repositories.AggressionTypeRepository;
import com.gonzalitos.web.app.repositories.AggressorRepository;
import com.gonzalitos.web.app.repositories.HelpRequestRepository;
import com.gonzalitos.web.app.repositories.RelationshipRepository;
import com.gonzalitos.web.app.repositories.VictimRepository;
import com.gonzalitos.web.app.services.AggressorService;
import com.gonzalitos.web.app.services.RelationshipService;
import com.gonzalitos.web.app.services.VictimService;

@Component
public class HelpRequestConverter extends OwnConverter<HelpRequestModel, HelpRequest>{
	
	@Autowired
	private HelpRequestRepository helpRequestRepository;
	
	@Autowired 
	private AggressionTypeRepository aggressionTypeRepository;
	
	@Autowired 
	private VictimRepository victimService;
	
	@Autowired 
	private AggressorRepository aggressorRepo;
	
	@Autowired 
	private RelationshipRepository relationShipRepository;
	
	public HelpRequestModel entityToModel(HelpRequest entity) {
		HelpRequestModel model = new HelpRequestModel();
		try {
			BeanUtils.copyProperties(entity, model);
//			if (entity.getTypesOfViolences()!=null) {
//				model.setTypesOfViolences(aggressionTypeConverter.entitiesToModels(entity.getTypesOfViolences()));
//			}
//			if (entity.getVictim()!=null) {
//				model.setVictim(victimConverter.entityToModel(entity.getVictim()));
//			}
//			if (entity.getAggressor()!=null) {
//				model.setAggressor(aggressorConverter.entityToModel(entity.getAggressor()));
//			}
//			if (entity.getRelationship()!=null) {
//				model.setRelationship(relationShipConverter.entityToModel(entity.getRelationship()));
//			}
		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo del Pedido de ayuda", e);
		}

		return model;
	}

	public HelpRequest modelToEntity(HelpRequestModel model) {
		HelpRequest entity = new HelpRequest();
		if (model.getId() != null && !model.getId().isEmpty()) {
			entity = helpRequestRepository.getOne(model.getId());
		}

		try {
			BeanUtils.copyProperties(model, entity);
			
			if (model.getVictim() != null) {
				Victim victim = victimService.findByDni(model.getVictim().getDni());
				entity.setVictim(victim);
			}
			
			if (model.getAggressor() != null) {
				Aggressor aggressor = aggressorRepo.findByDni(model.getAggressor().getDni());
				entity.setAggressor(aggressor);
			}
			
			if (model.getRelationship() != null) {
				Relationship relationship = relationShipRepository.getOne(model.getRelationship().getId());
				entity.setRelationship(relationship);
			}
			
			if (model.getTypesOfViolences() != null) {
				List<AggressionType> aggressionTypes = new ArrayList<>();
				for (AggressionTypeModel aggression: model.getTypesOfViolences()) {
					AggressionType aggressionType = aggressionTypeRepository.getOne(aggression.getId());
					aggressionTypes.add(aggressionType);
				}
				entity.setTypesOfViolences(aggressionTypes);
			}
			
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Pedido de ayuda en entidad", e);
		}

		return entity;
	}

	public List<HelpRequestModel> entitiesToModels(List<HelpRequest> financiamientos) {
		List<HelpRequestModel> model = new ArrayList<>();
		for (HelpRequest a : financiamientos) {
			model.add(entityToModel(a));
		}
		return model;
	}

	public JSONArray entitiesTOJSON(List<HelpRequest> aggresors) {
		JSONArray result = new JSONArray();
		try {
			for (HelpRequest a : aggresors) {
				result.put(entityTOJSON(a));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	public JSONObject entityTOJSON(HelpRequest helpRequest) {
		JSONObject object = new JSONObject();
		
		try {
			object.put("id", helpRequest.getId());
			object.put("address", helpRequest.getAddress());
			object.put("factTime", helpRequest.getFactTimeString());
//			object.put("typesOfViolences", aggressionTypeConverter.entitiesTOJSON(helpRequest.getTypesOfViolences()));
//			object.put("victim", victimConverter.entityTOJSON(helpRequest.getVictim()));
//			object.put("aggressor", aggressorConverter.entityTOJSON(helpRequest.getAggressor()));
//			object.put("relationShip", relationShipConverter.entityTOJSON(helpRequest.getRelationship()));
			object.put("description", helpRequest.getDescription());
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<HelpRequest> modelsToEntities(List<HelpRequestModel> m) {
		// TODO Auto-generated method stub
		return null;
	}
}
