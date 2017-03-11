package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;


import br.ufs.dcomp.farms.model.entity.Search;


@Component
@SuppressWarnings("unchecked")
public class SearchDao  extends HibernateDao<Search>{

	public SearchDao() {
		super(Search.class);
	}
	
	public Search getByNrSearch(Long nrSearch){
		Query query = getSession().createQuery("from Search p where nrSearch = :nrSearch");
		query.setLong("nrSearch", nrSearch);
		List<Search> results = query.list();
		return (results != null && !results.isEmpty()) ? (Search) results.get(0) : null;		
	}
}
