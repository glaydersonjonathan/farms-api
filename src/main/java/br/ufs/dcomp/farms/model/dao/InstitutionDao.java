package br.ufs.dcomp.farms.model.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.model.entity.Country;
import br.ufs.dcomp.farms.model.entity.Institution;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class InstitutionDao extends HibernateDao<Institution> {

    /**
     * Constructor from superclass.
     *
     */
    public InstitutionDao() {
        super(Institution.class);
    }

    /**
     * Update a institution.
     *
     * @param institution
     */
    @Override
    public void update(Institution institution) {
        Query query = getSession().createQuery("update Institution set nmInstitution = :nmInstitution" + " "
                + ", dsAbbreviation = :dsAbbreviation, country.idCountry = :idCountry"
                + " where idInstitution = :idInstitution");
        query.setParameter("nmInstitution", institution.getNmInstitution());
        query.setParameter("dsAbbreviation", institution.getDsAbbreviation());
        query.setParameter("idCountry", institution.getCountry().getIdCountry());
        query.setParameter("idInstitution", institution.getIdInstitution());
        query.executeUpdate();
    }

    /**
     * Get a institution by id.
     *
     * @param idInstitution
     * @return Institution object.
     */
    public Institution getById(Long idInstitution) {
        return super.get(idInstitution);
    }

    /**
     * Inserts a institution.
     *
     * @param institution
     */
    @Override
    public void save(Institution institution) {
        super.save(institution);
    }

    /**
     * Returns all institutions from the specified project.
     *
     * @param dsKey the identifier of the project.
     * @return a list of all the institutions of the specified project.
     */
    public List<Institution> getByDsKeyProject(String dsKey) {
        StringBuilder sbHql = new StringBuilder();
        sbHql.append("from Institution i");
        sbHql.append(" join fetch i.project p");
        sbHql.append(" where p.dsKey like :dsKey");

        Query q = getSession().createQuery(sbHql.toString());
        q.setParameter("dsKey", dsKey);

        List<Institution> institutions = q.list();

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

    /**
     * Get all registered countries.
     *
     * @return List of all countries registered.
     */
    public List<Country> getAllCountries() {
        StringBuilder sbHql = new StringBuilder();
        sbHql.append("from Country c");

        Query q = getSession().createQuery(sbHql.toString());

        List<Country> countries = q.list();
        return countries;
    }

    public Institution getByName(String nmInstitution, Long idProject) {
        Query query = getSession().createQuery(
                "from Institution i where i.project.idProject = (?) and lower(i.nmInstitution) = lower(?)");
        query.setLong(0, idProject);
        query.setString(1, nmInstitution);
        List<Institution> results = query.list();
        return (results != null && !results.isEmpty()) ? (Institution) results.get(0) : null;
    }

    /**
     * Delete institution from project
     *
     * @param idProject
     * @param idInstitution
     */
    public void deleteInstitution(Long idProject, Long idInstitution) {
        Transaction transaction = getSession().beginTransaction();
        try {

            String hql = "delete from Institution where project.idProject= :idProject and idInstitution =:idInstitution";
            Query query = getSession().createQuery(hql);
            query.setLong("idProject", idProject);
            query.setLong("idInstitution", idInstitution);
            System.out.println(query.executeUpdate());

            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    /**
     * Count institutions of a project
     *
     * @param idProject
     * @return
     */
    public Long countInstitutions(Long idProject) {
        Query query = getSession().createQuery("select count(*) from Institution where project.idProject= :idProject");
        query.setLong("idProject", idProject);
        return (Long) query.uniqueResult();
    }
}
