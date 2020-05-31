package com.gonzalitos.web.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class HelpRequest implements Serializable{

	private static final long serialVersionUID = 6522896498689132123L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String address;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date factTime;

	@ManyToMany
	private List<AggressionType> typesOfViolences;
	
	@ManyToOne
	private Victim victim;
	
	@ManyToOne
	private Aggressor aggressor;
	
	@ManyToOne
	private Relationship relationship;
	
	@Lob
	@Column(name = "description", length = 4000)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date remove;
}
