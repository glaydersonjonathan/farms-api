package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dto.SearchKeywordCreatedDto;
import br.ufs.dcomp.farms.model.entity.SearchKeyword;

/**
 * @author farms
 *
 */
@Component
@SuppressWarnings("unchecked")
public class SearchKeywordDao extends HibernateDao<SearchKeyword> {
	/**
	 * Constructor from superclass, indicate to Hibernate.
	 *
	 */
	public SearchKeywordDao() {
		super(SearchKeyword.class);
	}

	/**
	 * Returns all search keywords from the specified project.
	 *
	 * @param dsKey
	 *            the identifier of the project.
	 * @return a list of all the search keywords of the specified project.
	 */
	public List<SearchKeyword> getByDsKeyProject(String dsKey) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from SearchKeyword sk");
		sbHql.append(" join fetch sk.project p");
		sbHql.append(" where p.dsKey = :dsKey");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);
		List<SearchKeyword> searchKeyword = query.list();
		return searchKeyword;
	}
	
	/**
	 * Delete keyword.
	 *
	 * @param searchKeyword
	 * 
	 */
	public void delete (SearchKeyword searchKeyword){
		Transaction transaction = getSession().beginTransaction();
		try {
			
			String hql = "delete from SearchKeyword where project.idProject= :idProject and idSearchKeyword =:idSearchKeyword";
			Query query = getSession().createQuery(hql);
			query.setLong("idProject", searchKeyword.getProject().getIdProject());
			query.setLong("idSearchKeyword", searchKeyword.getIdSearchKeyword());
			System.out.println(query.executeUpdate());

			transaction.commit();
		} catch (Throwable t) {
			transaction.rollback();
			throw t;
		}
	}

	/**
	 * Update keyword
	 * @param searchKeywordCreatedDto
	 */
	public void update(SearchKeywordCreatedDto searchKeywordCreatedDto) {
		Query query = getSession().createQuery("update SearchKeyword set dsSearchKeyword = :dsSearchKeyword" + " "
				+ " where idSearchKeyword = :idSearchKeyword");
		query.setParameter("dsSearchKeyword", searchKeywordCreatedDto.getDsSearchKeyword());
		query.setParameter("idSearchKeyword", searchKeywordCreatedDto.getIdSearchKeyword());
		query.executeUpdate();
		
	}
}