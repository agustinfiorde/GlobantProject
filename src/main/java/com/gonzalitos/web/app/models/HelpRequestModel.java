package com.gonzalitos.web.app.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class HelpRequestModel {
	private String id;
	private String address;
	private String factTimeString;
	
	private List<AggressionTypeModel> typesOfViolences = new ArrayList<>();
	
	private VictimModel victim;
	
	private AggressorModel aggressor;
	
	private RelationshipModel relationship;
	
	private String description;
	
}
