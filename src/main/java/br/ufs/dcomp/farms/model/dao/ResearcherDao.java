package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Researcher;

/**
 * @author farms
 *
 */
@SuppressWarnings("unchecked")
@Component
public class ResearcherDao extends HibernateDao<Researcher> {

	/**
	 * Constructor from superclass.
	 *
	 */
	public ResearcherDao() {
		super(Researcher.class);
	}

	/**
	 * Inserts a researcher.
	 * 
	 * @param researcher
	 */
	public void save(Researcher researcher) {
		super.save(researcher);
	}

	/**
	 * Update a researcher.
	 * 
	 * @param researcher
	 */
	public void update(Researcher researcher) {
		super.update(researcher);
	}

	/**
	 * Deletes a researcher at id.
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(Long idResearcher) {
		Researcher researcher = get(idResearcher);
		super.delete(researcher);
		return true;
	}

	/**
	 * Gets a researcher at id.
	 * 
	 * @param id
	 * @return
	 */
	public Researcher get(Long idResearcher) {
		return super.get(idResearcher);
	}

	/**
	 * Returns all researchers.
	 *
	 * @return a list of all the researchers.
	 */
	public List<Researcher> getAll() {
		Query query = getSession().createQuery("from Researcher");
		return query.list();
	}

	/**
	 * Search a researcher by the name.
	 * 
	 * @param nmResearcher
	 * @return
	 */
	public List<Researcher> getByNmResearcher(String nmResearcher) {
		Query query = getSession().createQuery("from Researcher where lower(nmResearcher) like lower(?)");
		query.setString(0, "%" + nmResearcher + "%");
		return query.list();
	}

	/**
	 * Search a researcher by sso (username).
	 * 
	 * @param dsSSO
	 * @return
	 */
	public Researcher getByDsSSO(String dsSSO) {
		Query query = getSession().createQuery("from Researcher r where lower(r.dsSSO) = lower(?)");
		query.setString(0, dsSSO);
		List<Researcher> results = query.list();
		return (results != null && !results.isEmpty()) ? (Researcher) results.get(0) : null;
	}

	/**
	 * Search a researcher by e-mail.
	 * 
	 * @param dsEmail
	 * @return Researcher object.
	 */
	public Researcher getByDsEmail(String dsEmail) {
		Query query = getSession().createQuery("from Researcher r where lower(r.dsEmail) = lower(?)");
		query.setString(0, dsEmail);
		List<Researcher> results = query.list();
		return (results != null && !results.isEmpty()) ? (Researcher) results.get(0) : null;
	}

	/**
	 * Returns all researchers from the specified project.
	 *
	 * @param dsKey
	 *            the identifier of the project.
	 * @return a list of all the researchers of the specified project.
	 */
	public List<Researcher> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Researcher r");
		sbHql.append(" join fetch r.projectMember pm");
		sbHql.append(" where pm.project.dsKey = :dsKey");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<Researcher> researchers = query.list();
		return researchers;
	}

	/**
	 * Search a researcher by cdUuid.
	 * 
	 * @param cdUuid
	 * @return Researcher object.
	 */
	public Researcher getByUuid(String cdUuid) {
		Query query = getSession().createQuery("from Researcher r where lower(r.cdUuid) = lower(?)");
		query.setString(0, cdUuid);
		List<Researcher> results = query.list();
		return (results != null && !results.isEmpty()) ? (Researcher) results.get(0) : null;
	}

	/**
	 * Set state of researcher to inactive.
	 * 
	 * @param idResearcher
	 * @return
	 */
	public boolean inactive(Long idResearcher) {
		Query query = getSession()
				.createQuery("update Researcher set tp_state = :tpState" + " where idResearcher = :idResearcher");
		query.setParameter("idResearcher", idResearcher);
		query.setParameter("tpState", "I");
		System.out.println(query.executeUpdate());
		return true;
	}

	/**
	 * Set state of researcher to active.
	 * 
	 * @param idResearcher
	 * @return
	 */
	public boolean active(Long idResearcher) {
		Query query = getSession()
				.createQuery("update Researcher set tp_state = :tpState" + " where idResearcher = :idResearcher");
		query.setParameter("idResearcher", idResearcher);
		query.setParameter("tpState", "A");
		System.out.println(query.executeUpdate());
		return true;
	}
}