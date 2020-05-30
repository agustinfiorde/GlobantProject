package com.gonzalitos.web.app.models;

import java.util.Date;

import lombok.Data;

@Data
public class FileModel {
	
	private String id;
	
	private byte[] photo;
	
	private HelpRequestModel helpRequestModel;
	
    private Date registered;
	
	private Date edited;
	
	private Date remove;

}
