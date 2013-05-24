package pl.iqsoft.plc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.iqsoft.plc.jpa.DataSource;

public class DataSourceDao {

	@PersistenceContext(unitName="iqplcpu")
	private EntityManager em;
	
	public List<DataSource> getDataSources() {
		TypedQuery<DataSource> query = em.createQuery("SELECT a.* FROM DataSource", DataSource.class);
		
		return query.getResultList();
	}
}
