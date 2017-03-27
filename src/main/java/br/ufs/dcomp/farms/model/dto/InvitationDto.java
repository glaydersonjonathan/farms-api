package br.ufs.dcomp.farms.model.dto;

import java.util.Date;

import br.ufs.dcomp.farms.model.entity.Invitation;

public class InvitationDto {

	private Long idInvitation;
	private Date dhInvitation;
	private Date dhConfirmation;
	private Long idProject;
	private Long idResearcher;
	private String dsTitle;

	public InvitationDto(Long idInvitation, Date dhInvitation, Date dhConfirmation, Long idProject, Long idResearcher) {
		super();
		this.idInvitation = idInvitation;
		this.dhInvitation = dhInvitation;
		this.dhConfirmation = dhConfirmation;
		this.idProject = idProject;
		this.idResearcher = idResearcher;
	}

	public InvitationDto(Invitation invitation) {
		this.idInvitation = invitation.getIdInvitation();
		this.dhInvitation = invitation.getDhInvitation();
		this.dhConfirmation = invitation.getDhConfirmation();
		this.idProject = invitation.getProject().getIdProject();
		this.idResearcher = invitation.getResearcher().getIdResearcher();
		this.dsTitle = invitation.getProject().getDsTitle();		
	}

	public Long getIdInvitation() {
		return idInvitation;
	}

	public void setIdInvitation(Long idInvitation) {
		this.idInvitation = idInvitation;
	}

	public Date getDhInvitation() {
		return dhInvitation;
	}

	public void setDhInvitation(Date dhInvitation) {
		this.dhInvitation = dhInvitation;
	}

	public Date getDhConfirmation() {
		return dhConfirmation;
	}

	public void setDhConfirmation(Date dhConfirmation) {
		this.dhConfirmation = dhConfirmation;
	}

	public Long getIdProject() {
		return idProject;
	}

	public void setIdProject(Long idProject) {
		this.idProject = idProject;
	}

	public Long getIdResearcher() {
		return idResearcher;
	}

	public void setIdResearcher(Long idResearcher) {
		this.idResearcher = idResearcher;
	}

	public String getDsTitle() {
		return dsTitle;
	}

	public void setDsTitle(String dsTitle) {
		this.dsTitle = dsTitle;
	}

}
