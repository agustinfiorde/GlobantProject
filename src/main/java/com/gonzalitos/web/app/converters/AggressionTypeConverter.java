package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.AggressionType;
import com.gonzalitos.web.app.models.AggressionTypeModel;
import com.gonzalitos.web.app.repositories.AggressionTypeRepository;

@Component("AggressionTypeConverter")
public class AggressionTypeConverter extends OwnConverter<AggressionTypeModel, AggressionType> {

	@Autowired
	private AggressionTypeRepository aggressionTypeRepository;

	public AggressionTypeModel entityToModel(AggressionType entity) {
		AggressionTypeModel model = new AggressionTypeModel();
		try {
			BeanUtils.copyProperties(entity, model);
		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo de tipo de violencia", e);
		}

		return model;
	}

	public AggressionType modelToEntity(AggressionTypeModel model) {
		AggressionType aggressionType = new AggressionType();
		if (model.getId() != null && !model.getId().isEmpty()) {
			aggressionType = aggressionTypeRepository.getOne(model.getId());
		}

		try {
			BeanUtils.copyProperties(model, aggressionType);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del tipo de violencia en entidad", e);
		}

		return aggressionType;
	}

	public List<AggressionTypeModel> entitiesToModels(List<AggressionType> typesOfViolence) {
		List<AggressionTypeModel> models = new ArrayList<>();
		for (AggressionType a : typesOfViolence) {
			models.add(entityToModel(a));
		}
		return models;
	}
	
	@Override
	public List<AggressionType> modelsToEntities(List<AggressionTypeModel> models) {
		List<AggressionType> entities = new ArrayList<>();
		for (AggressionTypeModel m : models) {
			entities.add(modelToEntity(m));
		}
		return entities;
	}

	public JSONArray entitiesTOJSON(List<AggressionType> aggresors) {
		JSONArray result = new JSONArray();
		try {
			for (AggressionType a : aggresors) {
				result.put(entityTOJSON(a));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	public JSONObject entityTOJSON(AggressionType aggressionType) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", aggressionType.getId());
			object.put("name", aggressionType.getName());
			object.put("description", aggressionType.getDescription());
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
