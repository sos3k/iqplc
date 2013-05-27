package pl.iqsoft.plc.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "RAWDATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class RawData {
	@Id
	@SequenceGenerator(name = "RAWDATA_ID_GENERATOR", sequenceName = "RAWDATA_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RAWDATA_ID_GENERATOR")
	private Long id;
	
	@Transient
	private Long datasourceId;
	
	@JsonIgnore	
	@OneToOne
	@JoinColumn(name="DATASOURCEID")
	private DataSource datasource;
	
	private Float value;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date tstamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDatasourceId() {
		DataSource ds = getDatasource();
		datasourceId = (ds != null ? ds.getId() : 0);
		
		return datasourceId;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Date getTstamp() {
		return tstamp;
	}

	public void setTstamp(Date tstamp) {
		this.tstamp = tstamp;
	}
}
