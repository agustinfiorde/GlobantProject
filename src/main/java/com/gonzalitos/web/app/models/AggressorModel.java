package com.gonzalitos.web.app.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AggressorModel {

	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("secondName")
	private String secondName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("dni")
	private String dni;
	@JsonProperty("registered")
	private Date registered;
	@JsonProperty("edited")
	private Date edited;
	@JsonProperty("remove")
	private Date remove;
	
}