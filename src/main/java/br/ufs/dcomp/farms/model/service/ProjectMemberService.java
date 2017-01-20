package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.model.dao.InstitutionDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.ProjectMemberDao;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dto.ProjectCreatedDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberAddInstitutionDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberInviteDto;
import br.ufs.dcomp.farms.model.entity.Institution;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.ProjectMember;
import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.enums.ReviewEnum;
import br.ufs.dcomp.farms.model.enums.RoleEnum;

@Component
public class ProjectMemberService {

	@Autowired
	private ProjectMemberDao projectMemberDao;
	@Autowired
	private ResearcherDao researcherDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private InstitutionDao institutionDao;

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

	public Boolean addInstitutionProject(ProjectMemberAddInstitutionDto pm) {

		Researcher researcher = researcherDao.getByDsSSO(pm.getDsUserName());
		Project project = projectDao.getByDsKey(pm.getDsKey());
		ProjectMember projectMember = new ProjectMember();
		projectMember.setResearcher(researcher);
		projectMember.setProject(project);
		//projectMember.setInstitution(institution);
		projectMember.setTpRole(RoleEnum.COORDINATOR);// VERIFICAR !!!!!!!!!!!!!!!!!
		projectMemberDao.save(projectMember);

		return true;
	}

	public Boolean invite(ProjectMemberInviteDto pm) throws FarmsException {

		Researcher researcher = researcherDao.getByDsEmail(pm.getDsEmail());
		if (researcher == null) {
			FarmsMail.sendInviteEmail(pm.getDsEmail());

			throw new FarmsException(ErrorMessage.MEMBER_NOT_FOUND);
		}

		Project project = projectDao.getByDsKey(pm.getDsKey());
		

		ProjectMember projectMember = new ProjectMember();
		projectMember.setResearcher(researcher);
		projectMember.setProject(project);
		//projectMember.setInstitution(institution);
		projectMember.setTpRole(RoleEnum.MEMBER);
		projectMemberDao.save(projectMember);

		return true;
	}
}