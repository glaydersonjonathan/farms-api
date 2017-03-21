package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.model.dao.InstitutionDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.ProjectMemberDao;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dto.ProjectCreateDto;
import br.ufs.dcomp.farms.model.dto.ProjectCreatedDto;
import br.ufs.dcomp.farms.model.entity.Institution;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.ProjectMember;
import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.enums.ReviewEnum;
import br.ufs.dcomp.farms.model.enums.RoleEnum;
import br.ufs.dcomp.farms.model.enums.StateEnum;

/**
 * @author farms
 *
 */
@Component
public class ProjectService {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ResearcherDao researcherDao;

	@Autowired
	private InstitutionDao institutionDao;

	@Autowired
	private ProjectMemberDao projectMemberDao;

	/**
	 * Save a project
	 * 
	 * @param projectCreateDto
	 * @return ProjectCreatedDto
	 * @throws FarmsException
	 */
	@Transactional(rollbackFor = FarmsException.class)
	public Boolean save(ProjectCreateDto projectCreateDto) throws FarmsException {

		Project project_verify = projectDao.getByDsKey(projectCreateDto.getDsKey());

		if (project_verify != null) {
			throw new FarmsException(ErrorMessage.KEY_ALREADY_IN_USE);
		}

		Researcher researcher = researcherDao.getByDsSSO(projectCreateDto.getDsSsoResearcher());
		if (researcher == null) {
			throw new FarmsException(ErrorMessage.RESEARCHER_NOT_FOUND);
		}

		Project project = new Project();
		project.setDsKey(projectCreateDto.getDsKey());
		project.setDsTitle(projectCreateDto.getDsTitle());
		project.setDsProject(projectCreateDto.getDsProject());
		project.setTpReview(ReviewEnum.fromCode(projectCreateDto.getTpReview()));
		projectDao.save(project);

		Institution institution = new Institution();
		institution.setProject(project);
		institution.setCountry(projectCreateDto.getCountry());
		institution.setDsAbbreviation(projectCreateDto.getDsAbbreviation());
		institution.setNmInstitution(projectCreateDto.getNmInstitution());
		institutionDao.save(institution);

		ProjectMember projectMember = new ProjectMember();
		projectMember.setResearcher(researcher);
		projectMember.setProject(project);
		projectMember.setTpState(StateEnum.A);
		projectMember.setTpRole(RoleEnum.COORDINATOR);
		projectMemberDao.save(projectMember);

		return true;
	}

	/**
	 * Update a project
	 * 
	 * @param projectCreatedDto
	 * @return boolean
	 * @throws FarmsException
	 */
	@Transactional(rollbackFor = FarmsException.class)
	public boolean update(ProjectCreatedDto projectCreatedDto) throws FarmsException {
		Project project = new Project();
		project.setDsKey(projectCreatedDto.getDsKey());
		project.setDsTitle(projectCreatedDto.getDsTitle());
		project.setDsProject(projectCreatedDto.getDsProject());
		project.setIdProject(projectCreatedDto.getIdProject());
		project.setTpReview(ReviewEnum.fromCode(projectCreatedDto.getTpReview()));
		projectDao.update(project);
		return true;
	}

	/**
	 * Search a project by dsKey.
	 * 
	 * @param dsKey
	 * @return project
	 */
	public ProjectCreatedDto getByDsKey(String dsKey) {
		Project project = projectDao.getByDsKey(dsKey);
		ProjectCreatedDto projectCreatedDto = new ProjectCreatedDto(project);
		return projectCreatedDto;
	}

	/**
	 * Get all projects of researcher
	 * 
	 * @param dsSSO
	 * @return List<ProjectCreatedDto>
	 */
	public List<ProjectCreatedDto> GetByDsSsoResearcher(String dsSSO) {
		List<ProjectCreatedDto> projectCreatedDtos = new ArrayList<ProjectCreatedDto>();
		List<Project> projects = projectDao.getByDsSsoResearcher(dsSSO);
		if (projects != null) {
			for (Project project : projects) {
				projectCreatedDtos.add(new ProjectCreatedDto(project));
			}
		}
		return projectCreatedDtos;
	}
}