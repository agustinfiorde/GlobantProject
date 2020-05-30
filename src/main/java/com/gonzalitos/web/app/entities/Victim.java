package com.gonzalitos.web.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Victim implements Serializable{
	
	private static final long serialVersionUID = 6522896498689132123L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String name;
	private String lastName;
	private String dni;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateBorn; //change Date to String in victimModel
	private String phone;
	private String email;
	private Integer children;
	private Boolean whistleblower;
	private String ipAddress;
	
	@ManyToMany
	private List<Aggressor> aggressors; //change List<Aggressor> to List<AggressorModel> in victimModel
	
	@Temporal(TemporalType.TIMESTAMP) //change Date to String in victimModel
	private Date remove; 
	
	@Temporal(TemporalType.TIMESTAMP) //change Date to String in victimModel
	private Date membership; 
	
	@Temporal(TemporalType.TIMESTAMP) //change Date to String in victimModel
	private Date modification;
	

}
