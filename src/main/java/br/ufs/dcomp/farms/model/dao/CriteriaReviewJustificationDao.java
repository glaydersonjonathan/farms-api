package br.ufs.dcomp.farms.model.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.CriteriaReviewJustification;

/**
 * @author farms
 *
 */
@Component
public class CriteriaReviewJustificationDao extends HibernateDao<CriteriaReviewJustification> {

	public CriteriaReviewJustificationDao() {
		super(CriteriaReviewJustification.class);
	}

	
	/**
	 * Deletes criterias and justification of a realized review
	 * @param idReview
	 * @param idSelectionCriteria
	 */
	public void deleteCriteriaJustificationReview (Long idReview, Long idSelectionCriteria){	
			String hql = "delete from CriteriaReviewJustification "
					+ "where id_review = :idReview "
					+ " and id_selection_criteria = :idSelectionCriteria";
			Query query = getSession().createQuery(hql);
			query.setLong("idReview", idReview);
			query.setLong("idSelectionCriteria", idSelectionCriteria);
			System.out.println(query.executeUpdate());
	}
}
