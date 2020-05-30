package com.gonzalitos.web.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonzalitos.web.app.enums.Roles;

import lombok.Data;

@Data
public class UserModel {

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("dni")
	private String dni;
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("role")
	private Roles role;

}
