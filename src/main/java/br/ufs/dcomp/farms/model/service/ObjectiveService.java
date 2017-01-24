package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.ObjectiveDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dto.ObjectiveCreateDto;
import br.ufs.dcomp.farms.model.dto.ObjectiveCreatedDto;
import br.ufs.dcomp.farms.model.entity.Objective;
import br.ufs.dcomp.farms.model.entity.Project;

/**
 * @author farms
 *
 */
@Component
public class ObjectiveService {

	@Autowired
	private ObjectiveDao objectiveDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Get objective of protocol project
	 * 
	 * @param dsKey
	 * @return List<ObjectiveCreatedDto>
	 */
	public List<ObjectiveCreatedDto> getByDsKeyProject(String dsKey) {
		List<ObjectiveCreatedDto> objectiveCreatedDto = new ArrayList<ObjectiveCreatedDto>();
		List<Objective> objectives = objectiveDao.getByDsKeyProject(dsKey);
		if (objectives != null) {
			for (Objective objective : objectives) {
				objectiveCreatedDto.add(new ObjectiveCreatedDto(objective));
			}
		}
		return objectiveCreatedDto;
	}

	/**
	 * Save objective of protocol project
	 * 
	 * @param objectiveCreateDto
	 * @return boolean
	 */
	public Boolean saveObjective(ObjectiveCreateDto objectiveCreateDto) {
		Project project = projectDao.getByDsKey(objectiveCreateDto.getDsKey());

		Objective objective = new Objective(objectiveCreateDto.getDsObjective(), project);

		objectiveDao.delete(project.getIdProject());
		objectiveDao.save(objective);
		return true;
	}
}