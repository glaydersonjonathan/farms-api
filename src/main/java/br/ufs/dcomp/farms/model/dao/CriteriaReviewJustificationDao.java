package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.model.entity.CriteriaReviewJustification;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class CriteriaReviewJustificationDao extends HibernateDao<CriteriaReviewJustification> {

	public CriteriaReviewJustificationDao() {
		super(CriteriaReviewJustification.class);
	}

	/**
	 * GET criterias adn review justification by id review
	 * 
	 * @param idReview
	 * @return
	 */
	public List<CriteriaReviewJustification> getByIdReview(Long idReview) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from CriteriaReviewJustification cr");
		sbHql.append(" where cr.criteriaReviewJustificationPk.review.idReview = :idReview");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("idReview", idReview);

		List<CriteriaReviewJustification> criterias = query.list();

		return criterias;
	}

	/**
	 * Deletes criterias and justification of a realized review
	 * 
	 * @param idReview
	 * @param idSelectionCriteria
	 */
	public void deleteCriteriaJustificationReview(Long idReview, Long idSelectionCriteria) {
		String hql = "delete from CriteriaReviewJustification " + "where id_review = :idReview "
				+ " and id_selection_criteria = :idSelectionCriteria";
		Query query = getSession().createQuery(hql);
		query.setLong("idReview", idReview);
		query.setLong("idSelectionCriteria", idSelectionCriteria);
		System.out.println(query.executeUpdate());
	}
}
