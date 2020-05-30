package com.gonzalitos.web.app.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gonzalitos.web.app.converters.AggressorConverter;
import com.gonzalitos.web.app.entities.Aggressor;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.AggressorModel;
import com.gonzalitos.web.app.repositories.AggressorRepository;

@Service
public class AggressorService {

	@Autowired
	private AggressorConverter aggressorConverter;

	@Autowired
	private AggressorRepository aggressorRepository;

	@Transactional
	public Aggressor save(AggressorModel model) throws WebException {
		Aggressor aggressor = aggressorConverter.modelToEntity(model);
		if (aggressor.getRemove() != null) {
			throw new WebException("El agresor que intenta modificar se encuentra dada de baja.");
		}
		if (aggressor.getName() == null || aggressor.getName().isEmpty()) {
			throw new WebException("El nombre del agresor no puede estar vacio.");
		}
		return aggressorRepository.save(aggressor);
	}

	@Transactional
	public Aggressor delete(String id) throws WebException {
		Aggressor financiamiento = aggressorRepository.getOne(id);
		if (financiamiento.getRemove() == null) {
			financiamiento.setRemove(new Date());
			financiamiento = aggressorRepository.save(financiamiento);
		} else {
			throw new WebException("El agresor que intenta eliminar ya se encuentra dado de baja.");
		}
		return financiamiento;
	}
}
