package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.model.entity.Study;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class StudyDao extends HibernateDao<Study> {
	/**
	 * Constructor from superclass, indicate to Hibernate.
	 *
	 */
	public StudyDao() {
		super(Study.class);
	}

	/**
	 * Returns all studies from the specified project.
	 *
	 * @param dsKey the identifier of the project.
	 * @return a list of all the studies of the specified project.
	 */
	public List<Study> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Study s");
		sbHql.append(" join fetch s.project p");
		sbHql.append(" where p.dsKey = :dsKey");
		
		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<Study> studies = query.list();
		return studies;
	}

	public Study getByCdCiteKey(String cdCiteKey) {
		Query query = getSession().createQuery("from Study s where lower(s.cdCiteKey) = lower(?)");
		query.setString(0, cdCiteKey);
		List<Study> results = query.list();
		return (results != null && !results.isEmpty()) ? (Study) results.get(0) : null;
	}
}
