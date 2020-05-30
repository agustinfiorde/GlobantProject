package com.gonzalitos.web.app.services;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.gonzalitos.web.app.converters.AggressionTypeConverter;
import com.gonzalitos.web.app.entities.AggressionType;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.AggressionTypeModel;
import com.gonzalitos.web.app.repositories.AggressionTypeRepository;

@Service
public class AggressionTypeService {

	@Autowired
	private AggressionTypeConverter aggressionTypeConverter;

	@Autowired
	private AggressionTypeRepository aggressionTypeRepository;

	@Transactional
	public AggressionType save(AggressionTypeModel model) throws WebException {
		AggressionType aggressionType = aggressionTypeConverter.modelToEntity(model);
		if (aggressionType.getRemove() != null) {
			throw new WebException("El tipo de violencia que intenta modificar se encuentra dada de baja.");
		}
		if (aggressionType.getName() == null || aggressionType.getName().isEmpty()) {
			throw new WebException("El nombre del tipo de violencia no puede estar vacio.");
		}
		return aggressionTypeRepository.save(aggressionType);
	}

	@Transactional
	public AggressionType delete(String id) throws WebException {
		AggressionType financiamiento = aggressionTypeRepository.getOne(id);
		if (financiamiento.getRemove() == null) {
			financiamiento.setRemove(new Date());
			financiamiento = aggressionTypeRepository.save(financiamiento);
		} else {
			throw new WebException("El tipo de violencia que intenta eliminar ya se encuentra dado de baja.");
		}
		return financiamiento;
	}

	public Page<AggressionType> toList(Pageable paginable, String q) {
		return aggressionTypeRepository.searchActives(paginable, "%" + q + "%");
	}

	public Page<AggressionType> toList(Pageable paginable) {
		return aggressionTypeRepository.searchActives(paginable);
	}
	
	public List<AggressionType> toList() {
		return aggressionTypeRepository.searchActives();
	}
	
	public AggressionTypeModel search(String id) {
		AggressionType aggressionType = aggressionTypeRepository.getOne(id);
		return aggressionTypeConverter.entityToModel(aggressionType);
	}
		
}