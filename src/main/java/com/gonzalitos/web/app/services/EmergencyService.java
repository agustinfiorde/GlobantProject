package com.gonzalitos.web.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gonzalitos.web.app.converters.EmergencyConverter;
import com.gonzalitos.web.app.entities.Emergency;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.EmergencyModel;
import com.gonzalitos.web.app.repositories.EmergencyRepository;

@Service
public class EmergencyService {

	@Autowired
	private EmergencyConverter emergencyConverter;

	@Autowired
	private EmergencyRepository emergencyRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Emergency save(EmergencyModel model, String ipAddress) throws WebException {
		
		Emergency emergency = emergencyConverter.modeloToEntidad(model);
		
		emergency.getVictim().setIpAddress(ipAddress);

		if (emergency.getRemove() != null) {
			throw new WebException("La EMERGENCIA que intenta modificar se encuentra dada de baja.");
		}
		if (emergency.getRegistered() == null) {
			emergency.setRegistered(new Date());
		}
		//enviar mail
		return emergencyRepository.save(emergency);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Emergency delete(String id) throws WebException {
		Emergency emergency = emergencyRepository.getOne(id);
		if (emergency.getRemove() == null) {
			emergency.setRemove(new Date());
			emergency = emergencyRepository.save(emergency);
		} else {
			throw new WebException("La EMERGENCIA que intenta eliminar se encuentra dada de baja.");
		}
		
		return emergency;
	}

	public Page<Emergency> toList(Pageable paginable, String q) {
		return emergencyRepository.searchActives(paginable, "%" + q + "%");
	}

	public Page<Emergency> toList(Pageable paginable) {
		return emergencyRepository.searchActives(paginable);
	}
	
	public List<Emergency> toList() {
		return emergencyRepository.searchActives();
	}
	
	public EmergencyModel search(String id) {
		Emergency emergency = emergencyRepository.getOne(id);
		return emergencyConverter.entidadToModelo(emergency);
	}
		
}
