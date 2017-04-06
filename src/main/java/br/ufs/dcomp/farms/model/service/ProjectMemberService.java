package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.model.dao.InvitationDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.ProjectMemberDao;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dto.InvitationDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberInviteDto;
import br.ufs.dcomp.farms.model.entity.Invitation;
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
    @Autowired
    private InvitationDao invitationDao;

    /**
     * Get all members of project
     *
     * @param dsKey
     * @return List
     */
    public List<ProjectMemberDto> getByDsKeyProject(String dsKey) {
        List<ProjectMemberDto> projectMemberDto = new ArrayList<>();
        List<ProjectMember> projectMembers = projectMemberDao.getByDsKeyProject(dsKey);
        if (projectMembers != null) {
            projectMembers.forEach((projectMember) -> {
                projectMemberDto.add(new ProjectMemberDto(projectMember));
            });
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

        Invitation invitation = new Invitation();
        invitation.setDhInvitation(new Date(System.currentTimeMillis()));
        invitation.setProject(project);
        invitation.setResearcher(researcher);

        if (invitationDao.getInvitationsByProjectAndDSSO(project.getIdProject(), researcher.getIdResearcher()).isEmpty()) {
            invitationDao.save(invitation);
            FarmsMail.sendMailText(projectMemberInviteDto.getDsEmail(), "You are invited to project!", "Hi, "
                    + "</br> Open your FARMS account, you are invited to member of project '" + project.getDsTitle() + "'. ");
        } else {
            throw new FarmsException(ErrorMessage.ALREADY_INVITED);
        }
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

    /**
     * get pendents invitations of researcher.
     *
     * @param dsSSO
     * @return
     */
    public List<InvitationDto> GetInvitations(String dsSSO) {
        List<InvitationDto> invitations = new ArrayList<>();
        invitationDao.getInvitations(dsSSO).forEach((i) -> {
            invitations.add(new InvitationDto(i));
        });
        return invitations;
    }

    /**
     * Decline a invitation
     *
     * @param id
     * @return
     */
    public Boolean decline(Long id) {
        invitationDao.delete(id);
        return true;
    }

    /**
     * Accept a invitation.
     *
     * @param id
     * @return
     */
    public Boolean accept(Long id) {
        Invitation invitation = invitationDao.get(id);
        invitationDao.accept(id);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setResearcher(invitation.getResearcher());
        projectMember.setProject(invitation.getProject());
        projectMember.setTpRole(RoleEnum.MEMBER);
        projectMember.setTpState(StateEnum.A);
        projectMemberDao.save(projectMember);

        return true;
    }
}
