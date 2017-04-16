package br.ufs.dcomp.farms.model.dao;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.AdaptedQuery;

/**
 * @author farms
 *
 */
@Component
public class AdaptedQueryDao extends HibernateDao<AdaptedQuery> {

    public AdaptedQueryDao() {
        super(AdaptedQuery.class);
    }

    /**
     * Delete adapetedQuery
     *
     * @param adaptedQuery
     */
    public void deleteAdapted(AdaptedQuery adaptedQuery) {
        Transaction transaction = getSession().beginTransaction();
        try {
            String hql = "delete from AdaptedQuery where idAdaptedQuery = :idAdaptedQuery";
            Query query = getSession().createQuery(hql);
            query.setLong("idAdaptedQuery", adaptedQuery.getIdAdaptedQuery());
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

}
