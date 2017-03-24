package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SearchEngineDao;
import br.ufs.dcomp.farms.model.dto.BaseUseCriteriaCreatedDto;
import br.ufs.dcomp.farms.model.dto.SearchEngineCreatedDto;
import br.ufs.dcomp.farms.model.entity.BaseUseCriteria;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.SearchEngine;

/**
 * @author farms
 *
 */
@Component
public class SearchEngineService {

	@Autowired
	private SearchEngineDao searchEngineDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Get search engines of protocol project
	 * 
	 * @param dsKey
	 * @return List<SearchEngineCreatedDto>
	 */
	public List<SearchEngineCreatedDto> getByDsKeyProject(String dsKey) {
		List<SearchEngineCreatedDto> searchEngineCreatedDto = new ArrayList<SearchEngineCreatedDto>();
		List<BaseUseCriteria> baseUseCriterias = searchEngineDao.getByDsKeyProject(dsKey);
		if (baseUseCriterias != null) {
			for (BaseUseCriteria searchEngine : baseUseCriterias) {
				searchEngineCreatedDto.add(new SearchEngineCreatedDto(searchEngine));
			}
		}
		return searchEngineCreatedDto;
	}

	/**
	 * Get all registered engines
	 * 
	 * @return List<SearchEngine>
	 */
	public List<SearchEngine> getAllEngines() {
		return searchEngineDao.getAllEngines();
	}

	public Boolean saveEngine(BaseUseCriteriaCreatedDto bcd) {
		BaseUseCriteria bc = new BaseUseCriteria();

		SearchEngine engine = new SearchEngine(bcd.getEngine());
		Project project = projectDao.getByDsKey(bcd.getDsProjectKey());

		bc.setSearchEngine(engine);
		bc.setProject(project);
		bc.setDsBaseUseCriteria(bcd.getDsBaseUseCriteria());

		searchEngineDao.saveBaseUseCriteria(bc);
		return true;
	}

	/**
	 * Create a new engine
	 * 
	 * @param engine
	 * @return boolean
	 */
	public Boolean createEngine(String engine) {
		SearchEngine searchEngine = new SearchEngine(engine);
		searchEngineDao.saveSearchEngine(searchEngine);
		return true;
	}

	/**
	 * Delete engine of protocol project
	 * 
	 * @param dsProjectKey
	 * @param idEngine
	 * @return
	 */
	public Boolean deleteEngine(String dsProjectKey, Long idEngine) {

		searchEngineDao.deleteEngine(projectDao.getByDsKey(dsProjectKey).getIdProject(), idEngine);
		return true;
	}

	/**
	 * Edit engine of protocol project
	 * 
	 * @param searchEngineCreatedDto
	 * @return
	 */
	public Boolean editEngine(SearchEngineCreatedDto searchEngineCreatedDto) {
		searchEngineDao.editEngine(projectDao.getByDsKey(searchEngineCreatedDto.getDsProjectKey()).getIdProject(),
				searchEngineCreatedDto);
		return true;
	}
}