package com.gonzalitos.web.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HelpRequestModel implements Serializable{
	
	private static final long serialVersionUID = 6522896498689132123L;
	
	private String id;
	private String address;
	private String factTimeString;
	private List<AggressionTypeModel> typesOfViolences = new ArrayList<>();
	private VictimModel victim;
	private AggressorModel aggressor;
	private RelationshipModel relationship;
	private String description;
	
}
