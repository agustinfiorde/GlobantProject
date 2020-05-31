package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.Victim;
import com.gonzalitos.web.app.models.VictimModel;
import com.gonzalitos.web.app.repositories.VictimRepository;

@Component("VictimConverter")
public class VictimConverter extends OwnConverter<VictimModel, Victim> {

	@Autowired
	private VictimRepository victimRepository;

	@Autowired
	private AggressorConverter aggressorConverter;

	public VictimModel entityToModel(Victim entity) {
		VictimModel model = new VictimModel();
		try {
			BeanUtils.copyProperties(entity, model);
//			if (entity.getAggressors() != null) {
//				model.setAggressorsModel(aggressorConverter.entitiesToModels(entity.getAggressors()));
//			}
		} catch (Exception e) {
			log.error("Error al convertir la entity en el modelo del Agresor", e);
		}

		return model;
	}

	public Victim modelToEntity(VictimModel model) {
		Victim victim = new Victim();
		if (model.getId() != null && !model.getId().isEmpty()) {
			victim = victimRepository.getOne(model.getId());
		}

		try {
			BeanUtils.copyProperties(model, victim);
//			if (model.getAggressorsModel()!=null) {
//				victim.setAggressors(aggressorConverter.modelsToEntities(model.getAggressorsModel()));
//			}
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Agresor en entity", e);
		}

		return victim;
	}

	public List<VictimModel> entitiesToModels(List<Victim> victims) {
		List<VictimModel> model = new ArrayList<>();
		for (Victim a : victims) {
			model.add(entityToModel(a));
		}
		return model;
	}

	@Override
	public List<Victim> modelsToEntities(List<VictimModel> m) {
		// TODO Auto-generated method stub
		return null;
	}

	//Necessary converter in pwa applications
	public JSONArray entitiesTOJSON(List<Victim> aggresors) {
		JSONArray result = new JSONArray();
		try {
			for (Victim a : aggresors) {
				result.put(entityTOJSON(a));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	//Necessary converter in pwa applications
	public JSONObject entityTOJSON(Victim victim) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", victim.getId());
			object.put("name", victim.getName());
			object.put("lastName", victim.getLastName());
			object.put("dateBorn", victim.getDateBorn());
			object.put("phone", victim.getPhone());
			object.put("email", victim.getEmail());
			object.put("children", victim.getChildren());
			object.put("whistleblower", victim.getWhistleblower());
			object.put("ipAddress", victim.getIpAddress());
//			object.put("aggressors", aggressorConverter.entitiesTOJSON(victim.getAggressors()));
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
