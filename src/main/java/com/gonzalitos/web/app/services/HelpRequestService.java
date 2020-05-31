package com.gonzalitos.web.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gonzalitos.web.app.converters.HelpRequestConverter;
import com.gonzalitos.web.app.entities.HelpRequest;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.repositories.HelpRequestRepository;

@Service
public class HelpRequestService {

	@Autowired
	private HelpRequestConverter helpRequestConverter;

	@Autowired
	private HelpRequestRepository helpRequestRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public HelpRequest save(HelpRequestModel model) throws WebException {
		HelpRequest helpRequest = helpRequestConverter.modelToEntity(model);
		if (helpRequest.getRemove() != null) {
			throw new WebException("El agresor que intenta modificar se encuentra dada de baja.");
		}
		return helpRequestRepository.save(helpRequest);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public HelpRequest delete(String id) throws WebException {
		HelpRequest financiamiento = helpRequestRepository.getOne(id);
		if (financiamiento.getRemove() == null) {
			financiamiento.setRemove(new Date());
			financiamiento = helpRequestRepository.save(financiamiento);
		} else {
			throw new WebException("El agresor que intenta eliminar ya se encuentra dado de baja.");
		}
		return financiamiento;
	}
	
	public HelpRequestModel search(String id) {
		HelpRequest helpRequest = helpRequestRepository.getOne(id);
		return helpRequestConverter.entityToModel(helpRequest);
	}
	
	public Page<HelpRequest> toList(Pageable paginable, String q) {
		return helpRequestRepository.searchActives(paginable, "%" + q + "%");
	}

	public Page<HelpRequest> toList(Pageable paginable) {
		return helpRequestRepository.searchActives(paginable);
	}
	
	public List<HelpRequest> toList() {
		return helpRequestRepository.searchActives();
	}
		
}