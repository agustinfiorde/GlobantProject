package com.gonzalitos.web.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RelationshipModel {
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
//	@JsonProperty("removeString")
	private String removeString;
//	@JsonProperty("membershipString")
	private String membershipString;
//	@JsonProperty("modificationString")
	private String modificationString;
	
	public RelationshipModel() {}
}
