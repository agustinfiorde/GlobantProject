package com.gonzalitos.web.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gonzalitos.web.app.converters.RelationshipConverter;
import com.gonzalitos.web.app.entities.Relationship;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.RelationshipModel;
import com.gonzalitos.web.app.repositories.RelationshipRepository;


@Service
public class RelationshipService {

	@Autowired
	private RelationshipConverter relationshipConverter; 

	@Autowired
	private RelationshipRepository relationshipRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Relationship save(RelationshipModel model) throws WebException {
		Relationship relationship = relationshipConverter.modelToEntity(model);
		if (relationship.getRemove() != null) {
			throw new WebException("La relación que intenta modificar se encuentra dada de baja.");
		}
		if (relationship.getName() == null || relationship.getName().isEmpty()) {
			throw new WebException("La relación no puede estar vacia.");
		}
		return relationshipRepository.save(relationship);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Relationship delete(String id) throws WebException {
		Relationship relationship = relationshipRepository.getOne(id);
		if (relationship.getRemove() == null) {
			relationship.setRemove(new Date());
			relationship = relationshipRepository.save(relationship);
		} else {
			throw new WebException("La relación que intenta eliminar ya se encuentra dada de baja.");
		}
		return relationship;
	}
	
	public Page<Relationship> toList(Pageable paginable, String q) {
		return relationshipRepository.searchActives(paginable, "%" + q + "%");
	}

	public Page<Relationship> toList(Pageable paginable) {
		return relationshipRepository.searchActives(paginable);
	}
	
	public List<Relationship> toList() {
		return relationshipRepository.searchActives();
	}
	
	public RelationshipModel search(String id) {
		Relationship relationship = relationshipRepository.getOne(id);
		return relationshipConverter.entityToModel(relationship);
	}
		
	
	public List<RelationshipModel> toListModel(){
		return relationshipConverter.entitiesToModels(relationshipRepository.searchActives());
	}
	
		
}
