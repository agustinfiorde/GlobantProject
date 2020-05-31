package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.Emergency;
import com.gonzalitos.web.app.entities.Victim;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.EmergencyModel;
import com.gonzalitos.web.app.repositories.EmergencyRepository;
import com.gonzalitos.web.app.repositories.VictimRepository;


@Component("EmergencyConverter")
public class EmergencyConverter  extends OwnConverter<EmergencyModel, Emergency>{

	@Autowired
	private EmergencyRepository emergencyRepository;
	
	@Autowired
	private VictimRepository victimRepository;
	
	public EmergencyModel entidadToModelo(Emergency entity){
		EmergencyModel model = new EmergencyModel();
		try {
			BeanUtils.copyProperties(entity, model);
		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo de  Emegencia", e);
		}
		
		return model;
	}
	
	public Emergency modeloToEntidad(EmergencyModel model){
		Emergency emergency = new Emergency();
		if(model.getId() != null && !model.getId().isEmpty()){
			emergency = emergencyRepository.getOne(model.getId());
		}
		
		try {
			emergency.setAddress(model.getAddress());
//			emergency.setCity(model.getCity());
			if (emergency.getVictim() == null) {
				Victim victim = new Victim();
				victim.setName(model.getVictim().getName());
				victim.setLastName(model.getVictim().getLastName());
				victim.setDni(model.getVictim().getDni());
				victim.setMembership(new Date());
				victim.setPhone(model.getVictim().getPhone());
				victim = victimRepository.save(victim);
				emergency.setVictim(victim);
				
			}else {
				emergency.getVictim().setName(model.getVictim().getName());
				emergency.getVictim().setLastName(model.getVictim().getLastName());
				emergency.getVictim().setDni(model.getVictim().getDni());
				emergency.getVictim().setMembership(new Date());
				emergency.getVictim().setPhone(model.getVictim().getPhone());
			}
			
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Agresor en entidad", e);
		}
		
		return emergency;
	}
	
	public List<EmergencyModel> entidadesToModelos(List<Emergency> financiamientos ){
		List<EmergencyModel> model = new ArrayList<>();
		for(Emergency a : financiamientos){
			model.add(entidadToModelo(a));
		}
		return model;
	}

	@Override
	public Emergency modelToEntity(EmergencyModel m) throws WebException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmergencyModel entityToModel(Emergency e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emergency> modelsToEntities(List<EmergencyModel> m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmergencyModel> entitiesToModels(List<Emergency> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject entityTOJSON(Emergency e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray entitiesTOJSON(List<Emergency> e) {
		// TODO Auto-generated method stub
		return null;
	}
}
