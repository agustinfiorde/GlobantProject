package com.gonzalitos.web.app.models;

import lombok.Data;

@Data
public class RelationshipModel {

	private String id;
	private String name;
	private String description;

	private String removeString;
	private String membershipString;
	private String modificationString;
}
