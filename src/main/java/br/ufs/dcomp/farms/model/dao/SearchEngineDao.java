package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.BaseUseCriteria;
import br.ufs.dcomp.farms.model.entity.SearchEngine;


/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class SearchEngineDao extends HibernateDao<SearchEngine> {

	
	/**
	 * Constructor from superclass, indicate to Hibernate.
	 *
	 */
	public SearchEngineDao() {
		super(SearchEngine.class);
	}

	/**
	 * Returns all search engines from the specified project.
	 *
	 * @param dsKey the identifier of the project.
	 * @return a list of all the search engines of the specified project.
	 */
	public List<BaseUseCriteria> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from BaseUseCriteria bc");
		sbHql.append(" join fetch bc.searchEngine se");
		sbHql.append(" join fetch bc.project p");
		sbHql.append(" where p.dsKey = :dsKey");
		
		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<BaseUseCriteria> searchEngines = query.list();
		return searchEngines;
	}

	
	/**
	 * Returns all search engines registered.
	 * 
	 * @return a list of all the search engines registered.
	 */
	public List<SearchEngine> getAllEngines() {

		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from SearchEngine");

		Query query = getSession().createQuery(sbHql.toString());

		List<SearchEngine> engines = query.list();

		return engines;
		
	}
	
	/**
	 * Inserts a Base Use Criteria to a project.
	 * 
	 * @param BaseUseCriteria object.
	 */
	
	public void saveBaseUseCriteria(BaseUseCriteria bc) {
		Query query = getSession().createSQLQuery("INSERT INTO base_use_criteria (id_project, id_search_engine, ds_base_use_criteria) VALUES (:valor1, :valor2, :valor3)");
		query.setParameter("valor1", bc.getProject().getIdProject());
		query.setParameter("valor2", bc.getSearchEngine().getIdSearchEngine());
		query.setParameter("valor3", bc.getDsBaseUseCriteria());
		query.executeUpdate();	
	}
	
}