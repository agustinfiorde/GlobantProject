package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.Emergency;
import com.gonzalitos.web.app.models.EmergencyModel;
import com.gonzalitos.web.app.repositories.EmergencyRepository;
import com.madresdepie.web.app.entities.;

@Component("EmergencyConverter")
public class EmergencyConverter  extends OwnConverter<EmergencyModel, Emergency>{

	@Autowired
	private EmergencyRepository emergencyRepository;
	
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
		Emergency financiamiento = new Emergency();
		if(model.getId() != null && !model.getId().isEmpty()){
			financiamiento = emergencyRepository.getOne(model.getId());
		}
		
		try {
			BeanUtils.copyProperties(model, financiamiento);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Agresor en entidad", e);
		}
		
		return financiamiento;
	}
	
	public List<EmergencyModel> entidadesToModelos(List<Emergency> financiamientos ){
		List<EmergencyModel> model = new ArrayList<>();
		for(Emergency a : financiamientos){
			model.add(entidadToModelo(a));
		}
		return model;
	}
}
