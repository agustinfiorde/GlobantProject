package com.gonzalitos.web.app.models;

import java.util.Date;

import lombok.Data;

@Data
public class AggressorModel {

	private String id;
	
	private String name;
	
	private String secondName;
	
	private String lastName;
	
	private String dni;
	
	private Date registered;
	
	private Date edited;
	
	private Date remove;
	
}