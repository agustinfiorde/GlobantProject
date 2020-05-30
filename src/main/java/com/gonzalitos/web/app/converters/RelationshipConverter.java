package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.Relationship;
import com.gonzalitos.web.app.models.RelationshipModel;
import com.gonzalitos.web.app.repositories.RelationshipRepository;

@Component("RelationshipConverter")
public class RelationshipConverter extends OwnConverter<RelationshipModel, Relationship> {

	@Autowired
	private RelationshipRepository relationShipRepository;

	public RelationshipModel entityToModel(Relationship entity) {
		RelationshipModel model = new RelationshipModel();
		try {
			BeanUtils.copyProperties(entity, model);
		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo de vinculo", e);
		}

		return model;
	}

	public Relationship modelToEntity(RelationshipModel model) {
		Relationship relationShip = new Relationship();
		if (model.getId() != null && !model.getId().isEmpty()) {
			relationShip = relationShipRepository.getOne(model.getId());
		}
		try {
			BeanUtils.copyProperties(model, relationShip);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del vinculo en entidad", e);
		}

		return relationShip;
	}

	public List<RelationshipModel> entitiesToModels(List<Relationship> relationShips) {
		List<RelationshipModel> model = new ArrayList<>();
		for (Relationship e : relationShips) {
			model.add(entityToModel(e));
		}
		return model;
	}
	
	@Override
	public List<Relationship> modelsToEntities(List<RelationshipModel> models) {
		List<Relationship> entities = new ArrayList<>();
		for (RelationshipModel m : models) {
			entities.add(modelToEntity(m));
		}
		return entities;
	}

	//Necessary converter in pwa applications
	public JSONArray entitiesTOJSON(List<Relationship> aggresors) {
		JSONArray result = new JSONArray();
		try {
			for (Relationship a : aggresors) {
				result.put(entityTOJSON(a));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
	
	//Necessary converter in pwa applications
	public JSONObject entityTOJSON(Relationship relationShip) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", relationShip.getId());
			object.put("name", relationShip.getName());
			object.put("description", relationShip.getDescription());
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}