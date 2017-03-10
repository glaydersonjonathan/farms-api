package br.ufs.dcomp.farms.model.dao;

import org.hibernate.Query;

import br.ufs.dcomp.farms.model.entity.AdaptedQuery;

public class AdaptedQueryDao extends HibernateDao<AdaptedQuery>{

	public AdaptedQueryDao() {
		super(AdaptedQuery.class);
	}
	
	public void insert (AdaptedQuery adaptedQuery){
		Query query = getSession()
				.createSQLQuery("INSERT INTO adapted_query (ds_adapted_query, id_search_engine, id_standard_query, ds_observation) VALUES (:valor1, :valor2, :valor3, :valor4)");
		query.setParameter("valor1", adaptedQuery.getDsAdaptedQuery());
		query.setParameter("valor2", adaptedQuery.getSearchEngine().getIdSearchEngine());
		query.setParameter("valor3", adaptedQuery.getStandardQuery().getIdStandardQuery());
		query.setParameter("valor4", adaptedQuery.getDsObservation());
		query.executeUpdate();
	}

}
