package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.MainQuestionDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dto.MainQuestionCreatedDto;
import br.ufs.dcomp.farms.model.entity.MainQuestion;
import br.ufs.dcomp.farms.model.entity.Project;

@Component
public class MainQuestionService {

	@Autowired
	private MainQuestionDao mainQuestionDao;
	@Autowired
	private ProjectDao projectDao;

	public List<MainQuestionCreatedDto> getByDsKeyProject(String dsKey) {
		List<MainQuestionCreatedDto> mainQuestionCreatedDto = new ArrayList<MainQuestionCreatedDto>();
		List<MainQuestion> mainQuestions = mainQuestionDao.getByDsKeyProject(dsKey);
		if (mainQuestions != null) {
			for (MainQuestion mainQuestion : mainQuestions) {
				mainQuestionCreatedDto.add(new MainQuestionCreatedDto(mainQuestion));
			}
		}
		return mainQuestionCreatedDto;
	}

	public Boolean saveMainQuestion(MainQuestionCreatedDto mqcd) {
		Project project = projectDao.getByDsKey(mqcd.getDsProjectKey());

		MainQuestion mainQuestion = new MainQuestion(mqcd.getDsMainQuestion(), mqcd.getDsPopulation(),mqcd.getDsIntervation(),
				mqcd.getDsControl(), mqcd.getDsResult(), mqcd.getDsApplicationContext(), mqcd.getDsExperimentalDesign(),
				project);

		mainQuestionDao.delete(project.getIdProject()); // apagar main question
														// anterior
														// para gravar novo

		mainQuestionDao.save(mainQuestion);
		return true;
	}
}