package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.SelectionStep;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class SelectionStepDao extends HibernateDao<SelectionStep> {

	public SelectionStepDao() {
		super(SelectionStep.class);
	}

	/**
	 * Get configuration step of project
	 * 
	 * @param dsKey
	 * @return
	 */
	public SelectionStep getConfiguration(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from SelectionStep ss");
		sbHql.append(" join fetch ss.project p");
		sbHql.append(" where p.dsKey = :dsKey");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<SelectionStep> selectionStep = query.list();
		return selectionStep.get(0); // verificar
	}

	/**
	 * Delete a configuration step of project
	 * 
	 * @param idProject
	 */
	public void delete(Long idProject) {
		String hql = "delete from SelectionStep where project.idProject= :idProject";
		Query query = getSession().createQuery(hql);
		query.setLong("idProject", idProject);
		System.out.println(query.executeUpdate());
	}

}
