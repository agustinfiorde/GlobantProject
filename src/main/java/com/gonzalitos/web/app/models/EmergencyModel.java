package com.gonzalitos.web.app.models;

import lombok.Data;

@Data
public class EmergencyModel {
	
    private String id;
//    private VictimModel victim;
    private String address;
	private String ipAddress;
	private String phone;
	private String city;

}
