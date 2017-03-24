package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SearchKeywordDao;
import br.ufs.dcomp.farms.model.dto.SearchKeywordCreatedDto;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.SearchKeyword;

/**
 * @author farms
 *
 */
@Component
public class SearchKeywordService {

	@Autowired
	private SearchKeywordDao searchKeywordDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Get keywords of protocol project
	 * 
	 * @param dsKey
	 * @return List<SearchKeywordCreatedDto>
	 */
	public List<SearchKeywordCreatedDto> getByDsKeyProject(String dsKey) {
		List<SearchKeywordCreatedDto> searchKeywordCreatedDto = new ArrayList<SearchKeywordCreatedDto>();
		List<SearchKeyword> searchKeywords = searchKeywordDao.getByDsKeyProject(dsKey);
		if (searchKeywords != null) {
			for (SearchKeyword searchKeyword : searchKeywords) {
				searchKeywordCreatedDto.add(new SearchKeywordCreatedDto(searchKeyword));
			}
		}
		return searchKeywordCreatedDto;
	}

	/**
	 * Save Keyword project
	 * 
	 * @param searchKeywordCreatedDto
	 * @return boolean
	 */
	public Boolean saveKeyword(SearchKeywordCreatedDto searchKeywordCreatedDto) {
		Project project = projectDao.getByDsKey(searchKeywordCreatedDto.getDsProjectKey());

		SearchKeyword keyword = new SearchKeyword(searchKeywordCreatedDto.getDsSearchKeyword(), project);

		searchKeywordDao.save(keyword);
		return true;
	}

	/**
	 * Delete a keyword
	 * 
	 * @param dsProjectKey
	 * @param dsSearchKeyword
	 * @param idSearchKeyword
	 * @return
	 */
	public Boolean deleteKeyword(String dsProjectKey, String dsSearchKeyword, Long idSearchKeyword) {
		SearchKeyword searchKeyword = new SearchKeyword(idSearchKeyword, dsSearchKeyword,
				projectDao.getByDsKey(dsProjectKey));
		searchKeywordDao.delete(searchKeyword);
		return true;
	}

	/**
	 * Edit keyword
	 * 
	 * @param searchKeywordCreatedDto
	 * @return
	 */
	public Boolean editKeyword(SearchKeywordCreatedDto searchKeywordCreatedDto) {
		searchKeywordDao.update(searchKeywordCreatedDto);
		return true;
	}
}