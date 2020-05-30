package com.gonzalitos.web.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class AggressionType implements Serializable{

	private static final long serialVersionUID = 6522896498689132122L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String name;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date registered;
	@Temporal(TemporalType.TIMESTAMP)
	private Date edited;
	@Temporal(TemporalType.TIMESTAMP)
	private Date remove;
}
