package com.gonzalitos.web.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gonzalitos.web.app.converters.VictimConverter;
import com.gonzalitos.web.app.entities.Victim;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.VictimModel;
import com.gonzalitos.web.app.repositories.VictimRepository;


@Service
public class VictimService {

	@Autowired
	private VictimConverter victimConverter;

	@Autowired
	private VictimRepository victimRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Victim save(VictimModel model) throws WebException {
		Victim victim = victimConverter.modelToEntity(model);
		if (victim.getRemoveString() != null) {
			throw new WebException("La víctima que intenta modificar se encuentra dada de baja.");
		}
		if (victim.getName() == null || victim.getName().isEmpty()) {
			throw new WebException("El nombre de la víctima no puede estar vacio.");
		}
		return victimRepository.save(victim);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Victim delete(String id) throws WebException {
		Victim victim = victimRepository.getOne(id);
		if (victim.getRemoveString() == null) {
			victim.setRemoveString(new Date().toString());
			victim = victimRepository.save(victim);
		} else {
			throw new WebException("La víctima que intenta eliminar ya se encuentra dada de baja.");
		}
		return victim;
	}

	public Page<Victim> toList(Pageable paginable, String q) {
		return victimRepository.searchActives(paginable, "%" + q + "%");
	}

	public Page<Victim> toList(Pageable paginable) {
		return victimRepository.searchActives(paginable);
	}
	
	public List<Victim> toList() {
		return victimRepository.searchActives();
	}
	
	public VictimModel search(String id) {
		Victim victim = victimRepository.getOne(id);
		return victimConverter.entityToModel(victim);
	}
	
	public Victim searchEntity(String id) {
		return victimRepository.getOne(id);
	}
	
	public VictimModel searchByDni(String dni) {
		Victim victim = victimRepository.searchByDni(dni);
		return victimConverter.entityToModel(victim);
	}
		
}