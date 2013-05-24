package pl.iqsoft.plc.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "UNITS")
public class Unit {
	@Id
	@SequenceGenerator(name = "UNITS_ID_GENERATOR", sequenceName = "UNITS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNITS_ID_GENERATOR")
	private Long id;
	
	private String name;

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
}
