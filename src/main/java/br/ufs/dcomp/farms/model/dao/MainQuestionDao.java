package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.MainQuestion;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class MainQuestionDao extends HibernateDao<MainQuestion> {

	public MainQuestionDao() {
		super(MainQuestion.class);
	}

	/**
	 * Returns main question from the specified project.
	 *
	 * @param dsKey
	 *            the identifier of the project.
	 * @return a list with a main question of the specified project.
	 */
	public List<MainQuestion> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from MainQuestion mq");
		sbHql.append(" join fetch mq.project p");
		sbHql.append(" where p.dsKey = :dsKey");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<MainQuestion> mainQuestions = query.list();
		return mainQuestions;
	}

	/**
	 * Delete a mainQuestion
	 * 
	 * @param idProject
	 */
	public void delete(Long idProject) {
		Transaction transaction = getSession().beginTransaction();
		try {

			String hql = "delete from MainQuestion where project.idProject= :idProject";
			Query query = getSession().createQuery(hql);
			query.setLong("idProject", idProject);
			System.out.println(query.executeUpdate());

			transaction.commit();
		} catch (Throwable t) {
			transaction.rollback();
			throw t;
		}

	}
}
