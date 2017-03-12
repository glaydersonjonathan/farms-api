package br.ufs.dcomp.farms.model.dao;


import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.AdaptedQuery;
@Component
public class AdaptedQueryDao extends HibernateDao<AdaptedQuery>{

	public AdaptedQueryDao() {
		super(AdaptedQuery.class);
	}
	
}
