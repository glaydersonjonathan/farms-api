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

@Component
public class SearchKeywordService {

	@Autowired
	private SearchKeywordDao searchKeywordDao;
	@Autowired
	private ProjectDao projectDao;

	public List<SearchKeywordCreatedDto> getByDsKeyProject(String dsKey) {
		List<SearchKeywordCreatedDto> searchKeywordCreatedDto = new ArrayList<SearchKeywordCreatedDto>();
		List<SearchKeyword> searchKeywords = searchKeywordDao.getByDsKeyProject(dsKey);
		if (searchKeywords != null) {
			for(SearchKeyword searchKeyword : searchKeywords) {
				searchKeywordCreatedDto.add(new SearchKeywordCreatedDto(searchKeyword));
			}
		}
		return searchKeywordCreatedDto;
	}

	public Boolean saveKeyword(SearchKeywordCreatedDto key) {
		Project project = projectDao.getByDsKey(key.getDsProjectKey());

		SearchKeyword keyword = new SearchKeyword(key.getDsSearchKeyword(), project);

		searchKeywordDao.save(keyword);
		return true;
	}
}