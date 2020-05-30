package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.HelpRequest;
import com.gonzalitos.web.app.models.HelpRequestModel;

@Component
public class HelpRequestConverter extends OwnConverter<HelpRequestModel, HelpRequest>{
	
	@Autowired
	private HelpRequestRepository helpRequestRepository;
	
	@Autowired 
	private AggressionTypeConverter aggressionTypeConverter;
	
	@Autowired 
	private VictimConverter victimConverter;
	
	@Autowired 
	private AggressorConverter aggressorConverter;
	
	@Autowired 
	private RelationshipConverter relationShipConverter;
	
	public HelpRequestModel entityToModel(HelpRequest entity) {
		HelpRequestModel model = new HelpRequestModel();
		try {
			BeanUtils.copyProperties(entity, model);
			if (entity.getTypesOfViolences()!=null) {
				model.setTypesOfViolences(aggressionTypeConverter.entitiesToModels(entity.getTypesOfViolences()));
			}
			if (entity.getVictim()!=null) {
				model.setVictim(victimConverter.entityToModel(entity.getVictim()));
			}
			if (entity.getAggressor()!=null) {
				model.setAggressor(aggressorConverter.entityToModel(entity.getAggressor()));
			}
			if (entity.getRelationship()!=null) {
				model.setRelationship(relationShipConverter.entityToModel(entity.getRelationship()));
			}
		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo del Pedido de ayuda", e);
		}

		return model;
	}

	public HelpRequest modelToEntity(HelpRequestModel model) {
		HelpRequest financiamiento = new HelpRequest();
		if (model.getId() != null && !model.getId().isEmpty()) {
			financiamiento = helpRequestRepository.getOne(model.getId());
		}

		try {
			BeanUtils.copyProperties(model, financiamiento);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Pedido de ayuda en entidad", e);
		}

		return financiamiento;
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
			object.put("factTime", helpRequest.getFactTime());
			object.put("typesOfViolences", aggressionTypeConverter.entitiesTOJSON(helpRequest.getTypesOfViolences()));
			object.put("victim", victimConverter.entityTOJSON(helpRequest.getVictim()));
			object.put("aggressor", aggressorConverter.entityTOJSON(helpRequest.getAggressor()));
			object.put("relationShip", relationShipConverter.entityTOJSON(helpRequest.getRelationship()));
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
