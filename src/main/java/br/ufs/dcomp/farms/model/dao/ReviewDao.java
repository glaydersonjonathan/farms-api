package br.ufs.dcomp.farms.model.dao;

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
}
