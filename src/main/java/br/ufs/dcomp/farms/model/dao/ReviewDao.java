package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Review;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class ReviewDao extends HibernateDao<Review> {

	public ReviewDao() {
		super(Review.class);
	}

	/**
	 * Get reviews by project and researcher
	 * 
	 * @param dsKey
	 * @param dsSSO
	 * @return
	 */
	public List<Review> getStudiesToReview(String dsKey, String dsSSO) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Review r");
		sbHql.append(" join fetch r.study s");
		sbHql.append(" join fetch s.project p");
		sbHql.append(" join fetch r.researcher re");
		sbHql.append(" where p.dsKey = :dsKey and re.dsSSO = :dsSSO");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		query.setParameter("dsSSO", dsSSO);

		List<Review> reviews = query.list();

		return reviews;
	}

}
