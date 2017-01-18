package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.StandardQuery;

@Component
@SuppressWarnings("unchecked")
public class StandardQueryDao extends HibernateDao<StandardQuery> {

	public StandardQueryDao() {
		super(StandardQuery.class);
	}

	/**
	 * Returns the standard query from the specified project.
	 *
	 * @param dsKey the identifier of the project.
	 * @return a list with a standard query of the specified project.
	 */
	public List<StandardQuery> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from StandardQuery sq");
		sbHql.append(" join fetch sq.project p");
		sbHql.append(" where p.dsKey = :dsKey");
		
		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<StandardQuery> standardQueries = query.list();
		return standardQueries;
	}

	public void delete(Long idProject) {
		Transaction transaction = getSession().beginTransaction();
		try {
			// your code
			String hql = "delete from StandardQuery where project.idProject= :idProject";
			Query query = getSession().createQuery(hql);
			query.setLong("idProject", idProject);
			System.out.println(query.executeUpdate());
			// your code end

			transaction.commit();
		} catch (Throwable t) {
			transaction.rollback();
			throw t;
		}				
	}
}
