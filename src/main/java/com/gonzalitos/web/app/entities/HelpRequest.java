package com.gonzalitos.web.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
public class HelpRequest {

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
	
	@OneToOne
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
