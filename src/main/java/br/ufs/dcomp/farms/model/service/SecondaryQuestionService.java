package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SecondaryQuestionDao;
import br.ufs.dcomp.farms.model.dto.SecondaryQuestionCreatedDto;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.SecondaryQuestion;

/**
 * @author farms
 *
 */
@Component
public class SecondaryQuestionService {

	@Autowired
	private SecondaryQuestionDao secondaryQuestionDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Get secondary question of protocol project
	 * 
	 * @param dsKey
	 * @return List<SecondaryQuestionCreatedDto>
	 */
	public List<SecondaryQuestionCreatedDto> getByDsKeyProject(String dsKey) {
		List<SecondaryQuestionCreatedDto> studyCreatedDtos = new ArrayList<SecondaryQuestionCreatedDto>();
		List<SecondaryQuestion> secondaryQuestions = secondaryQuestionDao.getByDsKeyProject(dsKey);
		if (secondaryQuestions != null) {
			for (SecondaryQuestion secondaryQuestion : secondaryQuestions) {
				studyCreatedDtos.add(new SecondaryQuestionCreatedDto(secondaryQuestion));
			}
		}
		return studyCreatedDtos;
	}

	/**
	 * Save secondary question of protocol project
	 * 
	 * @param secondaryQuestionCreatedDto
	 * @return boolean
	 */
	public Boolean saveSecondaryQuestion(SecondaryQuestionCreatedDto secondaryQuestionCreatedDto) {
		Project project = projectDao.getByDsKey(secondaryQuestionCreatedDto.getDsProjectKey());

		SecondaryQuestion secondaryQuestion = new SecondaryQuestion(
				secondaryQuestionCreatedDto.getDsSecondaryQuestion(), project);

		secondaryQuestionDao.delete(project.getIdProject());

		secondaryQuestionDao.save(secondaryQuestion);
		return true;
	}
}