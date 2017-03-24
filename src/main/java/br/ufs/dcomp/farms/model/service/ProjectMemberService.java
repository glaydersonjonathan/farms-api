package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.ProjectMemberDao;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dto.ProjectMemberDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberInviteDto;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.ProjectMember;
import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.enums.RoleEnum;
import br.ufs.dcomp.farms.model.enums.StateEnum;

/**
 * @author farms
 *
 */
@Component
public class ProjectMemberService {

	@Autowired
	private ProjectMemberDao projectMemberDao;
	@Autowired
	private ResearcherDao researcherDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Get all members of project
	 * 
	 * @param dsKey
	 * @return List<ProjectMemberDto>
	 */
	public List<ProjectMemberDto> getByDsKeyProject(String dsKey) {
		List<ProjectMemberDto> projectMemberDto = new ArrayList<ProjectMemberDto>();
		List<ProjectMember> projectMembers = projectMemberDao.getByDsKeyProject(dsKey);
		if (projectMembers != null) {
			for (ProjectMember projectMember : projectMembers) {
				projectMemberDto.add(new ProjectMemberDto(projectMember));
			}
		}
		return projectMemberDto;
	}

	/**
	 * Invite a member to project
	 * 
	 * @param projectMemberInviteDto
	 * @return
	 * @throws FarmsException
	 */
	public Boolean invite(ProjectMemberInviteDto projectMemberInviteDto) throws FarmsException {

		Researcher researcher = researcherDao.getByDsEmail(projectMemberInviteDto.getDsEmail());
		if (researcher == null) {
			FarmsMail.sendInviteEmail(projectMemberInviteDto.getDsEmail());
			throw new FarmsException(ErrorMessage.MEMBER_NOT_FOUND);
		}

		if (researcher.getTpState() == StateEnum.I) {
			throw new FarmsException(ErrorMessage.MEMBER_INACTIVE);
		}

		Project project = projectDao.getByDsKey(projectMemberInviteDto.getDsKey());

		if (projectMemberDao.verifyAlreadyMember(project.getIdProject(), researcher.getIdResearcher())) {
			throw new FarmsException(ErrorMessage.ALREADY_MEMBER);
		}

		ProjectMember projectMember = new ProjectMember();
		projectMember.setResearcher(researcher);
		projectMember.setProject(project);
		projectMember.setTpRole(RoleEnum.MEMBER);
		projectMember.setTpState(StateEnum.A);
		projectMemberDao.save(projectMember);

		return true;
	}

	/**
	 * Get role code of researcher in a project
	 * 
	 * @param dsKey
	 * @param dsUserName
	 * @return int
	 */
	public int getRole(String dsKey, String dsUserName) {
		Researcher researcher = researcherDao.getByDsSSO(dsUserName);
		Project project = projectDao.getByDsKey(dsKey);
		return projectMemberDao.getResearcherRole(project.getIdProject(), researcher.getIdResearcher());
	}

	/**
	 * Turns member inactive
	 * 
	 * @param idProjectMember
	 * @return
	 */
	public Boolean inactive(Long idProjectMember) {
		return projectMemberDao.inactive(idProjectMember);
	}

	/**
	 * Turns member active
	 * 
	 * @param idProjectMember
	 * @return
	 */
	public Boolean active(Long idProjectMember) {
		return projectMemberDao.active(idProjectMember);
	}
}