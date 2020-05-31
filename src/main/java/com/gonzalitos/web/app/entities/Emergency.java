package com.gonzalitos.web.app.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Emergency {
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@ManyToOne
	private Victim victim;
	
	private String address;
	
	private String city;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date registered;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date remove;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modification;
	
	

}
