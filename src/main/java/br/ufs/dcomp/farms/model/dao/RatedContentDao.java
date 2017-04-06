package br.ufs.dcomp.farms.model.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.model.entity.RatedContent;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class RatedContentDao extends HibernateDao<RatedContent> {

    public RatedContentDao() {
        super(RatedContent.class);
    }

    /**
     * Get All RatedContent.
     *
     * @return List
     */
    public List<RatedContent> getAllRated() {
        StringBuilder sbHql = new StringBuilder();
        sbHql.append("from RatedContent rc");

        Query q = getSession().createQuery(sbHql.toString());

        List<RatedContent> rateds = q.list();
        return rateds;
    }

}
