package com.gonzalitos.web.app.models;

import java.util.Date;

import lombok.Data;

@Data
public class AggressionTypeModel {

	
	private String id;
	
	private String name;
	
	private String description;
	
	private Date registered;
	
	private Date edited;
	
	private Date remove;
	
}
