package br.ufs.dcomp.farms.model.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.model.dto.SearchEngineCreatedDto;
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
     * Create a engine
     *
     * @param engine
     */
    public void saveSearchEngine(SearchEngine engine) {
        Query query = getSession().createSQLQuery("INSERT INTO search_engine (nm_search_engine) VALUES (:valor1)");
        query.setParameter("valor1", engine.getNmSearchEngine());
        query.executeUpdate();
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
        sbHql.append("from SearchEngine where nmSearchEngine != 'MANUAL INSERT'");
        Query query = getSession().createQuery(sbHql.toString());
        List<SearchEngine> engines = query.list();
        return engines;
    }

    /**
     * Inserts a Base Use Criteria to a project.
     *
     * @param baseUseCriteria
     */
    public void saveBaseUseCriteria(BaseUseCriteria baseUseCriteria) {
        Query query = getSession().createSQLQuery(
                "INSERT INTO base_use_criteria (id_project, id_search_engine, ds_base_use_criteria) VALUES (:valor1, :valor2, :valor3)");
        query.setParameter("valor1", baseUseCriteria.getProject().getIdProject());
        query.setParameter("valor2", baseUseCriteria.getSearchEngine().getIdSearchEngine());
        query.setParameter("valor3", baseUseCriteria.getDsBaseUseCriteria());
        query.executeUpdate();
    }

    /**
     * Delete engine of protocol project
     *
     * @param idProject
     * @param idEngine
     */
    public void deleteEngine(Long idProject, Long idEngine) {
        Transaction transaction = getSession().beginTransaction();
        try {

            String hql = "delete from BaseUseCriteria where project.idProject= :idProject and searchEngine.idSearchEngine =:idEngine";
            Query query = getSession().createQuery(hql);
            query.setLong("idProject", idProject);
            query.setLong("idEngine", idEngine);
            query.executeUpdate();

            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }

    }

    /**
     * Edit engine of protocol project
     *
     * @param idProject
     * @param searchEngineCreatedDto
     */
    public void editEngine(Long idProject, SearchEngineCreatedDto searchEngineCreatedDto) {
        Query query = getSession().createQuery("update BaseUseCriteria set dsBaseUseCriteria = :dsBaseUseCriteria" + " "
                + " where project.idProject= :idProject and searchEngine.idSearchEngine =:idEngine");
        query.setString("dsBaseUseCriteria", searchEngineCreatedDto.getDsBaseUseCriteria());
        query.setLong("idProject", idProject);
        query.setLong("idEngine", searchEngineCreatedDto.getIdSearchEngine());
        query.executeUpdate();

    }

}
