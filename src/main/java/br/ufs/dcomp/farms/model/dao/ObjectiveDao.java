package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Objective;

/**
 * @author farms
 */
@Component
@SuppressWarnings("unchecked")
public class ObjectiveDao extends HibernateDao<Objective> {

	/**
	 * Constructor from superclass, indicate to Hibernate.
	 *
	 */
	public ObjectiveDao() {
		super(Objective.class);
	}

	/**
	 * Returns all objectives from the specified project.
	 *
	 * @param dsKey
	 *            the identifier of the project.
	 * @return a list of all the objectives of the specified project.
	 */
	public List<Objective> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Objective o");
		sbHql.append(" join fetch o.project p");
		sbHql.append(" where p.dsKey = :dsKey");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<Objective> objectives = query.list();
		return objectives;
	}

	/**
	 * Delete a objective.
	 * 
	 * @param idProject
	 */
	public void delete(Long idProject) {
		Transaction transaction = getSession().beginTransaction();
		try {
			// your code
			String hql = "delete from Objective where project.idProject= :idProject";
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
