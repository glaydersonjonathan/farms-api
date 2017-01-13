package br.ufs.dcomp.farms.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Country;
import br.ufs.dcomp.farms.model.entity.Institution;
import br.ufs.dcomp.farms.model.entity.Project;
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
	 * Inserts a institution.
	 * 
	 * @param institution
	 */
	public void save(Institution institution) {
		System.out.println("abbr " + institution.getDsAbbreviation());
		System.out.println("nome " + institution.getNmInstitution());
		System.out.println("id_country " + institution.getCountry().getIdCountry());
		System.out.println("nome countrhy" + institution.getCountry().getNmCountry());

		super.save(institution);
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
		sbHql.append("from ProjectMember pm");
		sbHql.append(" join fetch pm.institution i");
		sbHql.append(" where pm.project.dsKey like :dsKey");

		Query q = getSession().createQuery(sbHql.toString());
		q.setParameter("dsKey", dsKey);

		List<ProjectMember> projects_member = q.list();

		ArrayList<Institution> institutions = new ArrayList<Institution>();

		for (ProjectMember pm : projects_member) {
			institutions.add(pm.getInstitution());
		}
		return institutions;
	}

	/**
	 * Returns all institutions
	 * 
	 * @return a list of all the institutions
	 */
	public List<Institution> getAllInstitutions() {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Institution i");

		Query q = getSession().createQuery(sbHql.toString());

		List<Institution> institutions = q.list();
		return institutions;
	}

	public List<Country> getAllCountries() {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Country c");

		Query q = getSession().createQuery(sbHql.toString());

		List<Country> countries = q.list();
		return countries;
	}
}
