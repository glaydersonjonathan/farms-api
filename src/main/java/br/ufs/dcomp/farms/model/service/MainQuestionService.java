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

/**
 * @author farms
 *
 */
@Component
public class MainQuestionService {

    @Autowired
    private MainQuestionDao mainQuestionDao;
    @Autowired
    private ProjectDao projectDao;

    /**
     * Get main question of protocol project
     *
     * @param dsKey
     * @return List
     */
    public List<MainQuestionCreatedDto> getByDsKeyProject(String dsKey) {
        List<MainQuestionCreatedDto> mainQuestionCreatedDto = new ArrayList<>();
        List<MainQuestion> mainQuestions = mainQuestionDao.getByDsKeyProject(dsKey);
        if (mainQuestions != null) {
            mainQuestions.forEach((mainQuestion) -> {
                mainQuestionCreatedDto.add(new MainQuestionCreatedDto(mainQuestion));
            });
        }
        return mainQuestionCreatedDto;
    }

    /**
     * Register main question of a project
     *
     * @param mainQuestionCreatedDto
     * @return boolean
     */
    public Boolean saveMainQuestion(MainQuestionCreatedDto mainQuestionCreatedDto) {
        Project project = projectDao.getByDsKey(mainQuestionCreatedDto.getDsProjectKey());

        MainQuestion mainQuestion = new MainQuestion(mainQuestionCreatedDto.getDsMainQuestion(),
                mainQuestionCreatedDto.getDsPopulation(), mainQuestionCreatedDto.getDsIntervation(),
                mainQuestionCreatedDto.getDsControl(), mainQuestionCreatedDto.getDsResult(),
                mainQuestionCreatedDto.getDsApplicationContext(), mainQuestionCreatedDto.getDsExperimentalDesign(),
                project);

        mainQuestionDao.delete(project.getIdProject()); // delete main question
        // registered to save
        // new
        mainQuestionDao.save(mainQuestion);
        return true;
    }
}
