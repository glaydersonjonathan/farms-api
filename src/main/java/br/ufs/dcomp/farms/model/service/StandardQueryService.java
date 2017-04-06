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
        List<StandardQueryCreatedDto> languageCreatedDtos = new ArrayList<>();
        List<StandardQuery> standardQuerys = standardQueryDao.getByDsKeyProject(dsKey);
        if (standardQuerys != null) {
            standardQuerys.forEach((standardQuery) -> {
                languageCreatedDtos.add(new StandardQueryCreatedDto(standardQuery));
            });
        }
        return languageCreatedDtos;
    }

    /**
     * Save or replace a standard query
     *
     * @param standardQueryCreatedDto
     * @return
     */
    public Boolean saveStandardQuery(StandardQueryCreatedDto standardQueryCreatedDto) {
        Project project = projectDao.getByDsKey(standardQueryCreatedDto.getDsProjectKey());

        StandardQuery standardQuery = new StandardQuery(standardQueryCreatedDto.getDsStandardQuery(), project);

        standardQueryDao.delete(project.getIdProject());

        standardQueryDao.save(standardQuery);
        return true;
    }
}
