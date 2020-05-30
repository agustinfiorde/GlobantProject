package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.Aggressor;
import com.gonzalitos.web.app.models.AggressorModel;
import com.gonzalitos.web.app.repositories.AggressorRepository;


@Component("AggressorConverter")
public class AggressorConverter extends OwnConverter<AggressorModel, Aggressor> {

	@Autowired
	private AggressorRepository aggressorRepository;

	public AggressorModel entityToModel(Aggressor entity) {
		AggressorModel model = new AggressorModel();
		try {
			BeanUtils.copyProperties(entity, model);
		} catch (Exception e) {
			log.error("Error al convertir la entity en el modelo del Agresor", e);
		}

		return model;
	}

	public Aggressor modelToEntity(AggressorModel model) {
		Aggressor aggressor = new Aggressor();
		if (model.getId() != null && !model.getId().isEmpty()) {
			aggressor = aggressorRepository.getOne(model.getId());
		}
		try {
			BeanUtils.copyProperties(model, aggressor);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Agresor en entidad", e);
		}
		return aggressor;
	}

	public List<AggressorModel> entitiesToModels(List<Aggressor> aggressors) {
		List<AggressorModel> models = new ArrayList<>();
		for (Aggressor e : aggressors) {
			models.add(entityToModel(e));
		}
		return models;
	}
	
	@Override
	public List<Aggressor> modelsToEntities(List<AggressorModel> models) {
		List<Aggressor> entities = new ArrayList<>();
		for (AggressorModel m : models) {
			entities.add(modelToEntity(m));
		}
		return entities;
	}

	public JSONArray entitiesTOJSON(List<Aggressor> aggresors) {
		JSONArray result = new JSONArray();
		try {
			for (Aggressor a : aggresors) {
				result.put(entityTOJSON(a));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	public JSONObject entityTOJSON(Aggressor aggressor) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", aggressor.getId());
			object.put("name", aggressor.getName());
			object.put("secondName", aggressor.getSecondName());
			object.put("lastName", aggressor.getLastName());
			object.put("dni", aggressor.getDni());
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
