package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.ProjectMember;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class ProjectMemberDao extends HibernateDao<ProjectMember> {

	/**
	 * Indicates the entity type to Hibernate.
	 */
	public ProjectMemberDao() {
		super(ProjectMember.class);
	}

	/**
	 * Inserts a project member.
	 * 
	 * @param projectMember
	 */
	public void save(ProjectMember projectMember) {
		super.save(projectMember);
	}

	/**
	 * Returns all project members from the specified project.
	 *
	 * @param dsKey
	 *            the identifier of the project.
	 * @return a list of all the project members of the specified project.
	 */
	public List<ProjectMember> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from ProjectMember pm");
		sbHql.append(" join fetch pm.project p");
		sbHql.append(" where p.dsKey = :dsKey");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<ProjectMember> projectMembers = query.list();
		return projectMembers;
	}

	/**
	 * Get role of researcher in project
	 * 
	 * @param idProject
	 * @param idResearcher
	 * @return
	 */
	public int getResearcherRole(Long idProject, Long idResearcher) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from ProjectMember pm");
		sbHql.append(" where pm.project.idProject = :idProject");
		sbHql.append(" and pm.researcher.idResearcher = :idResearcher");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("idProject", idProject);
		query.setParameter("idResearcher", idResearcher);
		List<ProjectMember> projectMembers = query.list();

		return projectMembers.get(0).getTpRole().getCode();

	}

	/**
	 * Verify Already a member of project
	 * 
	 * @param idProject
	 * @param idResearcher
	 * @return
	 */
	public boolean verifyAlreadyMember(Long idProject, Long idResearcher) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from ProjectMember pm");
		sbHql.append(" where pm.project.idProject = :idProject");
		sbHql.append(" and pm.researcher.idResearcher = :idResearcher");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("idProject", idProject);
		query.setParameter("idResearcher", idResearcher);
		List<ProjectMember> projectMembers = query.list();

		if (projectMembers.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Turns member inactive
	 * @param idProjectMember
	 * @return
	 */
	public Boolean inactive(Long idProjectMember) {
		Query query = getSession()
				.createQuery("update ProjectMember set tp_state = :tpState" + " where idProjectMember = :idProjectMember");
		query.setParameter("idProjectMember", idProjectMember);
		query.setParameter("tpState", "I");
		System.out.println(query.executeUpdate());
		return true;
	}

	/**
	 * Turns member active
	 * @param idProjectMember
	 * @return
	 */
	public Boolean active(Long idProjectMember) {
		Query query = getSession()
				.createQuery("update ProjectMember set tp_state = :tpState" + " where idProjectMember = :idProjectMember");
		query.setParameter("idProjectMember", idProjectMember);
		query.setParameter("tpState", "A");
		System.out.println(query.executeUpdate());
		return true;
	}
}