package br.ufs.dcomp.farms.model.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.model.dto.SelectionCriteriaCreatedDto;
import br.ufs.dcomp.farms.model.entity.SelectionCriteria;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class SelectionCriteriaDao extends HibernateDao<SelectionCriteria> {

    /**
     * Constructor from superclass, indicate to Hibernate.
     *
     */
    public SelectionCriteriaDao() {
        super(SelectionCriteria.class);
    }

    /**
     * Returns all studies from the specified project.
     *
     * @param dsKey the identifier of the project.
     * @return a list of all the studies of the specified project.
     */
    public List<SelectionCriteria> getByDsKeyProject(String dsKey) {
        StringBuilder sbHql = new StringBuilder();
        sbHql.append("from SelectionCriteria sc");
        sbHql.append(" join fetch sc.project p");
        sbHql.append(" where p.dsKey = :dsKey");

        Query query = getSession().createQuery(sbHql.toString());
        query.setParameter("dsKey", dsKey);
        List<SelectionCriteria> selectionCriterias = query.list();
        return selectionCriterias;
    }

    /**
     * Delete criteria of protocol project
     *
     * @param idProject
     * @param dsSelectionCriteria
     * @param tpCriteria
     */
    public void deleteCriteria(Long idProject, String dsSelectionCriteria, Long tpCriteria) {
        Transaction transaction = getSession().beginTransaction();
        try {

            String hql = "delete from SelectionCriteria where project.idProject= :idProject and dsSelectionCriteria =:dsSelectionCriteria"
                    + " and tpCriteria = :tpCriteria";
            Query query = getSession().createQuery(hql);
            query.setLong("idProject", idProject);
            query.setString("dsSelectionCriteria", dsSelectionCriteria);
            query.setLong("tpCriteria", tpCriteria);
            System.out.println(query.executeUpdate());

            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    /**
     * Edit selection criteria of protocol project
     *
     * @param idProject
     * @param selectionCriteriaCreatedDto
     */
    public void editCriteria(Long idProject, SelectionCriteriaCreatedDto selectionCriteriaCreatedDto) {
        Query query = getSession().createQuery("update SelectionCriteria set dsSelectionCriteria = :dsSelectionCriteria"
                + ", " + "tpCriteria = :tpCriteria"
                + " where project.idProject= :idProject and idSelectionCriteria =:idSelectionCriteria");
        query.setString("dsSelectionCriteria", selectionCriteriaCreatedDto.getDsSelectionCriteria());
        query.setLong("idProject", idProject);
        query.setInteger("tpCriteria", selectionCriteriaCreatedDto.getTpCriteria());
        query.setLong("idSelectionCriteria", selectionCriteriaCreatedDto.getIdSelectionCriteria());
        query.executeUpdate();

    }
}
