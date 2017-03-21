package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Review;
import br.ufs.dcomp.farms.model.entity.Study;

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

	/**
	 * Update Date review and status
	 * 
	 * @param Review
	 * @return
	 */
	public void update(Review review) {
		Query query = getSession().createQuery(
				"update Review set dh_review = :dhReview, tp_status = :tpStatus" + " where idReview = :idReview");
		query.setParameter("idReview", review.getIdReview());
		query.setParameter("dhReview", review.getDhReview());
		query.setParameter("tpStatus", review.getTpStatus().getCode());
		System.out.println(query.executeUpdate());
	}

	public List<Long> reviewsConflicts (String dsKey){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select id_study from (select count (id_study) as ce, id_study from ( select foo.id_study, foo.tp_status from (select id_study, r.tp_status from review r left join study using (id_study) left join project using (id_project) where r.tp_status !=0  and ds_key = 'tcc' group by id_study, r.tp_status) as foo) as foo2 group by id_study) as foo3 where ce > 1");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
	
		List<Long> ids = query.list();

		return ids;
	}

}
