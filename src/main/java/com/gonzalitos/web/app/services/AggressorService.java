package com.gonzalitos.web.app.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		Aggressor aggressor = aggressorRepository.getOne(id);
		if (aggressor.getRemove() == null) {
			aggressor.setRemove(new Date());
			aggressor = aggressorRepository.save(aggressor);
		} else {
			throw new WebException("El agresor que intenta eliminar ya se encuentra dado de baja.");
		}
		return aggressor;
	}

//	public Page<Aggressor> toList(Pageable paginable, String q) {
//		return aggressorRepository.findActives(paginable, "%" + q + "%");
//	}

	public Page<Aggressor> toList(Pageable paginable) {
		return aggressorRepository.findActives(paginable);
	}
	
	public List<Aggressor> toList() {
		return aggressorRepository.findActives();
	}
	
	public AggressorModel search(String id) {
		Aggressor aggressor = aggressorRepository.getOne(id);
		return aggressorConverter.entityToModel(aggressor);
	}
		
}

