package com.gonzalitos.web.app.models;

import lombok.Data;

@Data
public class VictimModel {

	private String id;
	private String name;
	private String lastName;
	private String dni;
	
	private String dateBornString;
	
	private String phone;
	private String email;
	private Integer children;
	private Boolean whistleblower;
	private String ipAddress;
	
//	private List<AggressorModel> aggressorsModel = new ArrayList<>();

	private String removeString;
	
	private String membershipString;
	
	private String modificationString;
}
