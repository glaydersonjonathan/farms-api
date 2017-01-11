package br.ufs.dcomp.farms.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Institution;
import br.ufs.dcomp.farms.model.entity.ProjectMember;

@Component
@SuppressWarnings("unchecked")
public class InstitutionDao extends HibernateDao<Institution> {


	public InstitutionDao() {
		super(Institution.class);
	}

	public Institution getById(Long idInstitution) {
		return super.get(idInstitution);
	}

	/**
	 * Returns all institutions from the specified project.
	 *
	 * @param dsKey
	 *            the identifier of the project.
	 * @return a list of all the institutions of the specified project.
	 */
	public List<Institution> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		// sbHql.append("from Institution i");
		// sbHql.append(" join fetch i.project p");
		// sbHql.append(" join fetch i.project_member pm");
		// sbHql.append(" where p.dsKey = :dsKey");

		sbHql.append("from ProjectMember pm");
		sbHql.append(" join fetch pm.institution i");
		sbHql.append(" where pm.project.dsKey = :dsKey");

		/*
		 * StringBuilder sbHql = new StringBuilder();
		 * sbHql.append("from Project p");
		 * sbHql.append(" join fetch p.projectMembers pm");
		 * sbHql.append(" where pm.researcher.dsSSO = :dsSSO");
		 * 
		 * Query query = getSession().createQuery(sbHql.toString());
		 * query.setParameter("dsSSO", dsSSO);
		 */

		Query q = getSession().createQuery(sbHql.toString());
		q.setParameter("dsKey", dsKey);

		List<ProjectMember> projects_member = q.list();
		
		ArrayList<Institution> institutions = new ArrayList<Institution>(); ;

		for (ProjectMember pm : projects_member) {
			System.out.println(pm.getInstitution().getNmInstitution());
			institutions.add(pm.getInstitution());
		}

		return institutions;
	}
}
