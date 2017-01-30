package br.ufs.dcomp.farms.model.dao;

import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Search;


@Component
@SuppressWarnings("unchecked")
public class SearchDao  extends HibernateDao<Search>{

	public SearchDao() {
		super(Search.class);
	}
}
