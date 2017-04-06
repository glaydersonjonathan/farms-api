package br.ufs.dcomp.farms.model.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Search;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class SearchDao extends HibernateDao<Search> {

    public SearchDao() {
        super(Search.class);
    }

    /**
     * Searh by nrSearch
     *
     * @param nrSearch
     * @return
     */
    public Search getByNrSearch(Long nrSearch) {
        Query query = getSession().createQuery("from Search p where nrSearch = :nrSearch");
        query.setLong("nrSearch", nrSearch);
        List<Search> results = query.list();
        return (results != null && !results.isEmpty()) ? (Search) results.get(0) : null;
    }

    /**
     * Delete a search
     *
     * @param search
     */
    public void deleteSearch(Search search) {
        Transaction transaction = getSession().beginTransaction();
        try {

            String hql = "delete from Search where idSearch = :idSearch";
            Query query = getSession().createQuery(hql);
            query.setLong("idSearch", search.getIdSearch());
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }
}
