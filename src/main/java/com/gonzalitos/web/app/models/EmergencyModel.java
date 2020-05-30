package com.gonzalitos.web.app.models;

import com.madresdepie.web.app.entities.Victim;

import lombok.Data;

@Data
public class EmergencyModel {
	
    private String id;
    private Victim victim;
    private String address;
	private String ipAddress;
	private String phone;
	private String city;

}
