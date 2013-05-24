package pl.iqsoft.plc.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DATASOURCES")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSource {
	@Id
	@SequenceGenerator(name = "DATASOURCES_ID_GENERATOR", sequenceName = "DATASOURCES_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATASOURCES_ID_GENERATOR")
	private Long id;
	
	private String name;
	
	private Integer type;
	
	@Transient
	private Long measurementId;
	
	@Transient
	private Long unitId;
	
	@JsonIgnore	
	@OneToOne
	@JoinColumn(name="MEASUREMENTID")
	private Measurement measurement;
	
	@JsonIgnore	
	@OneToOne
	@JoinColumn(name="UNITID")
	private Unit unit;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getMeasurementId() {
		Measurement m = getMeasurement();
		measurementId = (m != null ? m.getId() : 0);
		
		return measurementId;
	}

	public Long getUnitId() {
		Unit u = getUnit();
		unitId = (u != null ? u.getId() : 0);
		
		return unitId;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
