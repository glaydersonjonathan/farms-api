package br.ufs.dcomp.farms.model.dao;

import java.math.BigInteger;
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

	/**
	 * getReviewByStudyAndResearcher
	 * 
	 * @param idStudy
	 * @param idResearcher
	 * @return
	 */
	public List<Review> getReviewByStudyAndResearcher(Long idStudy, Long idResearcher) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Review r");
		sbHql.append(" where r.study.idStudy = :idStudy and r.researcher.idResearcher = :idResearcher");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("idStudy", idStudy);
		query.setParameter("idResearcher", idResearcher);

		List<Review> reviews = query.list();

		return reviews;
	}

	/**
	 * Get id's of study in conflicts.
	 * 
	 * @param dsKey
	 * @return
	 */
	public List<BigInteger> reviewsConflicts(String dsKey) {
		Query query = getSession().createSQLQuery("select id_study from studies_in_conflicts where ds_key =:dsKey");
		query.setParameter("dsKey", dsKey);

		List<BigInteger> ids = query.list();

		return ids;
	}

	/**
	 * Score of accepts per study
	 * 
	 * @param idStudy
	 * @return
	 */
	public BigInteger scoreAccepted(Long idStudy) {
		Query query = getSession().createSQLQuery("select accepted from score_conflicts where id_study =:idStudy");
		query.setParameter("idStudy", idStudy);
		List<BigInteger> qtd = query.list();
		return qtd.size() > 0 ? qtd.get(0) : null;
	}

	/**
	 * Score of rejects per study
	 * 
	 * @param idStudy
	 * @return
	 */
	public BigInteger scoreRejected(Long idStudy) {
		Query query = getSession().createSQLQuery("select rejected from score_conflicts where id_study =:idStudy");
		query.setParameter("idStudy", idStudy);
		List<BigInteger> qtd = query.list();
		return qtd.size() > 0 ? qtd.get(0) : null;
	}

	/**
	 * Score of unclassified per study
	 * 
	 * @param idStudy
	 * @return
	 */
	public BigInteger scoreUnclassified(Long idStudy) {
		Query query = getSession().createSQLQuery("select unclassified from score_conflicts where id_study =:idStudy");
		query.setParameter("idStudy", idStudy);
		List<BigInteger> qtd = query.list();
		return qtd.size() > 0 ? qtd.get(0) : null;
	}

}
