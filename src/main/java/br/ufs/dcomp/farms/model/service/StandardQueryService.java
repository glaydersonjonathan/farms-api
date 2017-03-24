package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.StandardQueryDao;
import br.ufs.dcomp.farms.model.dto.StandardQueryCreatedDto;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.StandardQuery;

/**
 * @author farms
 *
 */
@Component
public class StandardQueryService {

	@Autowired
	private StandardQueryDao standardQueryDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Search standard query by project
	 * 
	 * @param dsKey
	 * @return
	 */
	public List<StandardQueryCreatedDto> getByDsKeyProject(String dsKey) {
		List<StandardQueryCreatedDto> languageCreatedDtos = new ArrayList<StandardQueryCreatedDto>();
		List<StandardQuery> standardQuerys = standardQueryDao.getByDsKeyProject(dsKey);
		if (standardQuerys != null) {
			for (StandardQuery standardQuery : standardQuerys) {
				languageCreatedDtos.add(new StandardQueryCreatedDto(standardQuery));
			}
		}
		return languageCreatedDtos;
	}

	/**
	 * Save or replace a standard query
	 * 
	 * @param stqcd
	 * @return
	 */
	public Boolean saveStandardQuery(StandardQueryCreatedDto stqcd) {
		Project project = projectDao.getByDsKey(stqcd.getDsProjectKey());

		StandardQuery standardQuery = new StandardQuery(stqcd.getDsStandardQuery(), project);

		standardQueryDao.delete(project.getIdProject());

		standardQueryDao.save(standardQuery);
		return true;
	}
}