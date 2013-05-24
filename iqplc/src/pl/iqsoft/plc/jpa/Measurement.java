package pl.iqsoft.plc.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MEASUREMENTS")
public class Measurement {
	@Id
	@SequenceGenerator(name = "MEASUREMENTS_ID_GENERATOR", sequenceName = "MEASUREMENTS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEASUREMENTS_ID_GENERATOR")
	private Long id;
	
	private String name;
	
	private Integer aggtype;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAggtype() {
		return aggtype;
	}
	
	public void setAggtype(Integer aggtype) {
		this.aggtype = aggtype;
	}
}
