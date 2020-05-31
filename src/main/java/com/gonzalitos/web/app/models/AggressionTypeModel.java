package com.gonzalitos.web.app.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AggressionTypeModel {

	
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("registered")
	private Date registered;
	@JsonProperty("edited")
	private Date edited;
	@JsonProperty("remove")
	private Date remove;
	
}
